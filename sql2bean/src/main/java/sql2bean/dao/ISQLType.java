package sql2bean.dao;

public enum ISQLType {
	/** 使用しない */
	NONE(""),
	/** 更新系 */
	ISQLExecute("ISQLBatch"),

	/** 問合せ系 */
	ISQLSelect("ISQLSelect");

	private final String className;
	private ISQLType(String className){
		this.className = className;
	}
	public String getClassName(){
		return className;
	}
}
