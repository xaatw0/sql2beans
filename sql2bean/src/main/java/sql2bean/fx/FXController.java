package sql2bean.fx;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javax.activation.UnsupportedDataTypeException;

import org.h2.tools.Server;

import sql2bean.beanmaker.BeanMaker;
import sql2bean.beans.PackageBean;
import sql2bean.beans.SQLKeyValue;
import sql2bean.dao.ISQLType;
import sql2bean.sql.ColumnInfo;
import sql2bean.sql.DataType;
import sql2bean.sql.SQLAnalyzer;

public class FXController implements Initializable{

	/** DBサーバー*/
	Server server;

	/** DBコネクション*/
	Connection conn;

	private LogicInterface logic;

	/** SQLを入力するテキストエリア */
	@FXML private TextArea txtSql;

	/** SQLのIDを入力するテキストボックス */
	@FXML private TextField txtSqlId;

	/** SQLの実施結果を表示するテーブル */
	@FXML private TableView tblResult;

	/** SQLの引数を表示するテーブル*/
	@FXML private TableView<SQLKeyValue> tblArgs;

	/** SQLの引数を表示するテーブルのキーの列*/
	@FXML private TableColumn<SQLKeyValue,String> colKey;

	/** SQLの引数を表示するテーブルの値の列*/
	@FXML private TableColumn<SQLKeyValue,DataType> colType;

	/** SQLの引数を表示するテーブルの値の列*/
	@FXML private TableColumn<SQLKeyValue,String> colValue;

	/** SQLの引数を表示するテーブルのリスト*/
	private ObservableList<SQLKeyValue> args;

	private SQLAnalyzer analyzer = new SQLAnalyzer();

	/** SQLを解析するボタン */
	@FXML private Button btnAnalyze;

	/** SQLを実行するボタン */
	@FXML private Button btnExecute;

	@FXML private Button btnExport;

	/** SQLを保存するボタン */
	@FXML private Button btnSave;

	/** SQLを呼び出すボタン */
	@FXML private Button btnLoad;

	private boolean blnQuery = false;

	@FXML private Label lblStatementType;

	private ObservableList<PackageBean> lstPackage;
	@FXML private ComboBox<PackageBean> cmbPackage;
	@FXML private Button btnPackage;

	/** パッケージのコンボボックスの編集可能を切り替える */
	private BooleanProperty blnEditablePackage = new SimpleBooleanProperty(false);

	private StringProperty sql = new SimpleStringProperty(txtSql, "");
	public StringProperty sql(){
		return sql;
	}

	public void setSql(String value){
		sql().set(value);
	}

	public String getSql(){
		return sql().get();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logic = new LogicDummy();
		args = FXCollections.observableArrayList();
		tblArgs.setItems(args);

		// 編集可能なテーブルにする
		tblArgs.setEditable(true);

		// 引数を表示するテーブルの設定
		colKey.setCellValueFactory(new PropertyValueFactory<>("key"));

		// Typeはコンボボックスにする
		colType.setCellValueFactory(new PropertyValueFactory<>("type"));
		colType.setCellFactory(ComboBoxTableCell.forTableColumn(
				new DataType.StringDataTypeConverter(), FXCollections.observableArrayList(DataType.values())));

		// Valueは編集可能なセルにする
		colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
		colValue.setCellFactory(TextFieldTableCell.forTableColumn());

		sql().bind(txtSql.textProperty());



		ObjectProperty<PackageBean> selectedPackage = new SimpleObjectProperty<>();
		selectedPackage.bind(cmbPackage.getSelectionModel().selectedItemProperty());
		blnEditablePackage.bind(selectedPackage.isEqualTo(PackageBean.NEW_PACKAGE));
		// x blnEditablePackage.bind(cmbPackage.getSelectionModel().selectedItemProperty().get().sqlIdProperty().isEqualTo(PackageBean.INITIALIZED));
		cmbPackage.setConverter(new PackageBean.ComboboxMenuConverter() );

		lstPackage = FXCollections.observableArrayList();
		cmbPackage.setItems(lstPackage);
		cmbPackage.editableProperty().bind(blnEditablePackage);
		lstPackage.add(PackageBean.NEW_PACKAGE);

		startup();
	}

	public void startup(){
		// Velocityの初期化
        BeanMaker.init();

		// データベースを起動
		try {
			server = Server.createTcpServer().start();
			Class.forName("org.h2.Driver");
	        conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

	        Statement statement = conn.createStatement();

	        InputStream stream = getClass().getResourceAsStream("CreateTable.sql");
	        byte[] data = new byte[stream.available()];
	        stream.read(data);
	        String createTable = new String(data);
	        statement.execute(createTable);

		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
			Platform.exit();
		}

	}

	public void shutdown(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		server.shutdown();
	}

	@FXML
	public void execute(ActionEvent event) throws UnsupportedDataTypeException, SQLException{

		tblResult.setItems(logic.execute(sql.getValue()));

		for(ColumnInfo column: logic.getColumnInfo()){
			addColumn(tblResult, column.getCamelName());
		}
	}

	@FXML
	public void analyze(ActionEvent event){
		// 現在の設定値を記録するしてから、表を初期化する
		List<SQLKeyValue> priviousData = args.stream().collect(Collectors.toList());
		args.clear();

		args.addAll(analyzer.analyze(sql.get()));
		analyzer.copyOldData(priviousData);
	}

	@FXML
	public void export(ActionEvent event){
		String source = analyzer.writeExecuteBean("testpackage", "testclass", ISQLType.ISQLSelect);
	}

	@FXML
	public void save(ActionEvent event){
	}

	@FXML
	public void load(ActionEvent event){
	}

    /**
     * 項目を追加する
     * @param table
     * @param columnName
     */
    private void addColumn(TableView table, String columnName) {

        TableColumn column = new TableColumn(columnName);
        column.setMinWidth(130);
        column.setCellValueFactory(new PropertyValueFactory<DummyObject,String>(columnName));
        table.getColumns().add(column);
    }

    @FXML
    public void btnAddPackagePressed(ActionEvent e){
    	PackageBean selected = cmbPackage.getSelectionModel().getSelectedItem();
    }
}
