package sql2bean.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sql2bean.sql.DataType;

public class SQLKeyValue {

	final static Pattern PTN_SNEKE_CASE = Pattern.compile("_([a-z])");

	private StringProperty key = new SimpleStringProperty();
	public StringProperty keyProperty(){ return key;}
	public void setKey(String key){ keyProperty().set(key);}
	public String getKey(){ return keyProperty().get();}

	/**
	 * 項目名をキャメル式で取得する(USER_ID → userId)
	 * @return 項目名(キャメル式)
	 */
	public String getKeyAsCamelName(){
		return convertUnderToBig(getKey().toLowerCase());
	}

	/**
	 * 項目名をパスカル式で取得する(USER_ID → UserId)
	 * @return 項目名(パスカル式)
	 */
	public String getKeyAsPascalName(){

		return convertUnderToBig(getKey().substring(0, 1).toUpperCase() + getKey().substring(1).toLowerCase());
	}


	private StringProperty value = new SimpleStringProperty();
	public StringProperty valueProperty() { return value;}
	public void setValue(String value){ valueProperty().set(value);}
	public String getValue(){ return valueProperty().get();}

	private ObjectProperty<DataType> type = new SimpleObjectProperty<>();
	public ObjectProperty<DataType> typeProperty(){return type;}
	public void setType(DataType type){ this.type.set(type);}
	public DataType getType(){ return this.type.get();}

	private List<Integer> paramNo;

	public void addParameter(int i){
		if (paramNo == null) {
			paramNo = new ArrayList<Integer>();
		}
		paramNo.add(Integer.valueOf(i));
	}

	public List<Integer> getParamNos(){
		return Collections.unmodifiableList(paramNo);
	}

	public SQLKeyValue() {
		this.type.set(DataType.String);
	}

	public SQLKeyValue(String key){
		this();
		this.key.set(key);
	}

	public SQLKeyValue(String key,DataType type, String value){
		this.key.set(key);
		this.type.set(type);
		this.value.set(value);
	}

	@Override
	public String toString(){

		return "SQLKeyValue(Key:" + key.get() + ",Value:" + value.get() + ",Type:"+type.get().toString() +",Param["
				+ paramNo.stream().map(p -> p.toString()).collect(Collectors.joining(",")) + "])";
	}

	@Override
	public boolean equals(Object obj){

		if (this == obj) return true;
		if (obj == null || ! (obj instanceof SQLKeyValue)) return false;

		SQLKeyValue another = (SQLKeyValue) obj;
		return Objects.equals(key.get(), another.key.get())
				&& Objects.equals(value.get(), another.value.get())
				&& Objects.equals(type.get(), another.type.get())
				&& Objects.deepEquals(paramNo, another.paramNo);
	}

	/**
	 * 小文字を大文字にして返す
	 * @param m パターン
	 * @return
	 */
	private String convertUnderToBig(String name){

		Matcher m = PTN_SNEKE_CASE.matcher(name);

		StringBuffer sb = new StringBuffer(name.length());
		while (m.find()) {
			m.appendReplacement(sb, m.group(1).toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
