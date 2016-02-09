package sql2bean.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

public class TestDB implements IMainDB {

	private static TestDB instance = new TestDB();

	private Server server;
	private Connection conn;

	public static IMainDB open(){

		instance.initialize();
		return instance;
	}

	@Override
	public void close() throws IOException {
		try {
			if(conn != null && ! conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn = null;
	}

	@Override
	public Connection getConnection() {
		return conn;
	}

	private void initialize(){
		if (conn == null){
			try {
				server = Server.createTcpServer().start();
				Class.forName("org.h2.Driver");
				conn = DriverManager.getConnection("jdbc:h2:~/sql2bean", "sa", "");

				// テーブルの作成
				InputStream stream = getClass().getClassLoader().getResourceAsStream("sql2bean\\fx\\CreateTable.sql");
				byte[] data = new byte[stream.available()];
				stream.read(data);

				try(Statement statement = conn.createStatement()){
					statement.execute(new String(data));
				}

			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

}
