package com.github.xaatw0.sql2bean.fx;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.activation.UnsupportedDataTypeException;

import com.github.xaatw0.sql2bean.sql.ColumnInfo;

public class FXController implements Initializable{


	private LogicInterface logic;

	@FXML private TextArea txtSql;
	@FXML private TextField txtSqlId;

	@FXML private TableView tblResult;
	@FXML private TableView<Map<String, String>> tblArgs;

	@FXML private Button btnExecute;
	@FXML private Button btnSave;

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
	}

	@FXML
	public void execute(ActionEvent event) throws UnsupportedDataTypeException, SQLException{

		tblResult.setItems(logic.execute(sql.getValue()));

		for(ColumnInfo column: logic.getColumnInfo()){
			addColumn(tblResult, column.getCamelName());
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
