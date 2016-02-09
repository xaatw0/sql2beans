package sql2bean.dao.table;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PackageUpdate {
	// original SQL
	//   update PACKAGE set PACKAGE_NAME = ${PACKAGE_NAME}, FOLDER = ${FOLDER} where PACKAGE_ID = ${PACKAGE_ID}

	private final static String SQL_STATEMENT =
			"update PACKAGE set PACKAGE_NAME = ?, FOLDER = ? where PACKAGE_ID = ?";

	public String getSql() {
		return SQL_STATEMENT;
	}

	// PACKAGE_NAME
	private String packageName;
	public void setPackageName(String packageName){this.packageName = packageName;}
	public String getPackageName(){return packageName;}

	// FOLDER
	private String folder;
	public void setFolder(String folder){this.folder = folder;}
	public String getFolder(){return folder;}

	// PACKAGE_ID
	private Integer packageId;
	public void setPackageId(Integer packageId){this.packageId = packageId;}
	public Integer getPackageId(){return packageId;}


	public void addBach(PreparedStatement preparedStatement) throws SQLException{

		preparedStatement.setObject(1, getPackageName(), 12);
		preparedStatement.setObject(2, getFolder(), 12);
		preparedStatement.setObject(3, getPackageId(), 4);

		preparedStatement.addBatch();
	}
}
