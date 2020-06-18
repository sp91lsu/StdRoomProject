package server_p;

import server_p.packet_p.ack_p.ScLoginAck;
import server_p.packet_p.ack_p.ScSignInUpAck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import client_p.packet_p.syn_p.CsLoginSyn;
import client_p.packet_p.syn_p.CsSignUpSyn;
import data_p.user_p.UserData;
import dbOracle_p.*;
import packetBase_p.EResult;
import packetBase_p.PacketBase;

public interface ServerPacketMethod {

	void receive(PacketClient client, PacketBase packet);
}

class MethLoginSyn implements ServerPacketMethod {

	public void receive(PacketClient client, PacketBase packet) {
		CsLoginSyn recPacket = (CsLoginSyn) packet;

		QueryObject qo = new QueryObject();
		String idOrPhone = recPacket.isID == true ? "id" : "phone";

		qo.findQuery(ETable.ACCOUNT, "*", idOrPhone + " = '" + recPacket.id + "' and pw = '" + recPacket.pw + "'");

		ResultSet rs = DBProccess.getInstance().findData(qo.query);

		ScLoginAck ack = null;

		try {
			if (rs.next()) {
				UserData userdata = new UserData(rs.getString("uuid"), rs.getString("name"), rs.getString("id"),
						rs.getString("phone"), rs.getString("birth"));
				rs.close();
				ack = new ScLoginAck(EResult.SUCCESS, userdata);
			} else {
				ack = new ScLoginAck(EResult.NOT_FOUND_DATA, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		client.sendPacket(ack);
	}
}

class MethSignUpSyn implements ServerPacketMethod {

	public void receive(PacketClient client, PacketBase packet) {

		ScSignInUpAck ack = null;
		try {
			CsSignUpSyn recPacket = (CsSignUpSyn) packet;
			UserData userData = new UserData(UUID.randomUUID().toString(), recPacket.name, recPacket.id, recPacket.pw,
					recPacket.phone, recPacket.birth, recPacket.cType);

			String ctype = userData.cType;

			QueryObject qo = new QueryObject();
			qo.findQuery(ETable.MANAGERKEY, "key", "key = '" + userData.cType + "'");
			ResultSet rs = DBProccess.getInstance().findData(qo.query);

			if (rs.next()) {
				ctype = EClientType.MANAGER.name();
			}
			DBProccess.getInstance().close();

			qo.createQuery("uuid,name,id,pw,birth,phone,ctype", userData.uuid, userData.name, userData.id, userData.pw,
					userData.birth, userData.phone, ctype);

			DBProccess.getInstance().insertData(ETable.ACCOUNT, qo);

			ack = new ScSignInUpAck(EResult.SUCCESS, userData.name);

		} catch (Exception e) {
			ack = new ScSignInUpAck(EResult.NOT_FOUND_DATA, "회원가입에 실패하였습니다.");
			e.printStackTrace();
		}

		client.sendPacket(ack);
	}
}
