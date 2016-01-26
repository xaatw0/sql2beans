package sql2bean.fx.packagemaker;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sql2bean.beans.PackageBean;


public class PackageMaker extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResourceAsStream(PackageMakerController.FXML_NAME));
			PackageMakerController controller = (PackageMakerController)loader.getController();

			Pane root = loader.getRoot();
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();

			PackageBean bean = controller.getData();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
