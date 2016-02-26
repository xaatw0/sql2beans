package sql2bean.dao.table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationSelect {

	// original SQL
	//   select  APP_ID ,APP_NAME ,DB_NAME ,DB_CONNECTION from APPLICATION

	private final static String SQL_STATEMENT =
			"select  APP_ID ,APP_NAME ,DB_NAME ,DB_CONNECTION from APPLICATION";


	public String getSql() {
		return SQL_STATEMENT;
	}

	public void setParameters(PreparedStatement preparedStatement) throws SQLException{
	}

	public static class Data{

		// APP_ID
		private Integer appId;
		public void setAppId(Integer appId){ this.appId = appId;}
		public Integer getAppId(){ return appId;}

		// APP_NAME
		private String appName;
		public void setAppName(String appName){ this.appName = appName;}
		public String getAppName(){ return appName;}

		// DB_NAME
		private String dbName;
		public void setDbName(String dbName){ this.dbName = dbName;}
		public String getDbName(){ return dbName;}

		// DB_CONNECTION
		private String dbConnection;
		public void setDbConnection(String dbConnection){ this.dbConnection = dbConnection;}
		public String getDbConnection(){ return dbConnection;}
	}


	public Data convert(ResultSet result) throws SQLException {

		Data data = new Data();

		data.setAppId((Integer)result.getObject(1));
		data.setAppName((String)result.getObject(2));
		data.setDbName((String)result.getObject(3));
		data.setDbConnection((String)result.getObject(4));

		return data;
	}
}
