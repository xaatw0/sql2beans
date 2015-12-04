package sql2bean.sql;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import sql2bean.beans.SQLKeyValue;

public class SQLAnalyzer {
	private Pattern ptnArgument = Pattern.compile("\\$\\{([^)]*?)\\}");

	private String originalSql = null;

	private String preparedSql = null;

	private List<SQLKeyValue> data;

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
}
