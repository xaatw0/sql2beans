package sql2bean.fx.application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sql2bean.dao.table.ApplicationSelect;
import sql2bean.dao.table.ApplicationSelect.Data;
import sql2bean.fx.IPanel;

import com.google.inject.Inject;

/**
 * アプリケーション
 */
public class ApplicationController implements Initializable, IPanel<ApplicationSelect.Data>{

	@Inject
	private ApplicationLogic logic;

	@FXML
	private TextField txtAppName;

	@FXML
	private TextField txtDbName;

	@FXML
	private TextField txtDbConnection;

	@FXML
	private Button btnDelete;

	/** ボタンの非有効化の設定。初期データがない場合、非有効化される*/
	private BooleanProperty blnBtnDeleteDisabled = new SimpleBooleanProperty(true);

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		btnDelete.disableProperty().bind(blnBtnDeleteDisabled);

		logic.appName().bind(txtAppName.textProperty());
		logic.dbName().bind(txtDbName.textProperty());
		logic.dbConnection().bind(txtDbConnection.textProperty());
	}

	@FXML
	public void btnSaveClicked(ActionEvent event) throws SQLException{
		logic.save();
		close();
	}

	@FXML
	public void btnCancelClicked(ActionEvent event){
		close();
	}

	@FXML
	public void btnDeleteClicked(ActionEvent event) throws SQLException{
		logic.delete();
		close();
	}

	@Override
	public Data getData() {
		return logic.get();
	}

	@Override
	public void setData(Data data) {

		logic.set(data);
		blnBtnDeleteDisabled.set(data == null);
	}

	private void close(){
		((Stage) txtAppName.getScene().getWindow()).close();
	}
}
