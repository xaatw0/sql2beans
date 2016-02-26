package sql2bean.dao.table;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApplicationDelete {

	// original SQL
	//   delete from  APPLICATION where APP_ID = ${APP_ID}

	private final static String SQL_STATEMENT =
			"delete from  APPLICATION where APP_ID = ?";

	public String getSql() {
		return SQL_STATEMENT;
	}

	// APP_ID
	private Integer appId;
	public void setAppId(Integer appId){this.appId = appId;}
	public Integer getAppId(){return appId;}


	public void addBach(PreparedStatement preparedStatement) throws SQLException{

		preparedStatement.setObject(1, getAppId(), 4);

		preparedStatement.addBatch();
	}
}
