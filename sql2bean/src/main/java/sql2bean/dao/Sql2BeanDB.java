package sql2bean.dao;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;


public class Sql2BeanDB implements Closeable{

	public final static int SQL_FAILED = -1;

	private static Sql2BeanDB instance = new Sql2BeanDB();

	private Server server;
	private Connection conn;

	private Sql2BeanDB() {
	}

	public static Sql2BeanDB getConnection() {

		if (instance.conn == null){
			instance.initialize();
		}
		return instance;
	}

	private void initialize() {
		if (conn == null){
			try {
				server = Server.createTcpServer().start();
				Class.forName("org.h2.Driver");
				conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close(){
        try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        conn = null;
		server.stop();
		server = null;
	}

	public int executeUpdate(IQuery query){

		try {
			Statement statement = conn.createStatement();
			return  statement.executeUpdate(query.getSql());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return SQL_FAILED;
	}

	public <T> List<T> select(ISQLSelect<T> query){

		try {
			PreparedStatement statement = conn.prepareStatement(query.getSql());
			ResultSet result = statement.executeQuery();

			List<T> list = new ArrayList<T>();

			while(result.next()){
				list.add(query.convert(result));
			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public PreparedStatement preparedStatement(String sql) throws SQLException{
		return conn.prepareStatement(sql);
	}
}
