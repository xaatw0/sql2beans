package sql2bean.fx.application;

import java.sql.SQLException;

import javafx.beans.property.StringProperty;
import sql2bean.dao.table.ApplicationSelect;

public interface ApplicationLogic {

	int save() throws SQLException;

	void delete() throws SQLException;

	void cancel();

	StringProperty appName();

	StringProperty dbName();

	StringProperty dbConnection();

	void set(ApplicationSelect.Data data);

	ApplicationSelect.Data get();

}
