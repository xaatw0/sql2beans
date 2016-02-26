package sql2bean.fx.packagemaker;

import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import sql2bean.dao.table.PackageSelect.Data;

public interface PackageMakerLogic {

	StringProperty packageName();

	StringProperty directoryPath();

	IntegerProperty appId();

	void chooseDirectory();

	int save() throws SQLException;

	boolean delete() throws SQLException;

	void set(Data data);

	Data get();
}
