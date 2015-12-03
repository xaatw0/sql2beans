package sql2bean.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISQLSelect<T> extends ISQLQuery{
	T convert(ResultSet result) throws SQLException;
}
