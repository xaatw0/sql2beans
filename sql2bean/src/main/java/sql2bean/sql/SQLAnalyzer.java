package sql2bean.sql;

import java.io.StringWriter;
import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import sql2bean.beans.SQLKeyValue;
import sql2bean.dao.ISQLType;

public class SQLAnalyzer {
	private Pattern ptnArgument = Pattern.compile("\\$\\{([^)]*?)\\}");

	private String originalSql = null;

	private String preparedSql = null;

	private List<SQLKeyValue> data;

	/**
	 * SQLを分析して、SQLKeyValueのリストを作成する。
	 * プリペアステートメントの準備をする。更新系に使用する。
	 * @param sql
	 * @return
	 */
	public List<SQLKeyValue> analyze(String sql){

		originalSql = sql;
		preparedSql = ptnArgument.matcher(sql).replaceAll("?");

		Matcher matcher = ptnArgument.matcher(sql);

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

		return data = result.values().stream().collect(Collectors.toList());
	}

	/**
	 * SQL実施後とのメタデータを解析して、SQLKeyValueのリストを作成する。
	 * 参照系に使用する。
	 * @param meta
	 * @return
	 */
	public List<SQLKeyValue> analyze(ResultSetMetaData meta){
		return null;
	}

	public void copyOldData(List<SQLKeyValue> oldData){

		for (SQLKeyValue key: data){

			Optional<SQLKeyValue> old = oldData.stream().filter(p-> p.getKey().equals(key.getKey())).findAny();
			if (old.isPresent()){
				key.setType(old.get().getType());
				key.setValue(old.get().getValue());
			}
		}
	}

	public  List<SQLKeyValue> getKeyValue(){
		return data;
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
		context.put("list", getKeyValue());
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
}
