package sql2bean.fx;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class FXMain extends Application {

	private static final String MAIN_FXML = "FXMain.fxml";

	private static final String PACKAGE_MAKER_FXML = "PackageMaker.fxml";

	FXController controller;

	/**
	 * 主なウィンドウ(LoginWindowとMainWindow)。
	 * Application.startの引数のStateを設定し、主要なウィンドウになる。
	 * 他のパネル(情報入力用の他のウィンドウ)の親となり、アプリケーションが終わるまで存在する。
	 */
	private Stage primaryStage;

	/**
	 * データ入力用のパネル(DatePanelとTextPanel)
	 * mainWindowから呼び出し、データを入力するためのパネルを表示するStage。
	 * 入力用のパネルを表示すると、
	 */
	private Stage subPanel;

	private static FXMain instance;
	public static FXMain getInstance(){
		return instance;
	}

	@Override
	public void start(Stage primaryStage) {

		instance = this;

		this.primaryStage = primaryStage;

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource(MAIN_FXML).openStream());
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

	/**
	 * 入力用のパネルを表示する。入力が完了するまでメインウィンドウを選択できない(モーダル)。<br/>
	 * 読み込んだウィンドウのコントロールを返す。
	 * @param <T> 表示するパネルが取り扱うデータの型。
	 * @param fxmlFile 表示するパネルのFXMLファイル
	 * @param data 初期化に使用するデータ
	 * @return 表示するパネルのコントロール
	 */
	public <T> T openPanel(InputStream streamFxml, T data){

		Stage newStage = new Stage();
		newStage.initOwner(primaryStage);
		newStage.initModality(Modality.APPLICATION_MODAL);

		IPanel<T> controller = null;
		FXMLLoader loader = new FXMLLoader();

		try{
			loader.load(streamFxml);
		} catch(IOException ex){
			ex.printStackTrace();
		}

		newStage.setScene(new Scene(loader.getRoot()));
		controller = loader.getController();

		// 以前データを選択して、そのデータの型がパネルの型と一致している場合、初期値に設定する
		if (data != null){
			controller.setData(data);
		}

		// データ入力が完了するまで待機する
		subPanel = newStage;
		newStage.showAndWait();

		return controller.getData();
	}
}
