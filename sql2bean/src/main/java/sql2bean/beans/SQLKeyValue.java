package sql2bean.beans;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SQLKeyValue {

	private StringProperty key = new SimpleStringProperty();
	public StringProperty key(){ return key;}
	public void setKey(String key){ key().set(key);}
	public String getKey(){ return key.get();}

	private StringProperty value = new SimpleStringProperty();
	public StringProperty value() { return value;}
	public void setValue(String value){ value().set(value);}
	public String getValue(){ return value().get();}
}
