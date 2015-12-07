package sql2bean.beanmaker;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import sql2bean.sql.ColumnInfo;

public class BeanMaker {

	private static boolean isInitialized = false;

	private final static String VM_NAME = "bean.vm";

	private String outputPath;

	public BeanMaker(String outputPath){
		this.outputPath = outputPath;
	}


	/**
	 * 初期化する
	 */
	public static void init(){

		if (isInitialized){
			return;
		}

		// 初期化
	    Properties p = new Properties();
	    p.setProperty("resource.loader", "class");
	    p.setProperty("class.resource.loader.class",
	            "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    p.setProperty("input.encoding", "UTF-8");
	    Velocity.init(p);

	    isInitialized = true;
	}

	/**
	 * Velocityのテンプレートを使用して、ファイルに出力する内容を取得する
	 * @param vimpath Velocityのテンプレートのパス
	 * @param staffs 埋め込むスタッフのデータ
	 * @return ファイルに出力する内容
	 */
	public String write(List<ColumnInfo> dataType, String packageName, String className){

		assert isInitialized : "初期化してません" ;

		VelocityContext context = new VelocityContext();
		context.put("list", dataType.iterator());
		context.put("package_name", packageName);
		context.put("bean_name", className);

		StringWriter writer = new StringWriter();
		Template template = Velocity.getTemplate(VM_NAME);
		template.merge(context, writer);
		writer.flush();

		return writer.toString();
	}

	public void compile(String qualifiedClassName, String source){
        DiagnosticCollector<JavaFileObject> diags = new DiagnosticCollector<JavaFileObject>();
        // コンパイル・オプション
        List<String> options = Arrays.asList("-d", outputPath);

        // Java のソースコード
        List<? extends JavaFileObject> src = Arrays.asList(
            new DynamicJavaSourceCodeObject(qualifiedClassName, source)
        );

        // (2) コンパイラ・オブジェクト取得
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // (3) コンパイル実行
        JavaCompiler.CompilationTask compilerTask = compiler.getTask(null, null, diags, options, null, src);

        // (4) コンパイル・エラーのチェック
        if (!compilerTask.call()){
            for (Diagnostic diag : diags.getDiagnostics()){
                System.out.format("Error on line %d in %s", diag.getLineNumber(), diag);
            }
        }
	}

	public void compile(List<ColumnInfo> dataType, String packageName, String className){

		compile(packageName +"." + className , write(dataType, packageName, className));
	}
}
