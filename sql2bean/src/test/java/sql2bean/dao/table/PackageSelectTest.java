package sql2bean.dao.table;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

import sql2bean.dao.Sql2BeanDB;

public class PackageSelectTest {

	@Test
	public void InsertSelectUpdateDelete() throws SQLException {
		try(Sql2BeanDB db = Sql2BeanDB.getConnection()){

			PackageInsert insert = new PackageInsert();
			PreparedStatement preparedStatement = db.preparedStatement(insert.getSql());

			insert.setFolder("folder1");
			insert.setPackageName("packageName1");
			insert.addBach(preparedStatement);

			insert.setFolder("folder2");
			insert.setPackageName("packageName2");
			insert.addBach(preparedStatement);

			int[] result = preparedStatement.executeBatch();
			assertThat(result.length, is(2));
			assertThat(IntStream.of(result).sum(), is(2));


			PackageSelect select = new PackageSelect();
			preparedStatement = db.preparedStatement(select.getSql());

			List<PackageSelect.Data> data = new ArrayList<>();
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				data.add(select.convert(resultSet));
			}

			assertThat(data.size(), is(2));

			assertThat(data.get(0).getFolder(), is("folder1"));
			assertThat(data.get(0).getPackageName(), is("packageName1"));
			assertThat(data.get(1).getFolder(), is("folder2"));
			assertThat(data.get(1).getPackageName(), is("packageName2"));
		}

	}

}
