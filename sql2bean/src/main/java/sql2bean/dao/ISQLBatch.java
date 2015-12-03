package sql2bean.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ISQLBatch extends ISQLQuery{
	void addBach(PreparedStatement preparedStatement) throws SQLException;
}
