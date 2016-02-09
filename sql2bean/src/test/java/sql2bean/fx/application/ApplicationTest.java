package sql2bean.fx.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ApplicationTest  extends Application {

	private final String FXML_NAME = "ApplicationMain.fxml";

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.load(ApplicationController.class.getResource(FXML_NAME).openStream());
			BorderPane root = loader.getRoot();
			Scene scene = new Scene(root);

			ApplicationLogic dummy = new ApplicationLogicDummy();

			ApplicationController controller = loader.getController();
	        Injector injector = Guice.createInjector(new AbstractModule() {
	            @Override protected void configure() {
	                bind(ApplicationLogic.class).toInstance(dummy);
	            }
	        });

	        injector.injectMembers(controller);
	        dummy.load(1);

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
