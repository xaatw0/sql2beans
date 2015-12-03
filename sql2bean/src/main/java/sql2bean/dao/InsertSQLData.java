package sql2bean.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class InsertSQLData implements ISQLBatch{

	// insert into SQL_DATA (ID, STATEMENT) values ({$ID},{$STATEMENT})

	private final static String SQL_STATEMENT =
			"insert into SQL_DATA (ID, STATEMENT) values (?,?)";

	private String id;
	private String statement;

	@Override
	public String getSql() {
		return SQL_STATEMENT;
	}

	public void setId(String id){
		this.id = id;
	}

	public void setStatement(String statement){
		this.statement = statement;
	}

	@Override
	public void addBach(PreparedStatement preparedStatement) throws SQLException{
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, statement);
		preparedStatement.addBatch();
	}
}
