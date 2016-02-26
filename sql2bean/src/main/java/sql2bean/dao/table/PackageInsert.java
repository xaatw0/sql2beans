package sql2bean.dao.table;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PackageInsert {

	// original SQL
	//   insert into PACKAGE(APP_ID,PACKAGE_NAME,FOLDER) values (${APP_ID},${PACKAGE_NAME},${FOLDER})

	private final static String SQL_STATEMENT =
			"insert into PACKAGE(APP_ID,PACKAGE_NAME,FOLDER) values (?,?,?)";

	public String getSql() {
		return SQL_STATEMENT;
	}

	// APP_ID
	private Integer appId;
	public void setAppId(Integer appId){this.appId = appId;}
	public Integer getAppId(){return appId;}

	// PACKAGE_NAME
	private String packageName;
	public void setPackageName(String packageName){this.packageName = packageName;}
	public String getPackageName(){return packageName;}

	// FOLDER
	private String folder;
	public void setFolder(String folder){this.folder = folder;}
	public String getFolder(){return folder;}


	public void addBach(PreparedStatement preparedStatement) throws SQLException{

		preparedStatement.setObject(1, getAppId(), 4);
		preparedStatement.setObject(2, getPackageName(), 12);
		preparedStatement.setObject(3, getFolder(), 12);

		preparedStatement.addBatch();
	}
}
