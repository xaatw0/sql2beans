package com.github.xaatw0.sql2bean.fx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DummyObject {
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();

	public IntegerProperty id(){ return id;}
	public int getId(){return id().get();}
	public void setId(int id){ id().set(id);}

	public StringProperty name(){return name;}
	public String getName(){ return name().get();}
	public void setName(String name){ name().set(name);}

	public DummyObject(int id, String name){
		setId(id);
		setName(name);
	}
}
