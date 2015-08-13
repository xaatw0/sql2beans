package com.github.xaatw0.sql2bean.sql;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.UnsupportedDataTypeException;


public class ColumnInfo {


	final static Pattern PTN_SNEKE_CASE = Pattern.compile("_([a-z])");

	private String name;
	private DataType type;

	public ColumnInfo(String name, DataType type){

		this.name = name;
		this.type = type;
	}

	public String getName(){
		return name;
	}

	public DataType getType(){
		return type;
	}

	public String getCamelName(){
		Matcher m = PTN_SNEKE_CASE.matcher(name.toLowerCase());
		return convertUnderToBig(m);
	}

	public String getPascalName(){

		Matcher m = PTN_SNEKE_CASE.matcher(
				name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
		return convertUnderToBig(m);
	}

	private String convertUnderToBig(Matcher m){
		StringBuffer sb = new StringBuffer(name.length());
		while (m.find()) {
			m.appendReplacement(sb, m.group(1).toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static ColumnInfo[] createColumnInfo(ResultSetMetaData meta) throws SQLException, UnsupportedDataTypeException{

		ColumnInfo[] info = new ColumnInfo[meta.getColumnCount()];

		for (int i = 0; i < info.length; i++){
			int sqlType =  meta.getColumnType(i + 1);
			Optional<DataType> type =
					Arrays.stream(DataType.values())
					.filter(p->p.getValueStream().anyMatch(q-> q == sqlType))
					.findFirst();

			if (! type.isPresent()){
				throw new UnsupportedDataTypeException("Not Upport SQL Data:" + sqlType);
			}

			info[i] = new ColumnInfo(meta.getColumnLabel(i + 1), type.get());
		}

		return info;

	}
}
