package sql2bean.fx.packagemaker;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sql2bean.beans.PackageBean;

public class PackageMakerController implements Initializable{

	@FXML private TextField txtPackage;

	@FXML private TextField txtDirectory;

	private PackageBean selectedPackage;

	private boolean isSave;

	public static final String FXML_NAME = "PackageMaker.fxml";

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	@FXML
	public void btnSavePressed(ActionEvent event){

		isSave = true;
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
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("該当パッケージを出力するディレクトリを選択してください");

	    File file = chooser.showDialog(new Stage());
	    if (file != null){
	    	txtDirectory.textProperty().set(file.getAbsolutePath());
	    }
	}

	public void createNewPackage(){
		setData(new PackageBean());
	}

	public void setData(PackageBean data){

    	selectedPackage.packageNameProperty().bindBidirectional(txtPackage.textProperty());
    	selectedPackage.folderProperty().bindBidirectional(txtDirectory.textProperty());
	}

	public PackageBean getSelectedPackage(){
		return isSave ? selectedPackage: null;
	}
}
