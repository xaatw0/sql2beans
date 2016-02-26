package sql2bean.fx.packagemaker;

import java.io.File;
import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sql2bean.dao.table.PackageSelect;
import sql2bean.dao.table.PackageSelect.Data;
import sql2bean.db.IDBInfo;

public class PackageMakerLogicImpl implements PackageMakerLogic {

	private IDBInfo db;

	private Data data;

	private StringProperty packageName = new SimpleStringProperty();

	private StringProperty directoryPath = new SimpleStringProperty();

	private IntegerProperty appId = new SimpleIntegerProperty();

	public PackageMakerLogicImpl(IDBInfo db){
		this.db = db;
	}

	@Override
	public StringProperty packageName() {
		return packageName;
	}

	@Override
	public StringProperty directoryPath() {
		return directoryPath;
	}

	@Override
	public IntegerProperty appId() {
		return appId;
	}

	@Override
	public void chooseDirectory() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("該当パッケージを出力するディレクトリを選択してください");

	    File file = chooser.showDialog(new Stage());
	    if (file != null){
	    	directoryPath.set(file.getAbsolutePath());
	    }
	}

	@Override
	public int save() throws SQLException {
		if (data == null){

			init(data = new Data());

			int id = db.insertPackage(data);
			data.setPackageId(id);

		} else {

			init(data);
			db.updatePackage(data);
		}

		return data.getPackageId();
	}

	private void init(Data data){
		data.setFolder(directoryPath().get());
		data.setAppId(appId().get());
		data.setPackageName(packageName().get());
	}

	@Override
	public void set(PackageSelect.Data data) {

		this.data = data;

		if (data != null){
			directoryPath().set(data.getFolder());
			appId().set(data.getAppId());
			packageName().set(data.getPackageName());
		}
	}

	@Override
	public PackageSelect.Data get() {
		return data;
	}
}
