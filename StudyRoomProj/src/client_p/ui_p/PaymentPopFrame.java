package client_p.ui_p;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import client_p.ClientNet;
import client_p.Receivable;
import client_p.packet_p.syn_p.CsBuyRoomSyn;
import data_p.product_p.ProductData;
import data_p.product_p.room_p.RoomProduct;
import packetBase_p.ELoginType;
import packetBase_p.EResult;
import packetBase_p.PacketBase;
import server_p.packet_p.ack_p.ScBuyRoomAck;

public class PaymentPopFrame extends JFrame implements Receivable {
	RoomProduct room;

	public PaymentPopFrame() {

		setBounds(50, 50, 150, 150);
		setLayout(new GridLayout(2, 1));

		JLabel jbl = new JLabel("결제 하시겠습니까?");
		jbl.setHorizontalAlignment(SwingConstants.CENTER);
		add(jbl);
		JButton jb = new JButton("확인");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				for (Calendar cal : BaseFrame.getInstance().roomProduct.calendarList) {
//
//					//System.out.println(cal.getTime());
//				}
				CsBuyRoomSyn packet = new CsBuyRoomSyn(room,
						BaseFrame.getInstance().userData.uuid);
				ClientNet.getInstance().sendPacket(packet);
				BaseFrame.getInstance().getReservationMain().resetResInfo();
				dispose();
			}
		});
		add(jb);

		setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void openPage(RoomProduct room) {
		setVisible(true);
		this.room = room;
	}

	@Override
	public void receive(PacketBase packet) {
		ScBuyRoomAck ack = (ScBuyRoomAck) packet;
		JDialog jd = new JDialog();
		jd.setLayout(new GridLayout(2, 1));
		jd.setBounds(50, 50, 150, 150);
		JLabel jl = new JLabel("");
		JButton jb = new JButton("확인");

		if (ack.eResult == EResult.SUCCESS) {
			jl.setText("결제완료");
			BaseFrame.getInstance().userData.isExit = false;
			BaseFrame.getInstance().openMainLayout(ack.roomList, ack.myReserList, null);
			if(BaseFrame.getInstance().loginType == ELoginType.MOBILE) {
				BaseFrame.getInstance().view("LoginMain");
			}
			BaseFrame.getInstance().payment.dispose();
//			BaseFrame.getInstance().roomProduct.calendarList.clear();
			BaseFrame.getInstance().payment.timeList.clear();
		} else
			jl.setText("결제실패");

		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		jd.add(jl);
		jd.add(jb);
	}
}