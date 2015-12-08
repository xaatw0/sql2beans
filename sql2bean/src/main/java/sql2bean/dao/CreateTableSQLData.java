package sql2bean.dao;


public class CreateTableSQLData implements ISQLQuery {

	private final static String SQL_STATEMENT =
			"CREATE TABLE IF NOT EXISTS  SQL_DATA ("
					+" ID VARCHAR(20) PRIMARY KEY,"
					+" STATEMENT VARCHAR(500) not null"
					+");";

	@Override
	public String getSql(){
		return SQL_STATEMENT;
	}
}
