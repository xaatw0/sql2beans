package sql2bean.fx.application;

import java.sql.SQLException;
import java.util.List;

import sql2bean.dao.table.ApplicationSelect;

public interface ApplicationLogic {

	List<ApplicationSelect.Data> select() throws SQLException;

	int save(ApplicationSelect.Data data) throws SQLException;

	void delete(ApplicationSelect.Data data) throws SQLException;

	void cancel();

}
