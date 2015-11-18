package sql2bean.sql;

import java.sql.Types;
import java.util.Arrays;
import java.util.stream.IntStream;

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
	public int[] getType(){
		return types;
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

}
