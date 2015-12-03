package sql2bean.dao;


/**
 *実施するクエリーを表すクラス
 */
public interface IQuery {
	/**
	 * 基になるSQLを取得する
	 * @return SQL
	 */
	String getSql();
}
