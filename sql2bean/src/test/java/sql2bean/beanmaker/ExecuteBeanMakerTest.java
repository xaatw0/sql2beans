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
		byte[] data = new byte[1024];
		int length = stream.read(data);

		ExecuteBeanMaker maker = new ExecuteBeanMaker();
		String[] result = maker.write(analyzer, "TestPackage", "TestClass", ISQLType.ISQLExecute).split(System.lineSeparator());
		String[] expected = new String(data,0,length).split(System.lineSeparator());

		for (int i = 0; i < result.length; i ++){
			assertThat((i+1) + "行目", result[i], is(expected[i]));
		}

		assertThat(result.length, is(expected.length));

	}

}
