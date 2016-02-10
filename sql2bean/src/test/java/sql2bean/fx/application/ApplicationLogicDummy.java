package sql2bean.fx.application;

import java.sql.SQLException;
import java.util.List;

import sql2bean.dao.table.ApplicationSelect.Data;


public class ApplicationLogicDummy implements ApplicationLogic{

	@Override
	public void cancel() {
		System.out.println("cancel");
	}

	@Override
	public void delete(Data data) {
		System.out.println("delete");
	}

	@Override
	public List<Data> select() throws SQLException {
		System.out.println("select");
		return null;
	}

	@Override
	public int save(Data data) throws SQLException {
		System.out.println("save name:" + data.getAppName() + " dbName:" + data.getDbName() + " dbConnection:" + data.getDbConnection());
		return 0;
	}
}
