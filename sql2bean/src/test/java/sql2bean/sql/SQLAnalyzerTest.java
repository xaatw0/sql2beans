package sql2bean.sql;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import sql2bean.beans.SQLKeyValue;

public class SQLAnalyzerTest {

	SQLAnalyzer target;

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
	public void analyze_メタデータ(){
		try{
	        Connection conn = DriverManager.
	            getConnection("jdbc:h2:~/test", "sa", "");

	        Statement statement = conn.createStatement();
	        statement.execute("drop table if exists USER");
	        statement.execute("create table USER (ID int, FULL_NAME varchar(50), MONEY smallint);");

	        ResultSet result = statement.executeQuery("SELECT ID, NAME, MONEY FROM USER ORDER BY ID;");
			List<SQLKeyValue> list = target.analyze(result.getMetaData());

			assertThat(list.size(), is(3));

			SQLKeyValue value0 = list.get(0);
			assertThat(value0.getKey(), is("ID"));
			assertThat(value0.getType(), is(DataType.Integer));
			assertThat(value0.getParamNos(), is(nullValue()));
			assertThat(value0.getValue(), is(""));

			SQLKeyValue value1 = list.get(1);
			assertThat(value1.getKey(), is("FULL_NAME"));
			assertThat(value1.getType(), is(DataType.String));
			assertThat(value1.getParamNos(), is(nullValue()));
			assertThat(value1.getValue(), is(""));

			SQLKeyValue value2 = list.get(2);
			assertThat(value2.getKey(), is("MONEY"));
			assertThat(value2.getType(), is(DataType.Integer));
			assertThat(value2.getParamNos(), is(nullValue()));
			assertThat(value2.getValue(), is(""));

	        conn.close();

		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
