package sql2bean.sql;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.UnsupportedDataTypeException;

import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sql2bean.beanmaker.BeanMaker;
import sql2bean.beans.SQLKeyValue;
import sql2bean.dao.ISQLType;

public class SQLAnalyzerTest {

	SQLAnalyzer target;
	private static Server server;
	private static Connection conn;

	@BeforeClass
	public static void start() throws SQLException, ClassNotFoundException{
		// データベースを起動
		server = Server.createTcpServer().start();
		Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

        BeanMaker.init();
	}

	@AfterClass
	public static void shutdown() throws SQLException{
		conn.close();
		server.shutdown();
	}

	@Before
	public void tearUp(){
		target = new SQLAnalyzer();
	}

	@Test
	public void reg(){
		Pattern ptnArgument = Pattern.compile("\\$\\{([^)]*?)\\}");
		Matcher matcher = ptnArgument.matcher("${ID}");

		assertThat(matcher.matches() ,is(true));
		assertThat(matcher.group(1), is("ID"));

		matcher = ptnArgument.matcher("${ ID }");

		assertThat(matcher.matches() ,is(true));
		assertThat(matcher.group(1), is(" ID "));

		matcher = ptnArgument.matcher("${ID} ${NAME}");

		assertThat(matcher.find() ,is(true));
		assertThat(matcher.group(1), is("ID"));

		assertThat(matcher.find() ,is(true));
		assertThat(matcher.group(1), is("NAME"));

		assertThat(matcher.find() ,is(false));
	}

	@Test
	public void analyze_simple() {

		List<SQLKeyValue> result = target.analyze("${ID}");
		assertThat(result.size(), is(1));

		SQLKeyValue key = new SQLKeyValue("ID");
		key.addParameter(1);

		assertThat(result.get(0), is(key));
	}

	@Test
	public void analyze() {

		List<SQLKeyValue> result = target.analyze("select * from test where id = ${ID} and name = ${NAME}");
		assertThat(result.size(), is(2));

		SQLKeyValue arg1 = new SQLKeyValue("ID");
		arg1.addParameter(1);
		assertThat(result.get(0), is(arg1));

		SQLKeyValue arg2 = new SQLKeyValue("NAME");
		arg2.addParameter(2);
		assertThat(result.get(1), is(arg2));

		assertThat(target.getPreparedSql(), is("select * from test where id = ? and name = ?"));
	}

	@Test
	public void analyze_重複() {

		List<SQLKeyValue> result = target.analyze("select * from test where id = ${ID} and name = ${NAME} and id2 = ${ID}");
		assertThat(result.size(), is(2));

		SQLKeyValue arg1 = new SQLKeyValue("ID");
		arg1.addParameter(1);
		arg1.addParameter(3);
		assertThat(result.get(0), is(arg1));

		SQLKeyValue arg2 = new SQLKeyValue("NAME");
		arg2.addParameter(2);
		assertThat(result.get(1), is(arg2));
	}

	@Test
	public void copyOldData(){
		List<SQLKeyValue> oldData = new ArrayList<>();
		SQLKeyValue arg1 = new SQLKeyValue("ID");
		arg1.setType(DataType.Integer);
		arg1.setValue("test");
		arg1.addParameter(1);
		oldData.add(arg1);

		SQLKeyValue arg2 = new SQLKeyValue("NAME");
		arg2.addParameter(2);
		oldData.add(arg2);

		List<SQLKeyValue> newData = target.analyze("select * from test where name = ${NAME2} and id = ${ID}");
		target.copyOldData(oldData);

		assertThat(newData.size(), is(2));
		SQLKeyValue result1 = newData.get(1);
		assertThat(result1.getKey(), is("ID"));
		assertThat(result1.getType(), is(DataType.Integer));
		assertThat(result1.getValue(), is("test"));
		assertThat(result1.getParamNos().get(0), is(2));

		assertThat(newData.get(0).getKey(), is("NAME2"));
	}

	@Test
	public void analyze_メタデータ() throws UnsupportedDataTypeException{
		try{
	        Statement statement = conn.createStatement();
	        statement.execute("drop table if exists USER");
	        statement.execute("create table USER (ID int, FULL_NAME varchar(50), MONEY smallint);");

	        ResultSet result = statement.executeQuery("SELECT ID, FULL_NAME, MONEY FROM USER ORDER BY ID");
			List<SQLKeyValue> list = target.analyze(result.getMetaData());

			assertThat(list.size(), is(3));

			SQLKeyValue value0 = list.get(0);
			assertThat(value0.getKey(), is("ID"));
			assertThat(value0.getType(), is(DataType.Integer));
			assertThat(value0.getValue(), is(nullValue()));

			SQLKeyValue value1 = list.get(1);
			assertThat(value1.getKey(), is("FULL_NAME"));
			assertThat(value1.getType(), is(DataType.String));
			assertThat(value1.getValue(), is(nullValue()));

			SQLKeyValue value2 = list.get(2);
			assertThat(value2.getKey(), is("MONEY"));
			assertThat(value2.getType(), is(DataType.Integer));
			assertThat(value2.getValue(), is(nullValue()));

		}catch(SQLException e){
			e.printStackTrace();
		}
	}


