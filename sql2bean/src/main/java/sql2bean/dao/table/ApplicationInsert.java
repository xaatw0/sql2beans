package sql2bean.dao.table;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApplicationInsert {

	// original SQL
	//   insert into APPLICATION(  APP_NAME ,DB_NAME ,DB_CONNECTION ) values (  ${APP_NAME} ,${DB_NAME} ,${DB_CONNECTION} )

	private final static String SQL_STATEMENT =
			"insert into APPLICATION(  APP_NAME ,DB_NAME ,DB_CONNECTION ) values (  ? ,? ,? )";

	public String getSql() {
		return SQL_STATEMENT;
	}

	// APP_NAME
	private String appName;
	public void setAppName(String appName){this.appName = appName;}
	public String getAppName(){return appName;}

	// DB_NAME
	private String dbName;
	public void setDbName(String dbName){this.dbName = dbName;}
	public String getDbName(){return dbName;}

	// DB_CONNECTION
	private String dbConnection;
	public void setDbConnection(String dbConnection){this.dbConnection = dbConnection;}
	public String getDbConnection(){return dbConnection;}


	public void addBach(PreparedStatement preparedStatement) throws SQLException{

		preparedStatement.setObject(1, getAppName(), 12);
		preparedStatement.setObject(2, getDbName(), 12);
		preparedStatement.setObject(3, getDbConnection(), 12);

		preparedStatement.addBatch();
	}
}
