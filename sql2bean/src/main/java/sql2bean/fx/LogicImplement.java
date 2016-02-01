package sql2bean.fx;

import java.sql.ResultSetMetaData;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import sql2bean.beans.SQLKeyValue;
import sql2bean.sql.ColumnInfo;

public class LogicImplement<T> implements LogicInterface<T> {


	@Override
	public ObservableList<T> execute(String sql, List<SQLKeyValue> lst) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ResultSetMetaData getMetaData() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void save(String id, String sql) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String load(String sqlId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ColumnInfo[] getColumnInfo() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setCell(TableColumn<T, String> column, String columnName) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void analize(String sql, ObservableList<SQLKeyValue> args) {
		// TODO 自動生成されたメソッド・スタブ

	}



}