	@Test
	public void 更新系引数無し() throws IOException {

		SQLAnalyzer analyzer = new SQLAnalyzer();
		List<SQLKeyValue> list = analyzer.analyze("update test where id = 1");
		assertThat(list.size(),is(0));

		InputStream stream = getClass().getResourceAsStream("更新系引数無し.txt");
		byte[] data = new byte[stream.available()];
		stream.read(data);

		String[] result = analyzer.writeExecuteBean("TestPackage", "TestClass", ISQLType.ISQLExecute).split(System.lineSeparator());
		String[] expected = new String(data).split(System.lineSeparator());

		for (int i = 0; i < result.length; i ++){
			assertThat((i+1) + "行目", result[i], is(expected[i]));
		}

		assertThat(result.length, is(expected.length));

	}

	@Test
	public void 更新系引数3つ() throws IOException {

		SQLAnalyzer analyzer = new SQLAnalyzer();
		List<SQLKeyValue> list = analyzer.analyze("update TEST set AGE = ${AGE}  and PHONE = ${PHONE} where ID = ${ID}");
		assertThat(list.size(),is(3));

		assertThat(list.get(0).getKey(), is("AGE"));
		assertThat(list.get(0).getType(), is(DataType.String));
		list.get(0).setType(DataType.Integer);
		assertThat(list.get(0).getType(), is(DataType.Integer));

		assertThat(list.get(1).getKey(), is("PHONE"));
		assertThat(list.get(1).getType(), is(DataType.String));

		assertThat(list.get(2).getKey(), is("ID"));
		assertThat(list.get(2).getType(), is(DataType.String));
		list.get(2).setType(DataType.Integer);
		assertThat(list.get(2).getType(), is(DataType.Integer));

		InputStream stream = getClass().getResourceAsStream("更新系引数3つ.txt");
		byte[] data = new byte[stream.available()];
		stream.read(data);

		String[] result = analyzer.writeExecuteBean("TestPackage", "TestClass", ISQLType.ISQLExecute).split(System.lineSeparator());
		String[] expected = new String(data).split(System.lineSeparator());

		for (int i = 0; i < result.length; i ++){
			assertThat((i+1) + "行目", result[i], is(expected[i]));
		}

		assertThat(result.length, is(expected.length));

	}

	@Test
	public void 更新系引数3つ1つかぶる() throws IOException {

		SQLAnalyzer analyzer = new SQLAnalyzer();
		List<SQLKeyValue> list = analyzer.analyze("update TEST set AGE = ${AGE}  and PHONE = ${PHONE} where ID = ${AGE}");
		assertThat(list.size(),is(2));

		assertThat(list.get(0).getKey(), is("AGE"));
		assertThat(list.get(0).getType(), is(DataType.String));
		list.get(0).setType(DataType.Integer);
		assertThat(list.get(0).getType(), is(DataType.Integer));

		assertThat(list.get(1).getKey(), is("PHONE"));
		assertThat(list.get(1).getType(), is(DataType.String));

		InputStream stream = getClass().getResourceAsStream("更新系引数3つ1つかぶる.txt");
		byte[] data = new byte[stream.available()];
		stream.read(data);

		String[] result = analyzer.writeExecuteBean("TestPackage", "TestClass", ISQLType.ISQLExecute).split(System.lineSeparator());
		String[] expected = new String(data).split(System.lineSeparator());

		for (int i = 0; i < result.length; i ++){
			assertThat((i+1) + "行目", result[i], is(expected[i]));
		}

		assertThat(result.length, is(expected.length));

	}

