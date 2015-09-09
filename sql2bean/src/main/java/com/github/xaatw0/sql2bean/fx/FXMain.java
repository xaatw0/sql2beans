package com.github.xaatw0.sql2bean.fx;


import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class FXMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource("FXMain.fxml").openStream());
			BorderPane root = loader.getRoot();
			Scene scene = new Scene(root);

			// メイン文の引数を取得する
			final List<String> args = getParameters().getRaw();

			// DIの注入の定義
			Injector injector = Guice.createInjector(new AbstractModule() {

				@Override
				protected void configure() {

					if (0 < args.size() && args.get(0).equals("TEST")){
						bind(LogicInterface.class).to(LogicDummy.class);
					}
				}
			});

			// コントロールを取得し、DIの注入を実施する
			injector.injectMembers(loader.getController());

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
