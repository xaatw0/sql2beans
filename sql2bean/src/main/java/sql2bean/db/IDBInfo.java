package sql2bean.db;

import java.sql.SQLException;
import java.util.List;

import sql2bean.dao.table.ApplicationSelect;
import sql2bean.dao.table.PackageSelect;

public interface IDBInfo {

	List<ApplicationSelect.Data> selectApplication() throws SQLException;

	int insertApplication(ApplicationSelect.Data data) throws SQLException;

	int updateApplication(ApplicationSelect.Data data) throws SQLException;

	boolean deleteApplication(ApplicationSelect.Data data) throws SQLException;

	List<PackageSelect.Data> selectPackage() throws SQLException;

	int insertPackage(PackageSelect.Data data) throws SQLException;

	int updatePackage(PackageSelect.Data data) throws SQLException;

	boolean deletePackage(PackageSelect.Data data) throws SQLException;
}
