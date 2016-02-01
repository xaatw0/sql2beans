package sql2bean.fx;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.activation.UnsupportedDataTypeException;

import sql2bean.beanmaker.BeanMaker;
import sql2bean.beans.SQLKeyValue;
import sql2bean.dao.ISQLType;
import sql2bean.sql.ColumnInfo;
import sql2bean.sql.SQLAnalyzer;

public class LogicDummy implements LogicInterface<Object> {

	private SQLAnalyzer analyzer = new SQLAnalyzer();

	private ResultSetMetaData metaData;

	private ColumnInfo[]  columnInfos;

	public LogicDummy() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public ObservableList execute(String sql, List<SQLKeyValue> lstArgs) {

		ObservableList<Object> data = FXCollections.observableArrayList();

		try{
	        Connection conn = DriverManager.
	            getConnection("jdbc:h2:~/test", "sa", "");

	        List<SQLKeyValue> params = analyzer.analyze(sql,conn);
	        for (SQLKeyValue newData: params){

	        	Optional<SQLKeyValue> inputData = lstArgs.stream().filter(p->p.getKey().equals(newData.getKey())).findFirst();
	        	if(inputData.isPresent()){
	        		newData.setValue(inputData.get().getValue());
	        	}
	        }

	        String test = analyzer.writeSelectBean("testpackage", "testclass", ISQLType.NONE);

	        PreparedStatement preparedStatement = conn.prepareStatement(analyzer.getPreparedSql());
	        analyzer.setParameter(preparedStatement);
	        ResultSet result = preparedStatement.executeQuery();
	        analyzer.analyze(result.getMetaData());

	        String source = analyzer.writeSelectBean("testpackage", "testclass", ISQLType.NONE);
	        BeanMaker maker = new BeanMaker("target\\classes");
	        maker.compile("testpackage.testclass", source);

	        Class clazz = Class.forName("testpackage.testclass");
	        Object dao = clazz.newInstance();
	        Method convert = clazz.getMethod("convert", ResultSet.class);

			columnInfos = ColumnInfo.createColumnInfo(result.getMetaData());

			while(result.next()){

				Object obj = convert.invoke(dao, result);
				data.add(obj);
			}

	        conn.close();

		}catch(SQLException | UnsupportedDataTypeException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return data;
	}

	@Override
	public ResultSetMetaData getMetaData() {
		return metaData;
	}



	@Override
	public void save(String id, String sql) {
		// TODO 自動生成されたメソッド・スタブ

	}



	@Override
	public String load(String sqlId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}



	@Override
	public ColumnInfo[] getColumnInfo() {
		return columnInfos;
	}


	@Override
	public void setCell(TableColumn<Object, String> column, String columnName) {
		column.setCellValueFactory(new PropertyValueFactory<Object,String>(columnName));
	}

	@Override
	public void analize(String sql, ObservableList<SQLKeyValue> args) {


	}

}
