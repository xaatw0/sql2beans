package sql2bean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sql2bean.sql.DataType;

public class testclass {

	// original SQL
	//   SELECT ID, FULL_NAME, MONEY FROM USER WHERE ID = ${ID}

	private final static String SQL_STATEMENT =
			"SELECT ID, FULL_NAME, MONEY FROM USER WHERE ID = ? ";


	public String getSql() {
		return SQL_STATEMENT;
	}

	// ID
	private Integer id;
	public void setId(Integer id){ this.id = id;}
	public Integer getId(){ return id;}



	public void setParameters(PreparedStatement preparedStatement) throws SQLException{
		preparedStatement.setObject(1, getId(), 4);
		preparedStatement.setObject(1, getId(), 4);
	}

	public class Data{

		// ID
		private Integer id;
		public void setId(Integer id){ this.id = id;}
		public Integer getId(){ return id;}

		// FULL_NAME
		private String fullName;
		public void setFullName(String fullName){ this.fullName = fullName;}
		public String getFullName(){ return fullName;}

		// MONEY
		private Integer money;
		public void setMoney(Integer money){ this.money = money;}
		public Integer getMoney(){ return money;}
	}


	public Data convert(ResultSet result) throws SQLException {

		Data data = new Data();

		data.setId((Integer)result.getObject(1, Integer.class));
		//data.setId(result.getInteger(1));
		data.setFullName(result.getString(2));

		//data.setMoney(result.getInteger(3));
		data.setMoney((Integer)result.getObject(1, DataType.Integer.getClazz()));


		return data;
	}
}
