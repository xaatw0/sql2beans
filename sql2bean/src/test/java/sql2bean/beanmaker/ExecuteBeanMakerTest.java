package sql2bean.beanmaker;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sql2bean.beans.SQLKeyValue;
import sql2bean.dao.ISQLType;
import sql2bean.sql.DataType;
import sql2bean.sql.SQLAnalyzer;

public class ExecuteBeanMakerTest {

	@Before
	public void tearUp(){
		new BeanMaker(null).init();
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

}
