package sql2bean.fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.activation.UnsupportedDataTypeException;

import sql2bean.beans.SQLKeyValue;
import sql2bean.sql.ColumnInfo;

public class LogicDummy implements LogicInterface<DummyObject> {

	private ResultSetMetaData metaData;

	private ColumnInfo[]  columnInfos;

	public LogicDummy() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public ObservableList<DummyObject> execute(String sql) {

		ObservableList<DummyObject> data = FXCollections.observableArrayList();

		try{
	        Connection conn = DriverManager.
	            getConnection("jdbc:h2:~/test", "sa", "");

	        Statement statement = conn.createStatement();
	        statement.execute("DROP TABLE IF EXISTS USER;");
	        statement.execute("CREATE TABLE USER (ID int, FULL_NAME VARCHAR(50), MONEY int);");
	        statement.execute("INSERT INTO USER VALUES(1,'test1', 100);");
	        statement.execute("INSERT INTO USER VALUES(2,'test2', 200);");
	        statement.execute("INSERT INTO USER VALUES(3,'test3', 300);");

	        ResultSet result = statement.executeQuery("SELECT ID,NAME, MONEY FROM USER ORDER BY ID;");
			metaData = result.getMetaData();

			columnInfos = ColumnInfo.createColumnInfo(result.getMetaData());

			while(result.next()){
				data.add(new DummyObject(result.getInt("ID"), result.getString("NAME")));
			}

	        conn.close();

		}catch(SQLException | UnsupportedDataTypeException e){
			e.printStackTrace();
		}

		return data;
	}

	@Override
	public ResultSetMetaData getMetaData() {
		return metaData;
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
		return columnInfos;
	}


	@Override
	public void setCell(TableColumn<DummyObject, String> column, String columnName) {
		column.setCellValueFactory(new PropertyValueFactory<DummyObject,String>(columnName));
	}

	@Override
	public void analize(String sql, ObservableList<SQLKeyValue> args) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
