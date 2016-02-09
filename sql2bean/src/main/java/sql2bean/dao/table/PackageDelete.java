package sql2bean.dao.table;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PackageDelete {
	// original SQL
	//   delete from PACKAGE where PACKAGE_ID = ${PACKAGE_ID}

	private final static String SQL_STATEMENT =
			"delete from PACKAGE where PACKAGE_ID = ?";

	public String getSql() {
		return SQL_STATEMENT;
	}

	// PACKAGE_ID
	private Integer packageId;
	public void setPackageId(Integer packageId){this.packageId = packageId;}
	public Integer getPackageId(){return packageId;}


	public void addBach(PreparedStatement preparedStatement) throws SQLException{

		preparedStatement.setObject(1, getPackageId(), 4);

		preparedStatement.addBatch();
	}
}
