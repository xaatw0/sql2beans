package com.github.xaatw0.sql2bean.fx;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.h2.tools.Server;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class FXMain extends Application {

	/**
	 * DBサーバー
	 */
	Server server;

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

			server = Server.createTcpServer().start();

			Class.forName("org.h2.Driver");
	        Connection conn = DriverManager.
	            getConnection("jdbc:h2:~/test", "sa", "");

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    @Override
    public void stop() {
		server.stop();
    }

	public static void main(String[] args) {
		launch(args);
	}
}
