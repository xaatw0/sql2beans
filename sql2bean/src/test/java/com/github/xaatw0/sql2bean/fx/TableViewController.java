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
	@FXML private TableView tableView;

	public void initialize(URL location, ResourceBundle resources) {

		TableColumn colFirstName = new TableColumn("First Name");
		colFirstName.setPrefWidth(80);
		colFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));

		TableColumn colLastName = new TableColumn("Last Name");
		colLastName.setPrefWidth(80);
		colLastName.setCellValueFactory(new PropertyValueFactory("lastName"));

		tableView.getColumns().addAll(colFirstName, colLastName);
		tableView.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue observale, Object oldValue, Object newValue)->{

					Person selectedPerson = (Person) newValue;

				});

		ObservableList<Person> list = FXCollections.observableArrayList();
		list.add(new Person("Michel", "Jacson"));
		list.add(new Person("Miles", "Davis"));

		tableView.setItems(list);
	}


	public class Person{
		private StringProperty firstName;
		private StringProperty lastName;

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

		public Person(String firstName, String lastName){
			setFirstName(firstName);
			setLastName(lastName);
		}

	}

}
