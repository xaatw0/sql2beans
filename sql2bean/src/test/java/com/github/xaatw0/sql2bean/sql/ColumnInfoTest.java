package com.github.xaatw0.sql2bean.sql;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.activation.UnsupportedDataTypeException;

import org.h2.tools.Server;
import org.junit.Test;

public class ColumnInfoTest {

	@Test
	public void integer() {

		ColumnInfo info = new ColumnInfo("USER_ID", DataType.Integer);
		assertThat(info.getName(), is( "USER_ID"));
		assertThat(info.getType(), is(DataType.Integer));
	}

	@Test
	public void string() {

		ColumnInfo info = new ColumnInfo("USER_ID", DataType.String);
		assertThat(info.getName(), is( "USER_ID"));
		assertThat(info.getType(), is(DataType.String));
	}

	@Test
	public void getCamelName() {
		ColumnInfo info = new ColumnInfo("USER_ID", DataType.Integer);
		assertThat(info.getName(), is( "USER_ID"));
		assertThat(info.getCamelName(), is( "userId"));
	}

	@Test
	public void getPascalName() {
		ColumnInfo info = new ColumnInfo("USER_ID", DataType.Integer);
		assertThat(info.getPascalName(), is( "UserId"));
	}


	@Test
	public void start() throws SQLException, ClassNotFoundException, UnsupportedDataTypeException{

		Server server = Server.createTcpServer().start();

		Class.forName("org.h2.Driver");
        Connection conn = DriverManager.
            getConnection("jdbc:h2:~/test", "sa", "");

        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE USER IF EXISTS;");
        statement.execute("CREATE TABLE USER (ID INT, NAME VARCHAR(50));");

        ResultSet resultSet = statement.executeQuery("SELECT ID,NAME FROM USER ORDER BY ID;");

        ResultSetMetaData meta = resultSet.getMetaData();

        ColumnInfo[] target = ColumnInfo.createColumnInfo(meta);

        assertThat(target.length, is(2));

        assertThat(target[0].getType(), is(DataType.Integer));
        assertThat(target[0].getName(), is("ID"));

        assertThat(target[1].getType(), is(DataType.String));
        assertThat(target[1].getName(), is("NAME"));

        conn.close();
		server.stop();
	}
}
