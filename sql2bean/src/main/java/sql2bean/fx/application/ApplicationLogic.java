package sql2bean.fx.application;

import java.sql.SQLException;

import sql2bean.dao.table.ApplicationSelect;

public interface ApplicationLogic {

	ApplicationSelect.Data load(int id) throws SQLException;

	int save(String name, String dbName, String dbConnection) throws SQLException;

	void delete() throws SQLException;

	void cancel();

}
