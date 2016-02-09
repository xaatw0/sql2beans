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
		int id = logic.save("name", "dbName", "dbConnection");

		ApplicationSelect.Data data = logic.load(id);
		assertThat(data.getAppId(), is(id));
		assertThat(data.getAppName(), is("name"));
		assertThat(data.getDbName(), is("dbName"));
		assertThat(data.getDbConnection(), is("dbConnection"));

		logic.save("name1", "dbName2", "dbConnection3");
		data = logic.load(id);
		assertThat(data.getAppId(), is(id));
		assertThat(data.getAppName(), is("name1"));
		assertThat(data.getDbName(), is("dbName2"));
		assertThat(data.getDbConnection(), is("dbConnection3"));

		logic.delete();
		assertThat(logic.load(id), is(nullValue()));
	}
}
