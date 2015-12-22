package sql2bean.beans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;

public class PackageBean {

	public final static PackageBean NEW_PACKAGE = new PackageBean();
	static {
		NEW_PACKAGE.setSqlId(-1);
	}

	private IntegerProperty sqlSqlId = new SimpleIntegerProperty(-1);
	public IntegerProperty sqlIdProperty() { return sqlSqlId;}
	public void setSqlId(int sqlId){sqlIdProperty().set(sqlId);}
	public int getSqlId(){return sqlIdProperty().get();}

	private StringProperty packageName = new SimpleStringProperty();
	public StringProperty packageNameProperty() {return packageName;}
	public void setPackageName(String packageName){packageNameProperty().set(packageName);}
	public String getPackageName() {return packageNameProperty().get();}

	private StringProperty folder = new SimpleStringProperty();
	public StringProperty folderProperty() {return folder;}
	public void setFolder(String folder){folderProperty().set(folder);}
	public String getFolder() {return folderProperty().get();}

	public static class ComboboxMenuConverter extends StringConverter<PackageBean>{

		@Override
		public String toString(PackageBean object) {

			if (object == null) return "";

			return object.getPackageName();
		}

		@Override
		public PackageBean fromString(String string) {

			NEW_PACKAGE.setPackageName(string);
			return NEW_PACKAGE;
		}
	}
}
