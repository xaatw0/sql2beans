package sql2bean.db;

import java.io.Closeable;
import java.sql.Connection;

public interface IMainDB extends Closeable{

	Connection getConnection();
}
