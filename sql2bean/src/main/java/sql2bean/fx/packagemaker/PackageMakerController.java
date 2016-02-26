package sql2bean.fx.packagemaker;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sql2bean.dao.table.PackageSelect;
import sql2bean.fx.IPanel;

import com.google.inject.Inject;

public class PackageMakerController implements Initializable, IPanel<PackageSelect.Data>{

	@Inject
	private PackageMakerLogic logic;

	@FXML private TextField txtPackage;

	@FXML private TextField txtDirectory;

	public static final String FXML_NAME = "PackageMaker.fxml";

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		logic.packageName().bind(txtPackage.textProperty());
		logic.directoryPath().bindBidirectional(txtDirectory.textProperty());
	}

	@FXML
	public void btnSavePressed(ActionEvent event) throws SQLException{

		logic.save();
    	close();
	}

	@FXML
	public void btnCancelPressed(ActionEvent event){
		close();
	}

	private void close(){
		((Stage) txtPackage.getScene().getWindow()).close();
	}

	@FXML
	public void btnChooseFolder(ActionEvent event){
		logic.chooseDirectory();
	}

	@Override
	public PackageSelect.Data getData() {

		return logic.get();
	}

	@Override
	public void setData(PackageSelect.Data data) {

		logic.set(data);
	}
}
