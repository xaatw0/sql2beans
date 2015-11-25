package sql2bean.beans;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sql2bean.sql.DataType;

public class SQLKeyValue {

	private StringProperty key = new SimpleStringProperty();
	public StringProperty keyProperty(){ return key;}
	public void setKey(String key){ keyProperty().set(key);}
	public String getKey(){ return keyProperty().get();}

	private StringProperty value = new SimpleStringProperty();
	public StringProperty valueProperty() { return value;}
	public void setValue(String value){ valueProperty().set(value);}
	public String getValue(){ return valueProperty().get();}

	private ObjectProperty<DataType> type = new SimpleObjectProperty<>();
	public ObjectProperty<DataType> typeProperty(){return type;}
	public void setType(DataType type){ this.type.set(type);}
	public DataType getType(){ return this.type.get();}

	public SQLKeyValue() {
	}

	public SQLKeyValue(String key){
		this.key.set(key);
		this.type.set(DataType.String);
	}

	public SQLKeyValue(String key,DataType type, String value){
		this.key.set(key);
		this.type.set(type);
		this.value.set(value);
	}

	public boolean isSameKey(String anotherKey){
		return anotherKey.equals(key.get());
	}

	@Override
	public String toString(){
		return "(" + key.get() + "," + value.get() + ")";
	}
}
