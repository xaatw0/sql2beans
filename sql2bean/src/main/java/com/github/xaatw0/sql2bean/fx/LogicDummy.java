package com.github.xaatw0.sql2bean.fx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

public class LogicDummy implements LogicInterface {

	private ResultSetMetaData metaData;

	public LogicDummy() {
		// TODO 自動生成されたコンストラクター・スタブ
	}



	@Override
	public ResultSet execute(String sql) {

		ResultSet result = null;

		try{
			Server server = Server.createTcpServer().start();

			Class.forName("org.h2.Driver");
	        Connection conn = DriverManager.
	            getConnection("jdbc:h2:~/test", "sa", "");

	        Statement statement = conn.createStatement();
	        statement.execute("DROP TABLE IF EXISTS USER;");
	        statement.execute("CREATE TABLE USER (ID INT, NAME VARCHAR(50));");
	        statement.execute("INSERT INTO USER VALUES(1,'test1');");
	        statement.execute("INSERT INTO USER VALUES(2,'test2');");

	        result = statement.executeQuery("SELECT ID,NAME FROM USER ORDER BY ID;");
			metaData = result.getMetaData();

	        conn.close();
			server.stop();

		}catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}

		return result;

	}

	@Override
	public void save(String sql) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public ResultSetMetaData getMetaData() {
		return metaData;
	}

}
