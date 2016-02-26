package sql2bean.fx.packagemaker;

import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sql2bean.dao.table.PackageSelect;

public class PackageMakerLogicDummy implements PackageMakerLogic{

	private StringProperty packageName = new SimpleStringProperty();

	private StringProperty directoryPath = new SimpleStringProperty();

	private IntegerProperty appId = new SimpleIntegerProperty();

	@Override
	public StringProperty packageName() {
		return packageName;
	}

	@Override
	public StringProperty directoryPath() {
		return directoryPath;
	}

	@Override
	public int save() throws SQLException {
		System.out.println("save packageName:"+packageName().get() + " directoryPath:"+ directoryPath().get() + " appId:" + appId().get());
		return -1;
	}

	@Override
	public void set(PackageSelect.Data data) {
	}

	@Override
	public PackageSelect.Data get() {
		return null;
	}

	@Override
	public void chooseDirectory() {
		System.out.println("push chooseDirectory");
	}

	@Override
	public IntegerProperty appId() {
		return appId;
	}


}
