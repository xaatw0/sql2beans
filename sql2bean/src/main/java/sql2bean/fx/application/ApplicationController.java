package sql2bean.fx.application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import com.google.inject.Inject;

public class ApplicationController implements Initializable{

	@Inject
	private ApplicationLogic logic;

	@FXML
	private TextField txtAppName;

	@FXML
	private TextField txtDbName;

	@FXML
	private TextField txtDbConnection;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	@FXML
	public void btnSaveClicked(ActionEvent event) throws SQLException{
		logic.save(txtAppName.getText(), txtDbName.getText(), txtDbConnection.getText());
	}

	@FXML
	public void btnCancelClicked(ActionEvent event){
		logic.cancel();
	}

	@FXML
	public void btnDeleteClicked(ActionEvent event) throws SQLException{
		logic.delete();
	}
}
