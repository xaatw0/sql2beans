package com.github.xaatw0.sql2bean.fx;

import java.net.URL;
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
import javafx.scene.control.cell.MapValueFactory;

public class FXController implements Initializable{


	private LogicInterface logic;

	@FXML private TextArea txtSql;
	@FXML private TableView tableResult;
	@FXML private Button btnExecute;
	@FXML private Button btnSave;

	private StringProperty sql = new SimpleStringProperty();

	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void execute(ActionEvent event){

		logic.execute(sql.getValue());


		addColumn(tableResult,"b");
	}

	@FXML
	public void save(ActionEvent event){
	}

    /**
     * 項目を追加する
     * @param table
     * @param columnName
     */
    private void addColumn(TableView table, String columnName) {
        TableColumn<Map, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new MapValueFactory(columnName));
        column.setMinWidth(130);
        // column.setCellFactory(cellFactoryForMap);
        table.getColumns().add(column);
      }
}
