package manager_p.panelDialog_p;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import client_p.ClientNet;
import client_p.PacketMap;
import client_p.Receivable;
import data_p.sales_p.SalesBySeat;
import data_p.sales_p.SalesRecord;
import data_p.sales_p.SalesTot;
import manager_p.ManagerWindow;
import manager_p.syn_p.MsSalesInquirySyn;
import packetBase_p.PacketBase;
import server_p.packet_p.ack_p.SmResvRoomAck;
import server_p.packet_p.ack_p.SmSalesInquiryAck;

public class ResvInquiry extends JPanel implements Receivable {
	JFrame tfram;
	ManagerWindow mw;
	
	private ArrayList<SalesRecord> salesRecordList;
	private DefaultTableModel dTable5;
	private String headerResvs[];
	private String contentsResvs[][];
	private JTable table;
	private JScrollPane scrollPane_12;
	
	
	//������
	public ResvInquiry(ManagerWindow mw) {
		this.mw = mw;
		PacketMap.getInstance().map.put(SmResvRoomAck.class, this);
		// ����
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		add(panel_3);
		
		panel_3.setLayout(null);

		CalendarTest cal = new CalendarTest(mw);
		cal.setBounds(224, 25, 502, 362);
		panel_3.add(cal);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(-28, 368, 887, 305);
		cal.add(scrollPane);

		scrollPane_12 = new JScrollPane();
		scrollPane_12.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_12.setBounds(79, 389, 842, 311);
		panel_3.add(scrollPane_12);

		headerResvs = new String[] { "�̿뼮", "������", "����ð�" };
		contentsResvs = new String[1][headerResvs.length];
		dTable5 = new DefaultTableModel(contentsResvs, headerResvs);
		table = new JTable(dTable5);
		table.getColumn("�̿뼮").setPreferredWidth(100);
		table.getColumn("������").setPreferredWidth(100);
		table.getColumn("����ð�").setPreferredWidth(400);
		table.setRowHeight(27);
		table.setFont(new Font("������", Font.PLAIN, 25));
		table.setFillsViewportHeight(true);
		scrollPane_12.setViewportView(table);
		
	}

	@Override
	public void receive(PacketBase packet) {
		if (packet.getClass() == SmResvRoomAck.class) {
			System.out.println("�Ŵ����� �޾Ҵ� ��ũ");
			SmResvRoomAck ack = (SmResvRoomAck) packet;
			salesRecordList = ack.rtd;

			contentsResvs = new String[salesRecordList.size()][headerResvs.length];
			for (int i = 0; i < salesRecordList.size(); i++) {
				contentsResvs[i][0] = salesRecordList.get(i).room_name;
				contentsResvs[i][1] = salesRecordList.get(i).user_name;
				String hhh = "";
				for (String h : salesRecordList.get(i).hourList) {
					hhh += h + " ";
				}
				contentsResvs[i][2] = hhh;
			}

			dTable5 = new DefaultTableModel(contentsResvs, headerResvs);
			table = new JTable(dTable5);
			table.setRowHeight(27);
			table.setFont(new Font("������", Font.PLAIN, 25));
			table.setFillsViewportHeight(true);
			scrollPane_12.setViewportView(table);

		}
	}
}