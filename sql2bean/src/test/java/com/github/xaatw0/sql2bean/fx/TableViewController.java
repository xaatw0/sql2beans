package com.github.xaatw0.sql2bean.fx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewController  implements Initializable{


	@FXML private TableView<Person> tableView;


	public void initialize(URL location, ResourceBundle resources) {

		TableColumn<Person, String> colFirstName = new TableColumn<>("First Name");
		colFirstName.setPrefWidth(80);
		colFirstName.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));

		TableColumn<Person, String> colLastName = new TableColumn<>("Last Name");
		colLastName.setPrefWidth(80);
		colLastName.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));

		TableColumn<Person, Integer> colAge = new TableColumn<>("Age");
		colAge.setPrefWidth(80);
		colAge.setCellValueFactory(new PropertyValueFactory<Person,Integer>("age"));

		tableView.getColumns().addAll(colFirstName, colLastName, colAge);
		tableView.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends Person> observale, Person oldValue, Person newValue)->{

					Person selectedPerson = newValue;
					System.out.println(selectedPerson.toString());

				});

		ObservableList<Person> list = FXCollections.observableArrayList();
		list.add(new Person("Michel", "Jacson",40));
		list.add(new Person("Miles", "Davis",30));

		tableView.setItems(list);
	}


	public class Person{

		private StringProperty firstName;

		public StringProperty firstNameProperty(){
			if (firstName == null)
				firstName = new SimpleStringProperty("First Name");
			return firstName;
		}

		public void setFirstName(String value){
			firstNameProperty().set(value);;
		}

		public String getFirstName(){
			return firstNameProperty().get();
		}

		private StringProperty lastName;

		public StringProperty lastNameProperty(){
			if (lastName == null)
				lastName = new SimpleStringProperty("Last Name");
			return lastName;
		}

		public void setLastName(String value){
			lastNameProperty().set(value);;
		}

		public String getLastName(){
			return lastNameProperty().get();
		}

		private int age;

		public void setAge(int age){
			this.age = age;
		}

		public int getAge(){
			return age;
		}

		public Person(String firstName, String lastName, int age){
			setFirstName(firstName);
			setLastName(lastName);
			setAge(age);
		}

		@Override
		public String toString(){
			return "Person[" + firstName.get() + "," + lastName.get() + "," + age +"]";
		}

	}

}
