package sql2bean.dao.table;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApplicationUpdate {

	// original SQL
	//   update APPLICATION set  APP_NAME = ${APP_NAME} ,DB_NAME = ${DB_NAME} ,DB_CONNECTION = ${DB_CONNECTION} where APP_ID = ${APP_ID}

	private final static String SQL_STATEMENT =
			"update APPLICATION set  APP_NAME = ? ,DB_NAME = ? ,DB_CONNECTION = ? where APP_ID = ?";

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

	// APP_ID
	private Integer appId;
	public void setAppId(Integer appId){this.appId = appId;}
	public Integer getAppId(){return appId;}


	public void addBach(PreparedStatement preparedStatement) throws SQLException{

		preparedStatement.setObject(1, getAppName(), 12);
		preparedStatement.setObject(2, getDbName(), 12);
		preparedStatement.setObject(3, getDbConnection(), 12);
		preparedStatement.setObject(4, getAppId(), 4);

		preparedStatement.addBatch();
	}
}
