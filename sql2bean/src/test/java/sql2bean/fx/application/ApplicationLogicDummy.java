package sql2bean.fx.application;

import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sql2bean.dao.table.ApplicationSelect.Data;


public class ApplicationLogicDummy implements ApplicationLogic{

	private StringProperty appName = new SimpleStringProperty();

	private StringProperty dbName = new SimpleStringProperty();

	private StringProperty dbConnection = new SimpleStringProperty();

	@Override
	public void cancel() {
		System.out.println("cancel");
	}

	@Override
	public StringProperty appName() {
		return appName;
	}

	@Override
	public StringProperty dbName() {
		return dbName;
	}

	@Override
	public StringProperty dbConnection() {
		return dbConnection;
	}

	@Override
	public int save() throws SQLException {
		System.out.println("save appName:" + appName.get() + " dbName:" + dbName.get() + " dbConnection:"+ dbConnection.get());
		return 0;
	}

	@Override
	public void delete() throws SQLException {
		System.out.println("delete");
	}

	@Override
	public void set(Data data) {
	}

	@Override
	public Data get() {
		return null;
	}
}
