package sql2bean.logic;

import java.util.List;

import sql2bean.dao.table.PackageSelect;

public interface IDBPackage {

	List<PackageSelect.Data> selectPackage();

	boolean insertPackage(String packageName, String folder);

	boolean deletePackage(String packageId);

	boolean updatePackage(String packageId, String packageName, String folder);
}
