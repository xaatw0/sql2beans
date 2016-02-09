package sql2bean.dao.table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackageSelect {

	// original SQL
	//   select  PACKAGE_ID ,APP_ID ,PACKAGE_NAME ,FOLDER from PACKAGE where PACKAGE_NAME = ${PACKAGE_NAME} or ${PACKAGE_NAME} is null;

	private final static String SQL_STATEMENT =
			"select  PACKAGE_ID ,APP_ID ,PACKAGE_NAME ,FOLDER from PACKAGE where PACKAGE_NAME = ? or ? is null;";


	public String getSql() {
		return SQL_STATEMENT;
	}

	// PACKAGE_NAME
	private String packageName;
	public void setPackageName(String packageName){ this.packageName = packageName;}
	public String getPackageName(){ return packageName;}



	public void setParameters(PreparedStatement preparedStatement) throws SQLException{
		preparedStatement.setObject(1, getPackageName(), 12);
		preparedStatement.setObject(2, getPackageName(), 12);
	}

	public class Data{

		// PACKAGE_ID
		private Integer packageId;
		public void setPackageId(Integer packageId){ this.packageId = packageId;}
		public Integer getPackageId(){ return packageId;}

		// APP_ID
		private Integer appId;
		public void setAppId(Integer appId){ this.appId = appId;}
		public Integer getAppId(){ return appId;}

		// PACKAGE_NAME
		private String packageName;
		public void setPackageName(String packageName){ this.packageName = packageName;}
		public String getPackageName(){ return packageName;}

		// FOLDER
		private String folder;
		public void setFolder(String folder){ this.folder = folder;}
		public String getFolder(){ return folder;}
	}


	public Data convert(ResultSet result) throws SQLException {

		Data data = new Data();

		data.setPackageId((Integer)result.getObject(1));
		data.setAppId((Integer)result.getObject(2));
		data.setPackageName((String)result.getObject(3));
		data.setFolder((String)result.getObject(4));

		return data;
	}
}
