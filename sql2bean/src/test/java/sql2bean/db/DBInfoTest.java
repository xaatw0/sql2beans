package sql2bean.db;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sql2bean.dao.table.PackageInsert;
import sql2bean.dao.table.PackageSelect;

public class DBInfoTest {

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
	public void selectApplication() {
		fail("まだ実装されていません");
	}

	@Test
	public void insertApplication() {
		fail("まだ実装されていません");
	}

	@Test
	public void updateApplication() {
		fail("まだ実装されていません");
	}

	@Test
	public void deleteApplication() {
		fail("まだ実装されていません");
	}

	@Test
	public void packageTable() throws SQLException{
		Connection con = db.getConnection();

		List<PackageSelect.Data> result = info.selectPackage();
		assertThat(result.size(), is(0));

		PackageInsert insert = new PackageInsert();
		PreparedStatement statement = con.prepareStatement(insert.getSql());

		PackageSelect.Data data = new PackageSelect.Data();
		data.setAppId(11);
		data.setFolder("folder");
		data.setPackageName("packageName");
		info.insertPackage(data);

		insert.addBach(statement);
		statement.executeBatch();



	}

}
