package sql2bean.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SQLAnalyzer {

	private Pattern ptnArgument = Pattern.compile("\\$\\{([^)]*?)\\}");

	public List<String> analyze(String sql){

		Matcher matcher = ptnArgument.matcher(sql);

		List<String> result = new ArrayList<>();
		while(matcher.find()){
			result.add(matcher.group(1));
		}

		return result;
	}

}
