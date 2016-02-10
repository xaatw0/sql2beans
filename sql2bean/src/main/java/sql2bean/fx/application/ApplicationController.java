package sql2bean.fx.application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import sql2bean.dao.table.ApplicationSelect;
import sql2bean.dao.table.ApplicationSelect.Data;
import sql2bean.fx.IPanel;

import com.google.inject.Inject;

public class ApplicationController implements Initializable, IPanel<ApplicationSelect.Data>{

	@Inject
	private ApplicationLogic logic;

	private ApplicationSelect.Data data;

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

		if (data == null){
			data = new ApplicationSelect.Data();
		}

		data.setAppName(txtAppName.getText());
		data.setDbName(txtDbName.getText());
		data.setDbConnection(txtDbConnection.getText());

		int id = logic.save(data);
		data.setAppId(id);
	}

	@FXML
	public void btnCancelClicked(ActionEvent event){
		logic.cancel();
	}

	@FXML
	public void btnDeleteClicked(ActionEvent event) throws SQLException{
		logic.delete(data);
	}

	@Override
	public Data getData() {
		return data;
	}

	@Override
	public void setData(Data data) {
		this.data = data;
	}
}
