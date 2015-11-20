package sql2bean.sql;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

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

		List<String> result = target.analyze("${ID}");
		assertThat(result.size(), is(1));
		assertThat(result.get(0), is("ID"));
	}

	@Test
	public void analyze() {

		List<String> result = target.analyze("select * from test where id = ${ID} and name = ${NAME}");
		assertThat(result.size(), is(2));
		assertThat(result.get(0), is("ID"));
		assertThat(result.get(1), is("NAME"));
	}

}
