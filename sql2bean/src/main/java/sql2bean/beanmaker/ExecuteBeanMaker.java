package sql2bean.beanmaker;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import sql2bean.dao.ISQLType;
import sql2bean.sql.SQLAnalyzer;

public class ExecuteBeanMaker {

	private final static String VM_NAME = "executeBean.vm";

	/**
	 * Velocityのテンプレートを使用して、ファイルに出力する内容を取得する
	 * @param vimpath Velocityのテンプレートのパス
	 * @param staffs 埋め込むスタッフのデータ
	 * @return ファイルに出力する内容
	 */
	public String write(SQLAnalyzer analyzer, String packageName, String className, ISQLType type){

		VelocityContext context = new VelocityContext();
		context.put("list", analyzer.getParameterFormat());
		context.put("package_name", packageName);
		context.put("bean_name", className);
		context.put("originalSql", analyzer.getOriginalSql());
		context.put("preparedSql", analyzer.getPreparedSql());
		context.put("extendInfo", "implements " + type.getClassName());
		context.put("getOverride", type == ISQLType.NONE ? "" : "@Override");

		StringWriter writer = new StringWriter();
		Template template = Velocity.getTemplate(VM_NAME);
		template.merge(context, writer);
		writer.flush();

		return writer.toString();
	}
}
