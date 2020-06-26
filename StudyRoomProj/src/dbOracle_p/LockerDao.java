package dbOracle_p;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import data_p.product_p.DataManager;
import data_p.product_p.LockerData;
import data_p.product_p.room_p.RoomProduct;

public class LockerDao extends DBProcess {

	public boolean insertLocker(String userUUID, LockerData lockerData) {
		String[] columArr = { "UUID", "ID", "PW" };

		String columQuery = getColum(columArr);
		String columNum = getColumNum(columArr.length);

		try {
			insertQuery(ETable.LOCKER, columQuery, columNum);

			stmt = con.prepareStatement(query);

			stmt.setString(1, userUUID);
			stmt.setInt(2, lockerData.id);
			stmt.setString(3, lockerData.pw);

			rs = stmt.executeQuery();

			close();

		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean findUserLocker(String uuid) {

		try {
			ResultSet rs = getRS(ETable.LOCKER, "*", "uuid = " + uuid);

			stmt.setString(1,uuid);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<LockerData> getLockerIDList() {

		ArrayList<LockerData> list = new ArrayList<LockerData>();
		try {
			ResultSet rs = getRS(ETable.LOCKER, "*");

			while (rs.next()) {
				for (LockerData data : DataManager.getInstance().lockerList) {

					if (data.id == rs.getInt("ID")) {
						data.setPW(rs.getString("pw"));
						list.add(data);
					}
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public boolean deleteLocker(String uuid) {
		deleteQuery(ETable.LOCKER, "uuid = " + uuid);

		try {
			stmt = con.prepareStatement(query);

			rs = stmt.executeQuery();

			close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
}
