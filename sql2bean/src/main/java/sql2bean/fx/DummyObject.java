package sql2bean.fx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DummyObject {
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private IntegerProperty money = new SimpleIntegerProperty();

	public IntegerProperty idProperty(){ return id;}
	public int getId(){return idProperty().get();}
	public void setId(int id){ idProperty().set(id);}

	public StringProperty fullNameProperty(){return name;}
	public String getFullName(){ return fullNameProperty().get();}
	public void setFullName(String name){ fullNameProperty().set(name);}

	public IntegerProperty moneyProperty(){ return money;}
	public int getMoney(){return moneyProperty().get();}
	public void setMoney(int id){ moneyProperty().set(id);}

	public DummyObject(int id, String name, int moeny){
		setId(id);
		setFullName(name);
		setMoney(moeny);
	}
}
