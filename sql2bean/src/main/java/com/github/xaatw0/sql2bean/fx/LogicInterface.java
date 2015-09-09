package com.github.xaatw0.sql2bean.fx;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.google.inject.ImplementedBy;

@ImplementedBy(LogicImplement.class)
public interface LogicInterface {

	/**
	 * SQLを実施し、テーブルに表示する結果を取得する
	 * @param sql 実施するSQL
	 * @return SQLの結果
	 */
	ResultSet execute(String sql);


	/**
	 * 前回実施したSQLの結果のメタデータを取得する
	 * @return
	 */
	ResultSetMetaData getMetaData();

	void save(String sql);
}
