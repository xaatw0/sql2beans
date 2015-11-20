package sql2bean.sql;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;

import org.junit.Test;

public class SQLAnalyzerTest {

	@Test
	public void analyze() {

		SQLAnalyzer target = new SQLAnalyzer();

		List<String> result = target.analyze("select * from test where id = ${ID} and name = ${NAME}");
		assertThat(result.size(), is(2));
		assertThat(result.get(0), is("ID"));
		assertThat(result.get(1), is("NAME"));
	}

}
