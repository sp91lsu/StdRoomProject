package client_p.packet_p.ack_p;

import java.util.UUID;

import packetBase_p.EResult;
import packetBase_p.ResultPacketBase;

public class CsChatConnectAck extends ResultPacketBase {

	public CsChatConnectAck(EResult eResult) {
		super(eResult);
	}

}
