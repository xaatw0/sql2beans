package com.github.xaatw0.sql2bean.fx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class FXController implements Initializable{

	@FXML private TextArea txtSql;
	@FXML private TableView tableResult;
	@FXML private Button btnExecute;
	@FXML private Button btnSave;

	private StringProperty sql = new SimpleStringProperty();

	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	public void execute(ActionEvent event){
	}

	@FXML
	public void save(ActionEvent event){
	}
}
