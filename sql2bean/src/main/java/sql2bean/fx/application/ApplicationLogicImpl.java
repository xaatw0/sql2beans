package sql2bean.fx.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sql2bean.dao.table.ApplicationDelete;
import sql2bean.dao.table.ApplicationInsert;
import sql2bean.dao.table.ApplicationSelect;
import sql2bean.dao.table.ApplicationUpdate;

public class ApplicationLogicImpl implements ApplicationLogic{

	private Connection connection;

	public ApplicationLogicImpl(Connection connection){
		this.connection = connection;
	}

	@Override
	public List<ApplicationSelect.Data> select() throws SQLException {

		List<ApplicationSelect.Data> list = new ArrayList<>();

		ApplicationSelect select = new ApplicationSelect();
		PreparedStatement statement = connection.prepareStatement(select.getSql());
		ResultSet result = statement.executeQuery();

		while(result.next()){
			list.add(select.convert(result));
		}

		return list;
	}

	@Override
	public int save(ApplicationSelect.Data data) throws SQLException {

		if (data.getAppId() == null){
			ApplicationInsert insert = new ApplicationInsert();

			PreparedStatement statement = connection.prepareStatement(insert.getSql(),
				    Statement.RETURN_GENERATED_KEYS);
			insert.setAppName(data.getAppName());
			insert.setDbName(data.getDbName());
			insert.setDbConnection(data.getDbConnection());
			insert.addBach(statement);
			statement.executeBatch();

			ResultSet tableKeys = statement.getGeneratedKeys();
			tableKeys.next();
			data.setAppId(tableKeys.getInt(1));

		} else {
			ApplicationUpdate update = new ApplicationUpdate();
			PreparedStatement statement = connection.prepareStatement(update.getSql());

			update.setAppId(data.getAppId());
			update.setAppName(data.getAppName());
			update.setDbName(data.getDbName());
			update.setDbConnection(data.getDbConnection());
			update.addBach(statement);
			statement.executeBatch();
		}

		return data.getAppId();
	}

	@Override
	public void cancel() {
	}

	@Override
	public void delete(ApplicationSelect.Data data) throws SQLException {

		ApplicationDelete delete = new ApplicationDelete();

		PreparedStatement statement = connection.prepareStatement(delete.getSql());
		delete.setAppId(data.getAppId());
		delete.addBach(statement);
		statement.executeBatch();
	}
}
