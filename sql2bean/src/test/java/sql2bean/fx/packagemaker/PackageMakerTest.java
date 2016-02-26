package sql2bean.fx.packagemaker;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class PackageMakerTest extends Application {

	@Override
	public void start(Stage primaryStage) {

		PackageMakerLogic dummy = new PackageMakerLogicDummy();
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override protected void configure() {
                bind(PackageMakerLogic.class).toInstance(dummy);
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
			loader.load(PackageMakerController.class.getResourceAsStream(PackageMakerController.FXML_NAME));

			PackageMakerController controller = (PackageMakerController)loader.getController();
	        injector.injectMembers(controller);

			Pane root = loader.getRoot();
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
