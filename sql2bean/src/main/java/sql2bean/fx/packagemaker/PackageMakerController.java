package sql2bean.fx.packagemaker;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sql2bean.beans.PackageBean;

public class PackageMakerController implements Initializable{

	private Stage thisStage;

	@FXML private TextField txtPackage;

	@FXML private TextField txtDirectory;

	private PackageBean selectedPackage;

	private final String FXML_NAME = "PackageMaker.fxml";

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	@FXML
	public void btnSavePressed(ActionEvent event){

    	if (selectedPackage == null){
    		selectedPackage = new PackageBean();
    	}

    	selectedPackage.packageNameProperty().set(txtPackage.textProperty().get());
    	selectedPackage.folderProperty().set(txtDirectory.textProperty().get());
    	thisStage.close();
	}

	@FXML
	public void btnCancelPressed(ActionEvent event){
		selectedPackage = null;
		thisStage.close();
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

	public void setData(PackageBean data){
		this.selectedPackage = data;
	}

	public PackageBean getData(){
		return selectedPackage;
	}

	public Optional<PackageBean> openWindow(Stage primaryStage){

		thisStage = new Stage();
		thisStage.initOwner(primaryStage);

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource(FXML_NAME).openStream());
			Scene scene = new Scene(loader.getRoot());
			thisStage.setScene(scene);

		} catch(Exception e) {
			e.printStackTrace();
		}

		thisStage.showAndWait();
		return  selectedPackage == null ? Optional.empty(): Optional.of(selectedPackage);
	}
}
