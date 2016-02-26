package sql2bean.fx.packagemaker;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sql2bean.dao.table.PackageSelect.Data;
import sql2bean.db.DBInfo;
import sql2bean.db.IDBInfo;
import sql2bean.db.IMainDB;
import sql2bean.db.TestDB;

public class PackageMakerLogicImplTest {


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
		PackageMakerLogic logic = new PackageMakerLogicImpl(info);
		logic.appId().set(11);
		logic.directoryPath().set("directoryPath");
		logic.packageName().set("packageName");
		final int id11 = logic.save();

		logic.set(null);
		logic.appId().set(22);
		logic.directoryPath().set("directoryPath2");
		logic.packageName().set("packageName2");
		final int id22 = logic.save();

		assertThat(id11, is(not(id22)));


		// 内容を確認
		List<Data> result = info.selectPackage();
		assertThat(result.size(), is(2));

		Data data = result.stream().filter(p -> p.getPackageId().equals(id11)).findAny().get();
		assertThat(data.getPackageId(), is(id11));
		assertThat(data.getAppId(), is(11));
		assertThat(data.getFolder(), is("directoryPath"));
		assertThat(data.getPackageName(), is("packageName"));

		data = result.stream().filter(p -> p.getPackageId().equals(id22)).findAny().get();
		assertThat(data.getPackageId(), is(id22));
		assertThat(data.getAppId(), is(22));
		assertThat(data.getFolder(), is("directoryPath2"));
		assertThat(data.getPackageName(), is("packageName2"));


		// データを更新
		data = info.selectPackage().stream().filter(p -> p.getPackageId().equals(id11)).findAny().get();
		logic.set(data);
		logic.directoryPath().set("directoryPathUpdate");
		logic.packageName().set("packageNameUpdate");
		logic.save();

		// 更新内容を確認
		result = info.selectPackage();
		assertThat(result.size(), is(2));

		data = result.stream().filter(p -> p.getPackageId().equals(id11)).findAny().get();
		assertThat(data.getPackageId(), is(id11));
		assertThat(data.getAppId(), is(11));
		assertThat(data.getFolder(), is("directoryPathUpdate"));
		assertThat(data.getPackageName(), is("packageNameUpdate"));

		data = result.stream().filter(p -> p.getPackageId().equals(id22)).findAny().get();
		assertThat(data.getPackageId(), is(id22));
		assertThat(data.getAppId(), is(22));
		assertThat(data.getFolder(), is("directoryPath2"));
		assertThat(data.getPackageName(), is("packageName2"));

		// 削除して、削除されたことを確認
		data = result.stream().filter(p -> p.getPackageId().equals(id11)).findAny().get();
		logic.set(data);
		logic.delete();

		result = info.selectPackage();
		assertThat(result.size(), is(1));
		assertThat(result.stream().filter(p -> p.getPackageId().equals(id11)).findAny().isPresent(), is(false));
		assertThat(result.stream().filter(p -> p.getPackageId().equals(id22)).findAny().isPresent(), is(true));
	}

	@Test
	public void test() {
		fail("まだ実装されていません");
	}

}
