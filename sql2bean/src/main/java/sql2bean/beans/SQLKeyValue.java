package sql2bean.beans;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SQLKeyValue {

	private StringProperty key = new SimpleStringProperty();
	public StringProperty keyProperty(){ return key;}
	public void setKey(String key){ keyProperty().set(key);}
	public String getKey(){ return keyProperty().get();}

	private StringProperty value = new SimpleStringProperty();
	public StringProperty valueProperty() { return value;}
	public void setValue(String value){ valueProperty().set(value);}
	public String getValue(){ return valueProperty().get();}

	public SQLKeyValue() {
	}

	public SQLKeyValue(String key, String value){
		this.key.set(key);
		this.value.set(value);
	}

	@Override
	public String toString(){
		return "(" + key.get() + "," + value.get() + ")";
	}
}
