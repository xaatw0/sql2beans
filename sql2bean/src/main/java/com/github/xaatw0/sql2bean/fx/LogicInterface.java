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

	/**
	 * 作成したSQLを保存する
	 * @param id SQLのID
	 * @param sql 保存するSQL
	 */
	void save(String id, String sql);

	/**
	 * 保存したSQLを呼び出す
	 * @param sqlId SQLのID
	 * @return 保存したSQLの内容
	 */
	String load(String sqlId);
}
