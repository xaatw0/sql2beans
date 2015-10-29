package com.github.xaatw0.sql2bean.fx;

import java.sql.ResultSetMetaData;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import com.github.xaatw0.sql2bean.sql.ColumnInfo;
import com.google.inject.ImplementedBy;

@ImplementedBy(LogicImplement.class)
public interface LogicInterface<T> {

	/**
	 * SQLを実施し、テーブルに表示する結果を取得する
	 * @param sql 実施するSQL
	 * @return SQLの結果
	 */
	ObservableList<T> execute(String sql);


	/**
	 * 前回実施したSQLの結果のメタデータを取得する
	 * @return
	 */
	ResultSetMetaData getMetaData();

	/**
	 * 前回実施したSQLの結果のメタデータを取得する
	 * @return
	 */
	ColumnInfo[] getColumnInfo();

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

	void setCell(TableColumn<T, String> column, String columnName);
}
