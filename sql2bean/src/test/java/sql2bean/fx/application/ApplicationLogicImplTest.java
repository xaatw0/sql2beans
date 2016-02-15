package sql2bean.fx.application;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sql2bean.dao.table.ApplicationSelect.Data;
import sql2bean.db.DBInfo;
import sql2bean.db.IDBInfo;
import sql2bean.db.IMainDB;
import sql2bean.db.TestDB;

public class ApplicationLogicImplTest {

	private IDBInfo info;

	private IMainDB db;

	@Before
	public void tearUp(){
		info = new DBInfo(db = TestDB.open());
	}

	@After
	public void tearDown() throws SQLException, IOException{

		db.close();
	}

	@Test
	public void load() throws SQLException {

		assertThat(db, is(notNullValue()));
		assertThat(info, is(notNullValue()));

		// データを挿入
		ApplicationLogic logic = new ApplicationLogicImpl(info);
		logic.appName().set("name");
		logic.dbName().set("dbName");
		logic.dbConnection().set("dbConnection");
		int id = logic.save();

		// 内容を確認
		Data data = info.selectApplication().stream().filter(p-> p.getAppId().equals(id)).findAny().get();
		assertThat(data.getAppId(), is(id));
		assertThat(data.getAppName(), is("name"));
		assertThat(data.getDbName(), is("dbName"));
		assertThat(data.getDbConnection(), is("dbConnection"));

		// データを更新
		logic.appName().set("name1");
		logic.dbName().set("dbName2");
		logic.dbConnection().set("dbConnection3");
		logic.save();

		// 更新内容を確認
		data = info.selectApplication().stream().filter(p-> p.getAppId().equals(id)).findAny().get();
		assertThat(data.getAppId(), is(id));
		assertThat(data.getAppName(), is("name1"));
		assertThat(data.getDbName(), is("dbName2"));
		assertThat(data.getDbConnection(), is("dbConnection3"));

		// 削除して、削除されたことを確認
		logic.delete();
		assertThat(info.selectApplication().stream().filter(p-> p.getAppId().equals(id)).findAny().isPresent(), is(false));
	}

	@Test
	public void set() throws SQLException{

		ApplicationLogic logic = new ApplicationLogicImpl(info);
		assertThat(logic.get(), is(nullValue()));

		Data data = new Data();
		data.setAppName("appName");
		data.setDbName("dbName");
		data.setDbConnection("dbConnection");

		logic.set(data);
		assertThat(logic.get(), is(notNullValue()));

		logic.cancel();
		assertThat(logic.get(), is(nullValue()));
	}
}
