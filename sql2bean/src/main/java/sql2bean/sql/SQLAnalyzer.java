package sql2bean.sql;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.activation.UnsupportedDataTypeException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import sql2bean.beans.SQLKeyValue;
import sql2bean.dao.ISQLType;

public class SQLAnalyzer {
	private static Pattern ptnArgument = Pattern.compile("\\$\\{([^)]*?)\\}");

	private static Pattern ptnSql = Pattern.compile("[\\t\\n\\r]+");

	private String originalSql = null;

	private String preparedSql = null;

	private List<SQLKeyValue> lstParameter;

	private List<SQLKeyValue> lstResult;

	/**
	 * SQLを分析して、SQLKeyValueのリストを作成する。
	 * プリペアステートメントの準備をする。更新系に使用する。
	 * @param sql
	 * @return
	 */
	public List<SQLKeyValue> analyze(String sql){

		String sqlWithoutTags = ptnSql.matcher(sql).replaceAll(" ");

		originalSql = sqlWithoutTags;
		preparedSql = ptnArgument.matcher(sqlWithoutTags).replaceAll("?");

		Matcher matcher = ptnArgument.matcher(sqlWithoutTags);

		Map<String, SQLKeyValue> result = new LinkedHashMap<>();
		int index = 1;
		while(matcher.find()){

			String key = matcher.group(1);

			SQLKeyValue value = result.get(key);
			if (value == null){
				value = new SQLKeyValue(key);
				result.put(key, value);
			}

			value.addParameter(index);
			index++;
		}

		return lstParameter = result.values().stream().collect(Collectors.toList());
	}

	public void setParameter(PreparedStatement statement) throws SQLException{
		for (SQLKeyValue param: lstParameter){
			for (Integer index: param.getParamNos()){
				statement.setObject(index, param.getValue(), param.getType().getType());
			}
		}
	}

	/**
	 * SQL実施後とのメタデータを解析して、SQLKeyValueのリストを作成する。
	 * 参照系に使用する。
	 * @param meta
	 * @return
	 * @throws UnsupportedDataTypeException
	 * @throws SQLException
	 */
	public List<SQLKeyValue> analyze(ResultSetMetaData meta) throws UnsupportedDataTypeException, SQLException{

		int size = meta.getColumnCount();
		lstResult  = new ArrayList<SQLKeyValue>(size);

		for (int i = 0; i < size; i++){
			int sqlType =  meta.getColumnType(i + 1);
			String columnName = meta.getColumnName(i+1);

			Optional<DataType> type =
					Arrays.stream(DataType.values())
					.filter(p->p.getValueStream().anyMatch(q -> q == sqlType))
					.findFirst();

			if (! type.isPresent()){
				throw new UnsupportedDataTypeException("Not Upport SQL Data:" + columnName + ":" + sqlType);
			}

			SQLKeyValue column = new SQLKeyValue();
			column.setKey(columnName);
			column.setType(type.get());
			lstResult.add(column);
		}

		return lstResult;
	}

	public void copyOldData(List<SQLKeyValue> oldData){

		for (SQLKeyValue key: lstParameter){

			Optional<SQLKeyValue> old = oldData.stream().filter(p-> p.getKey().equals(key.getKey())).findAny();
			if (old.isPresent()){
				key.setType(old.get().getType());
				key.setValue(old.get().getValue());
			}
		}
	}

	public  List<SQLKeyValue> getParameterFormat(){
		return lstParameter;
	}

	public List<SQLKeyValue> getResultFormat(){
		return lstResult;
	}

	public String getOriginalSql(){
		return originalSql;
	}

	public String getPreparedSql(){
		return preparedSql;
	}

	private final static String VM_NAME = "executeBean.vm";

	/**
	 * Velocityのテンプレートを使用して、ファイルに出力する内容を取得する
	 * @param vimpath Velocityのテンプレートのパス
	 * @param staffs 埋め込むスタッフのデータ
	 * @return ファイルに出力する内容
	 */
	public String writeExecuteBean(String packageName, String className, ISQLType type){

		VelocityContext context = new VelocityContext();
		context.put("list", getParameterFormat());
		context.put("package_name", packageName);
		context.put("bean_name", className);
		context.put("originalSql", getOriginalSql());
		context.put("preparedSql", getPreparedSql());
		context.put("extendInfo", type == ISQLType.NONE ? "" : " implements " + type.getClassName());
		context.put("getOverride", type == ISQLType.NONE ? "" : System.lineSeparator() + "\t@Override");

		StringWriter writer = new StringWriter();
		Template template = Velocity.getTemplate(VM_NAME);
		template.merge(context, writer);
		writer.flush();

		return writer.toString();
	}

	private final static String VM_SELECT = "selectBean.vm";

	public String writeSelectBean(String packageName, String className, ISQLType type) throws IOException{

		VelocityContext context = new VelocityContext();
		context.put("list", getParameterFormat());
		context.put("resultFormat", getResultFormat());
		context.put("package_name", packageName);
		context.put("bean_name", className);
		context.put("originalSql", getOriginalSql());
		context.put("preparedSql", getPreparedSql());
		context.put("extendInfo", type == ISQLType.NONE ? "" : " implements " + type.getClassName() + "<" +  className + ".Data>");
		context.put("getOverride", type == ISQLType.NONE ? "" : "\t@Override");

		StringWriter writer = new StringWriter();
		Template template = Velocity.getTemplate(VM_SELECT);
		template.merge(context, writer);
		writer.flush();

		return writer.toString();
	}
}
