package sql2bean.fx;


import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.h2.tools.Server;


public class FXMain<T> extends Application {

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

			primaryStage.setScene(scene);
			primaryStage.show();

			// データベースを起動
			server = Server.createTcpServer().start();

			Class.forName("org.h2.Driver");

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
