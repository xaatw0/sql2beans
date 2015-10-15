package com.github.xaatw0.sql2bean.sql;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.UnsupportedDataTypeException;


/**
 * DBより取得したコラムの情報。一つのコラムで一つのインスタンス。
 */
public class ColumnInfo {


	final static Pattern PTN_SNEKE_CASE = Pattern.compile("_([a-z])");

	private String name;
	private DataType type;

	/**
	 * データベースのテーブルの項目
	 * @param name テーブルの項目名
	 * @param type 項目の種類
	 */
	public ColumnInfo(String name, DataType type){

		this.name = name;
		this.type = type;
	}

	/**
	 * 項目名を取得する
	 * @return 項目名
	 */
	public String getName(){
		return name;
	}

	/**
	 * 項目の種類を取得する
	 * @return 項目の種類(文字型、数字型、等)
	 */
	public DataType getType(){
		return type;
	}

	/**
	 * 項目名をキャメル式で取得する
	 * @return 項目名(キャメル式)
	 */
	public String getCamelName(){
		Matcher m = PTN_SNEKE_CASE.matcher(name.toLowerCase());
		return convertUnderToBig(m);
	}

	/**
	 * 項目名をパスカル式で取得する
	 * @return 項目名(パスカル式)
	 */
	public String getPascalName(){

		Matcher m = PTN_SNEKE_CASE.matcher(
				name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
		return convertUnderToBig(m);
	}

	/**
	 * 小文字を大文字にして返す
	 * @param m パターン
	 * @return
	 */
	private String convertUnderToBig(Matcher m){
		StringBuffer sb = new StringBuffer(name.length());
		while (m.find()) {
			m.appendReplacement(sb, m.group(1).toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 実施したSQLのメタデータをColumnInfoの配列に変換する
	 * @param meta メタデータ
	 * @return ColumnInfoの配列
	 * @throws SQLException
	 * @throws UnsupportedDataTypeException
	 */
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
