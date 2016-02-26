package sql2bean.fx.application;

import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sql2bean.dao.table.ApplicationSelect.Data;
import sql2bean.db.IDBInfo;

public class ApplicationLogicImpl implements ApplicationLogic{

	private IDBInfo db;

	private Data data;

	private StringProperty appName = new SimpleStringProperty();

	private StringProperty dbName = new SimpleStringProperty();

	private StringProperty dbConnection = new SimpleStringProperty();

	public ApplicationLogicImpl(IDBInfo db){
		this.db = db;
	}

	@Override
	public int save() throws SQLException {

		if (data == null){

			data = new Data();
			init(data);

			int id = db.insertApplication(data);
			data.setAppId(id);

		} else {

			init(data);
			db.updateApplication(data);
		}

		return data.getAppId();
	}

	private void init(Data data){
		data.setAppName(appName.get());
		data.setDbName(dbName.get());
		data.setDbConnection(dbConnection.get());
	}

	@Override
	public void cancel() {
		data = null;
	}

	@Override
	public void delete() throws SQLException {

		db.deleteApplication(data);
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
	public void set(Data data) {

		this.data = data;
	}

	@Override
	public Data get() {
		return data;
	}
}
