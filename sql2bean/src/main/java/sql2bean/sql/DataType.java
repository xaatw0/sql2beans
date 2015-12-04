package sql2bean.sql;

import java.sql.Types;
import java.util.Arrays;
import java.util.stream.IntStream;

import javafx.util.StringConverter;

/**
 * DBから取得したデータの型のラッパー
 */
public enum DataType {

	/** 文字型 */
	String(String.class, Types.VARCHAR),
	/** 数字型 */
	Integer(Integer.class, Types.INTEGER);

	private int[] types;

	private Class<?> clazz;

	private DataType(Class<?> clazz, int... type){
		this.clazz = clazz;
		this.types = type;
	}

	public Class<?> getClazz(){
		return clazz;
	}

	/**
	 * SQLのオリジナルのデータ型
	 * @return
	 */
	public int[] getTypes(){
		return types;
	}

	public int getType(){
		return types[0];
	}

	/**
	 * 型のJavaとしてのクラスを返す
	 * @return クラス名
	 */
	public String getClassName(){
		return this.toString();
	}

	public IntStream getValueStream(){
		return Arrays.stream(types);
	}

	public static class StringDataTypeConverter extends StringConverter<DataType>{

		@Override
		public java.lang.String toString(DataType object) {
			return object.name();
		}

		@Override
		public DataType fromString(java.lang.String string) {
			return Arrays.stream(DataType.values()).filter(p->string.equals(p.name())).findAny().get();
		}
	}

}
