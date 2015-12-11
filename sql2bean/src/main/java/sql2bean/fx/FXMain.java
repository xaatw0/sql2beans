package sql2bean.fx;


import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class FXMain<T> extends Application {

	FXController controller;

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource("FXMain.fxml").openStream());
			BorderPane root = loader.getRoot();
			Scene scene = new Scene(root);

			// メイン文の引数を取得する
			final List<String> args = getParameters().getRaw();

			primaryStage.setScene(scene);
			primaryStage.show();

			controller = loader.getController();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    @Override
    public void stop() {
    	controller.shutdown();
    }

	public static void main(String[] args) {
		launch(args);
	}
}
