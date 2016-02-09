package sql2bean.fx.application;

import sql2bean.dao.table.ApplicationSelect;


public class ApplicationLogicDummy implements ApplicationLogic{

	@Override
	public ApplicationSelect.Data load(int id) {
		System.out.println("load id:" + id);
		return null;
	}

	@Override
	public int save(String name, String dbName, String dbConnection) {

		System.out.println("save name:" + name + " dbName:" + dbName + " dbConnection:" + dbConnection);
		return 0;
	}

	@Override
	public void cancel() {
		System.out.println("cancel");
	}

	@Override
	public void delete() {
		System.out.println("delete");
	}
}