	@Test
	public void 更新系ISQLTypeなし() throws IOException {

		SQLAnalyzer analyzer = new SQLAnalyzer();
		List<SQLKeyValue> list = analyzer.analyze("update TEST set AGE = ${AGE}  and PHONE = ${PHONE} where ID = ${AGE}");
		assertThat(list.size(),is(2));

		assertThat(list.get(0).getKey(), is("AGE"));
		assertThat(list.get(0).getType(), is(DataType.String));
		list.get(0).setType(DataType.Integer);
		assertThat(list.get(0).getType(), is(DataType.Integer));

		assertThat(list.get(1).getKey(), is("PHONE"));
		assertThat(list.get(1).getType(), is(DataType.String));

		InputStream stream = getClass().getResourceAsStream("更新系ISQLTypeなし.txt");
		byte[] data = new byte[stream.available()];
		stream.read(data);

		String[] result = analyzer.writeExecuteBean("TestPackage", "TestClass", ISQLType.NONE).split(System.lineSeparator());
		String[] expected = new String(data).split(System.lineSeparator());

		for (int i = 0; i < result.length; i ++){
			assertThat((i+1) + "行目", result[i], is(expected[i]));
		}

		assertThat(result.length, is(expected.length));

	}

	@Test
	public void 参照系ISQLTypeなし() throws IOException, SQLException {

        Statement statement = conn.createStatement();
        statement.execute("drop table if exists USER");
        statement.execute("create table USER (ID int, FULL_NAME varchar(50), MONEY smallint);");

        String sql = "select ID, FULL_NAME, MONEY from USER order by ID";
        target.analyze(sql);

        ResultSet resultSet = statement.executeQuery(sql);
        List<SQLKeyValue> list = target.analyze(resultSet.getMetaData());

		assertThat(list.get(0).getKey(), is("ID"));
		assertThat(list.get(0).getType(), is(DataType.Integer));

		assertThat(list.get(1).getKey(), is("FULL_NAME"));
		assertThat(list.get(1).getType(), is(DataType.String));

		assertThat(list.get(2).getKey(), is("MONEY"));
		assertThat(list.get(2).getType(), is(DataType.Integer));

		InputStream stream = getClass().getResourceAsStream("参照系引数無し.txt");
		byte[] data = new byte[stream.available()];
		stream.read(data);

		String[] result = target.writeSelectBean("TestPackage", "TestClass", ISQLType.NONE).split(System.lineSeparator());
		String[] expected = new String(data).split(System.lineSeparator());

		for (int i = 0; i < result.length; i ++){
			assertThat((i+1) + "行目", result[i], is(expected[i]));
		}

		assertThat(result.length, is(expected.length));
	}

	@Test
	public void 参照系引数2つ() throws IOException, SQLException {

        Statement statement = conn.createStatement();
        statement.execute("drop table if exists USER");
        statement.execute("create table USER (ID int, FULL_NAME varchar(50), MONEY smallint);");

        // ResultSetを取得するためのSQLを作成する
        List<SQLKeyValue> prelist = target.analyze("select ID, FULL_NAME, MONEY from USER where ID = ${ID} and FULL_NAME = ${FULL_NAME}");
        assertThat(prelist.size(), is(2));
		assertThat(prelist.get(0).getKey(), is("ID"));
		assertThat(prelist.get(0).getType(), is(DataType.String));
		prelist.get(0).setType(DataType.Integer);
		assertThat(prelist.get(0).getType(), is(DataType.Integer));
		prelist.get(0).setValue("1");

		assertThat(prelist.get(1).getKey(), is("FULL_NAME"));
		assertThat(prelist.get(1).getType(), is(DataType.String));
		prelist.get(1).setValue("1");

		PreparedStatement preparedStatement = conn.prepareStatement(target.getPreparedSql());
		for(SQLKeyValue sqlKeyValue: prelist){

			for (Integer index: sqlKeyValue.getParamNos()){
				preparedStatement.setObject(index.intValue(), sqlKeyValue.getValue());
			}
		}

		// ResultSetを取得して、結果を分析する
        ResultSet resultSet = preparedStatement.executeQuery();
		List<SQLKeyValue> list = target.analyze(resultSet.getMetaData());

		assertThat(list.get(0).getKey(), is("ID"));
		assertThat(list.get(0).getType(), is(DataType.Integer));

		assertThat(list.get(1).getKey(), is("FULL_NAME"));
		assertThat(list.get(1).getType(), is(DataType.String));

		assertThat(list.get(2).getKey(), is("MONEY"));
		assertThat(list.get(2).getType(), is(DataType.Integer));

		InputStream stream = getClass().getResourceAsStream("参照系引数2つ.txt");
		byte[] data = new byte[stream.available()];
		stream.read(data);

		String[] result = target.writeSelectBean("TestPackage", "TestClass", ISQLType.ISQLSelect).split(System.lineSeparator());
		String[] expected = new String(data).split(System.lineSeparator());

		for (int i = 0; i < result.length; i ++){
			assertThat((i+1) + "行目", result[i], is(expected[i]));
		}

		assertThat(result.length, is(expected.length));
	}
}
