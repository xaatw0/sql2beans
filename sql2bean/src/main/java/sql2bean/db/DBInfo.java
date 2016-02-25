package sql2bean.db;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sql2bean.dao.table.ApplicationDelete;
import sql2bean.dao.table.ApplicationInsert;
import sql2bean.dao.table.ApplicationSelect;
import sql2bean.dao.table.ApplicationUpdate;
import sql2bean.dao.table.PackageDelete;
import sql2bean.dao.table.PackageInsert;
import sql2bean.dao.table.PackageSelect;
import sql2bean.dao.table.PackageUpdate;

public class DBInfo implements IDBInfo,Closeable{

	private Connection connection;

	public DBInfo(IMainDB db) {
		connection = db.getConnection();
	}

	@Override
	public List<ApplicationSelect.Data> selectApplication() throws SQLException {
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
	public int insertApplication(ApplicationSelect.Data data) throws SQLException {

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

		return tableKeys.getInt(1);
	}

	@Override
	public int updateApplication(ApplicationSelect.Data data) throws SQLException {
		ApplicationUpdate update = new ApplicationUpdate();
		PreparedStatement statement = connection.prepareStatement(update.getSql());

		update.setAppId(data.getAppId());
		update.setAppName(data.getAppName());
		update.setDbName(data.getDbName());
		update.setDbConnection(data.getDbConnection());
		update.addBach(statement);
		statement.executeBatch();

		return data.getAppId();
	}

	@Override
	public boolean deleteApplication(ApplicationSelect.Data data) throws SQLException {
		ApplicationDelete delete = new ApplicationDelete();

		PreparedStatement statement = connection.prepareStatement(delete.getSql());
		delete.setAppId(data.getAppId());
		delete.addBach(statement);
		int result[] = statement.executeBatch();
		return Arrays.stream(result).sum() == 1;
	}

	@Override
	public void close() throws IOException {

		try {
			if (connection != null && ! connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e) {
		} finally{
			connection = null;
		}
	}

	@Override
	public List<PackageSelect.Data> selectPackage()
			throws SQLException {
		List<PackageSelect.Data> list = new ArrayList<>();

		PackageSelect select = new PackageSelect();
		PreparedStatement statement = connection.prepareStatement(select.getSql());
		ResultSet result = statement.executeQuery();

		while(result.next()){
			list.add(select.convert(result));
		}

		return list;
	}

	@Override
	public int insertPackage(PackageSelect.Data data)
			throws SQLException {

		PackageInsert insert = new PackageInsert();

		PreparedStatement statement = connection.prepareStatement(insert.getSql(),
			    Statement.RETURN_GENERATED_KEYS);

		insert.setAppId(data.getAppId());
		insert.setPackageName(data.getPackageName());
		insert.setFolder(data.getFolder());
		insert.addBach(statement);
		statement.executeBatch();

		ResultSet tableKeys = statement.getGeneratedKeys();
		tableKeys.next();

		return tableKeys.getInt(1);
	}

	@Override
	public int updatePackage(PackageSelect.Data data)
			throws SQLException {

		PackageUpdate update = new PackageUpdate();
		PreparedStatement statement = connection.prepareStatement(update.getSql());

		update.setPackageId(data.getAppId());
		update.setPackageName(data.getPackageName());
		update.setFolder(data.getFolder());
		update.addBach(statement);
		statement.executeBatch();

		return data.getAppId();
	}

	@Override
	public boolean deletePackage(PackageSelect.Data data)
			throws SQLException {
		PackageDelete delete = new PackageDelete();

		PreparedStatement statement = connection.prepareStatement(delete.getSql());
		delete.setPackageId(data.getPackageId());
		delete.addBach(statement);
		int result[] = statement.executeBatch();
		return Arrays.stream(result).sum() == 1;
	}
}
