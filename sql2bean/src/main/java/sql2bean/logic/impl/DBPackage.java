package sql2bean.logic.impl;

import java.util.List;

import sql2bean.dao.table.PackageSelect.Data;
import sql2bean.logic.IDBPackage;

public class DBPackage implements IDBPackage {


	@Override
	public List<Data> selectPackage() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean insertPackage(String packageName, String folder) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean deletePackage(String packageId) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean updatePackage(String packageId, String packageName,
			String folder) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
