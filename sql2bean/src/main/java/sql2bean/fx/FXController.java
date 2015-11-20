package sql2bean.fx;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

import javax.activation.UnsupportedDataTypeException;

import sql2bean.beans.SQLKeyValue;
import sql2bean.sql.ColumnInfo;
import sql2bean.sql.SQLAnalyzer;

public class FXController implements Initializable{


	private LogicInterface logic;

	/** SQLを入力するテキストエリア */
	@FXML private TextArea txtSql;

	/** SQLのIDを入力するテキストボックス */
	@FXML private TextField txtSqlId;

	/** SQLの実施結果を表示するテーブル */
	@FXML private TableView tblResult;

	/** SQLの引数を表示するテーブル*/
	@FXML private TableView<SQLKeyValue> tblArgs;

	/** SQLの引数を表示するテーブルのデータ*/
	private ObservableList<SQLKeyValue> lstArgs = FXCollections.observableArrayList();

	/** SQLの引数を表示するテーブルのキーの列*/
	@FXML private TableColumn<SQLKeyValue,String> colKey;

	/** SQLの引数を表示するテーブルの値の列*/
	@FXML private TableColumn<SQLKeyValue,String> colValue;

	/** SQLの引数を表示するテーブルのリスト*/
	private ObservableList<SQLKeyValue> args;

	/** SQLを解析するボタン */
	@FXML private Button btnAnalyze;

	/** SQLを実行するボタン */
	@FXML private Button btnExecute;

	/** SQLを保存するボタン */
	@FXML private Button btnSave;

	/** SQLを呼び出すボタン */
	@FXML private Button btnLoad;

	private StringProperty sql = new SimpleStringProperty(this, "sql");
	public StringProperty sql(){
		return sql;
	}

	public void setSql(String value){
		sql().set(value);
	}

	public String getSql(){
		return sql().get();
	}

	public void initialize(URL location, ResourceBundle resources) {
		logic = new LogicDummy();
		args = FXCollections.observableArrayList();

		// 編集可能なテーブルにする
		tblArgs.setEditable(true);

		// 引数を表示するテーブルの設定
		colKey.setCellValueFactory(new PropertyValueFactory<>("key"));

		// Valueは編集可能なセルにする
		colValue.setCellFactory(new Callback<TableColumn<SQLKeyValue,String>, TableCell<SQLKeyValue,String>>() {
			@Override
			public TableCell<SQLKeyValue, String> call(TableColumn<SQLKeyValue, String> arg0) {
				return new TextFieldTableCell<SQLKeyValue, String>(new DefaultStringConverter());
			}
		});
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

		lstArgs.clear();

		for (String key: new SQLAnalyzer().analyze(getSql())){

			SQLKeyValue row = new SQLKeyValue();
			row.setKey(key);
			lstArgs.add(row);
		}
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
        logic.setCell(column, columnName);
        table.getColumns().add(column);
    }
}
