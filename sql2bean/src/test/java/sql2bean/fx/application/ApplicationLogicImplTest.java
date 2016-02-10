package sql2bean.fx.application;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sql2bean.dao.table.ApplicationSelect;
import sql2bean.db.TestDB;

public class ApplicationLogicImplTest {

	private Connection conn;

	@Before
	public void tearUp(){
		conn = TestDB.open().getConnection();
	}

	@After
	public void tearDown() throws SQLException{

		if (conn != null){
			conn.close();
			conn = null;
		}
	}

	@Test
	public void load() throws SQLException {

		assertThat(conn, is(notNullValue()));

		ApplicationLogic logic = new ApplicationLogicImpl(conn);

		// データを挿入
		ApplicationSelect.Data data = new ApplicationSelect.Data();
		data.setAppName("name");
		data.setDbName("dbName");
		data.setDbConnection("dbConnection");
		int id = logic.save(data);

		// 内容を確認
		data = logic.select().stream().filter(p-> p.getAppId().equals(id)).findAny().get();
		assertThat(data.getAppId(), is(id));
		assertThat(data.getAppName(), is("name"));
		assertThat(data.getDbName(), is("dbName"));
		assertThat(data.getDbConnection(), is("dbConnection"));

		// データを更新
		data.setAppName("name1");
		data.setDbName("dbName2");
		data.setDbConnection("dbConnection3");
		logic.save(data);

		// 更新内容を確認
		data = logic.select().stream().filter(p-> p.getAppId().equals(id)).findAny().get();
		assertThat(data.getAppId(), is(id));
		assertThat(data.getAppName(), is("name1"));
		assertThat(data.getDbName(), is("dbName2"));
		assertThat(data.getDbConnection(), is("dbConnection3"));

		// 削除して、削除されたことを確認
		logic.delete(data);
		assertThat(logic.select().stream().filter(p-> p.getAppId().equals(id)).findAny().isPresent(), is(false));
	}
}
