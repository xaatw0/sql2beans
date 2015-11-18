package sql2bean;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.h2.tools.Server;
import org.junit.Test;

public class H2Test {

	@Test
	public void start() throws SQLException, ClassNotFoundException{

		Server server = Server.createTcpServer().start();

		Class.forName("org.h2.Driver");
        Connection conn = DriverManager.
        getConnection("jdbc:h2:~/test", "sa", "");

        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE IF EXISTS USER;");
        statement.execute("CREATE TABLE USER (ID INT, NAME VARCHAR(50));");
        statement.execute("INSERT INTO USER VALUES(1,'test1');");
        statement.execute("INSERT INTO USER VALUES(2,'test2');");

        ResultSet resultSet = statement.executeQuery("SELECT ID,NAME FROM USER ORDER BY ID;");

        assertThat(resultSet.next(), is(true));
        assertThat(resultSet.getInt("ID"), is(1));
        assertThat(resultSet.getString("NAME"), is("test1"));

        assertThat(resultSet.next(), is(true));
        assertThat(resultSet.getInt("ID"), is(2));
        assertThat(resultSet.getString("NAME"), is("test2"));

        assertThat(resultSet.next(), is(false));

        ResultSetMetaData meta = resultSet.getMetaData();
        assertThat(meta.getColumnCount(), is(2));

        assertThat(meta.getColumnLabel(1), is("ID"));
        assertThat(meta.getColumnType(1), is(Types.INTEGER));

        assertThat(meta.getColumnLabel(2), is("NAME"));
        assertThat(meta.getColumnType(2), is(Types.VARCHAR));

        conn.close();
		server.stop();
	}

	@Test
	public void noDataMeta() throws SQLException, ClassNotFoundException{

		Server server = Server.createTcpServer().start();

		Class.forName("org.h2.Driver");
        Connection conn = DriverManager.
            getConnection("jdbc:h2:~/test", "sa", "");

        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE  IF EXISTS USER;");
        statement.execute("CREATE TABLE USER (ID INT, NAME VARCHAR(50));");

        ResultSet resultSet = statement.executeQuery("SELECT ID,NAME FROM USER ORDER BY ID;");

        assertThat(resultSet.next(), is(false));

        ResultSetMetaData meta = resultSet.getMetaData();
        assertThat(meta.getColumnCount(), is(2));

        assertThat(meta.getColumnLabel(1), is("ID"));
        assertThat(meta.getColumnType(1), is(Types.INTEGER));

        assertThat(meta.getColumnLabel(2), is("NAME"));
        assertThat(meta.getColumnType(2), is(Types.VARCHAR));

        conn.close();
		server.stop();
	}

}
