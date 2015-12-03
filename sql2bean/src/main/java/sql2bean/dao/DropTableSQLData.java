package sql2bean.dao;

public class DropTableSQLData implements IQuery {

	private final static String SQL_STATEMENT =
			"drop table SQL_DATA";

	@Override
	public String getSql() {
		return SQL_STATEMENT;
	}
}
