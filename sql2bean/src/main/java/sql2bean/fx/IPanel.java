package sql2bean.fx;

/**
 * パネル(メインウィンドウから呼び出し、データを入力されて、閉じて、メインウィンドウにデータを返すStage)のインターフェース。
 */
public interface IPanel<T> {

	/**
	 * パネルで入力したデータを取得する。
	 * @return 入力値
	 */
	T getData();


	/**
	 * パネルに初期表示するデータを設定する。
	 * @param data
	 */
	void setData(T data);
}
