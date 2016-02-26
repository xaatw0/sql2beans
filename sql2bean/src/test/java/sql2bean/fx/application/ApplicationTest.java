package sql2bean.fx.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ApplicationTest  extends Application {

	private final String FXML_NAME = "ApplicationMain.fxml";

	@Override
	public void start(Stage primaryStage) {

		ApplicationLogic dummy = new ApplicationLogicDummy();
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override protected void configure() {
                bind(ApplicationLogic.class).toInstance(dummy);
            }
        });

		Callback<Class<?>, Object> guiceControllerFactory = new Callback<Class<?>, Object>() {
			  @Override
			  public Object call(Class<?> clazz) {
				  return injector.getInstance(clazz);
			  }
		};

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setControllerFactory(guiceControllerFactory);
			loader.load(ApplicationController.class.getResource(FXML_NAME).openStream());

			ApplicationController controller = loader.getController();
	        injector.injectMembers(controller);

	        BorderPane root = loader.getRoot();
			Scene scene = new Scene(root);

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
