package sql2bean.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectSQLData implements ISQLSelect<SelectSQLData.Data>{

	private final static String SQL_STATEMENT =
			"select ID, STATEMENT from SQL_DATA";

	@Override
	public String getSql() {
		return SQL_STATEMENT;
	}

	public class Data{
		private String id;
		private String statement;

		private void setId(String id){this.id = id;}
		private void setStatement(String statement){ this.statement = statement;}
		public String getId(){ return id;}
		public String getStatement(){return statement;}
	}

	@Override
	public Data convert(ResultSet result) throws SQLException {

		Data data = new Data();
		data.setId(result.getString(1));
		data.setStatement(result.getString(2));

		return data;
	}
}
