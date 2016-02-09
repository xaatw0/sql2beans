package sql2bean.fx.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sql2bean.dao.table.ApplicationDelete;
import sql2bean.dao.table.ApplicationInsert;
import sql2bean.dao.table.ApplicationSelect;
import sql2bean.dao.table.ApplicationUpdate;

public class ApplicationLogicImpl implements ApplicationLogic{

	private Connection connection;

	private int id;

	public ApplicationLogicImpl(Connection connection){
		this.connection = connection;
		id = -1;
	}

	@Override
	public ApplicationSelect.Data load(int id) throws SQLException {

		if (id == -1){
			return null;
		}

		this.id = id;

		ApplicationSelect select = new ApplicationSelect();
		PreparedStatement statement = connection.prepareStatement(select.getSql());
		ResultSet result = statement.executeQuery();

		while(result.next()){

			ApplicationSelect.Data data = select.convert(result);
			if (data.getAppId().equals(id)){
				return data;
			}
		}

		return null;
	}

	@Override
	public int save(String name, String dbName, String dbConnection) throws SQLException {

		if (id == -1){
			ApplicationInsert insert = new ApplicationInsert();

			PreparedStatement statement = connection.prepareStatement(insert.getSql(),
				    Statement.RETURN_GENERATED_KEYS);
			insert.setAppName(name);
			insert.setDbName(dbName);
			insert.setDbConnection(dbConnection);
			insert.addBach(statement);
			statement.executeBatch();

			ResultSet tableKeys = statement.getGeneratedKeys();
			tableKeys.next();
			id = tableKeys.getInt(1);

		} else {
			ApplicationUpdate update = new ApplicationUpdate();
			PreparedStatement statement = connection.prepareStatement(update.getSql());
			update.setAppId(id);
			update.setAppName(name);
			update.setDbName(dbName);
			update.setDbConnection(dbConnection);
			update.addBach(statement);
			statement.executeBatch();
		}

		return id;
	}

	@Override
	public void cancel() {
	}

	@Override
	public void delete() throws SQLException {

		if (id == -1){
			return;
		}

		ApplicationDelete delete = new ApplicationDelete();

		PreparedStatement statement = connection.prepareStatement(delete.getSql());
		delete.setAppId(id);
		delete.addBach(statement);
		statement.executeBatch();
	}
}
