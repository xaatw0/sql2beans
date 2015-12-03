package sql2bean.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ISQLBatch extends IQuery{
	void addBach(PreparedStatement preparedStatement) throws SQLException;
}
