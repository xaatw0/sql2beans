package sql2bean.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;

public class Sql2BeanDBTest {

	@Test
	public void testGetConnection() {

		try(Sql2BeanDB db = Sql2BeanDB.getConnection()){
		}
	}

	@Test
	public void testExecuteUpdate() {
		try(Sql2BeanDB db = Sql2BeanDB.getConnection()){

			assertThat(db.executeUpdate(new CreateTableSQLData()), is(not(Sql2BeanDB.SQL_FAILED)));
			assertThat(db.executeUpdate(new DropTableSQLData()), is(not(Sql2BeanDB.SQL_FAILED)));
		}
	}

/**
	@Test
	public void testExecuteUpdate2() throws SQLException {
		try(Sql2BeanDB db = Sql2BeanDB.getConnection()){

			db.executeUpdate(new CreateTableSQLData());

			SQLInsert data = new SQLInsert();
			PreparedStatement preparedStatement = db.preparedStatement(data.getSql());

			data.setId("TEST");
			data.setStatement("SELECT * FROM TEST");
			data.addBach(preparedStatement);

			data.setId("TEST2");
			data.setStatement("SELECT contents FROM TEST");
			data.addBach(preparedStatement);

			int[] result = preparedStatement.executeBatch();
			assertThat(result.length, is(2));

			List<SelectSQLData.Data> list = db.select(new SelectSQLData());
			assertThat(list.size(), is(2));

			assertThat(list.get(0).getId(), is("TEST"));
			assertThat(list.get(0).getStatement(), is("SELECT * FROM TEST"));

			assertThat(list.get(1).getId(), is("TEST2"));
			assertThat(list.get(1).getStatement(), is("SELECT contents FROM TEST"));

			db.executeUpdate(new DropTableSQLData());
		}
	}*/
}
