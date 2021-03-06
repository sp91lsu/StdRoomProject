package manager_p.panelDialog_p;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import client_p.ClientNet;
import client_p.PacketMap;
import client_p.Receivable;
import client_p.packet_p.syn_p.CsChatSyn;
import client_p.ui_p.BaseFrame;
import client_p.ui_p.MyColor;
import manager_p.ManagerWindow;
import manager_p.ack_p.MsChatConnectAck;
import packetBase_p.EResult;
import packetBase_p.PacketBase;
import server_p.packet_p.broadCast.ScChatBroadCast;
import server_p.packet_p.syn_p.SMChatConnectSyn;
import java.awt.Font;
import java.awt.Color;

public class Chatting extends JPanel implements Receivable {
	ManagerWindow mw;

	private JTable table_3;
	private JTable table_4;
	public JLabel lb_Chat_name;
	public String userName = "";
	public JLabel lb_Chat_end;
	public String chatEnd = "님과 채팅이 종료되었습니다.";
	public String chatStart = "님과 채팅 중입니다.";
	public boolean isChatting = false;
	public boolean amIstopChat = false;
	public JTextArea textArea;
	public JTextField textField;
	private JScrollPane scrollPane_Chat;
	public CsChatSyn chatSyn;
	String text = "";

	public Object tabbedPane;

	public JButton btnTerminate;

	class ActionLister_Chatting implements ActionListener {
		String sort;

		public ActionLister_Chatting(String sort) {
			this.sort = sort;
		}

		void send(boolean isChatting) {
			text = "[관리자]: " + textField.getText() + "\n";

			if (isChatting) {
				chatSyn.setText(text);
				ClientNet.getInstance().sendPacket(chatSyn);
			} else {
				textArea.setText(textArea.getText() + "\n" + text);
			}

			textField.setText("");

			textField.selectAll();
			scrollPane_Chat.getVerticalScrollBar().setValue(scrollPane_Chat.getVerticalScrollBar().getMaximum());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (sort) {
			case "전송":
				if (!textField.getText().equals("")) {
					send(isChatting);
				}
				break;
			case "엔터":
				send(isChatting);
				break;

			case "종료":
				new ChatEndDialog(mw);

				break;

			default:
				break;
			}
		}

	}

	public Chatting(ManagerWindow mw) {
		this.mw = mw;
		PacketMap.getInstance().map.put(SMChatConnectSyn.class, this); // 채팅 연결 요청에 대한 응답
		PacketMap.getInstance().map.put(ScChatBroadCast.class, this);

		setLayout(new BorderLayout(0, 0));

		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.DARK_GRAY);
		add(panel_8);

		GridBagLayout gbl_panel_8 = new GridBagLayout();
		gbl_panel_8.columnWidths = new int[] { 0, 348, 79, 678, 131, 0 };
		gbl_panel_8.rowHeights = new int[] { 59, 603, 71, 0 };
		gbl_panel_8.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_8.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panel_8.setLayout(gbl_panel_8);

		JPanel panel_17 = new JPanel();
		panel_17.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_panel_17 = new GridBagConstraints();
		gbc_panel_17.insets = new Insets(0, 0, 5, 5);
		gbc_panel_17.fill = GridBagConstraints.BOTH;
		gbc_panel_17.gridx = 1;
		gbc_panel_17.gridy = 1;
		panel_8.add(panel_17, gbc_panel_17);
		GridBagLayout gbl_panel_17 = new GridBagLayout();
		gbl_panel_17.columnWidths = new int[] { 0, 0 };
		gbl_panel_17.rowHeights = new int[] { 30, 0, 30, 0, 0, 0, 0, 0, 0 };
		gbl_panel_17.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_17.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_17.setLayout(gbl_panel_17);

		JLabel lblNewLabel_9 = new JLabel("1:1 \uCC44\uD305 \uBB38\uC758");
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setFont(new Font("새굴림", Font.BOLD, 30));
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_9.gridx = 0;
		gbc_lblNewLabel_9.gridy = 1;
		panel_17.add(lblNewLabel_9, gbc_lblNewLabel_9);

		lb_Chat_name = new JLabel("");
		lb_Chat_name.setForeground(Color.WHITE);
		lb_Chat_name.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		GridBagConstraints gbc_lb_Chat_name = new GridBagConstraints();
		gbc_lb_Chat_name.insets = new Insets(0, 0, 5, 0);
		gbc_lb_Chat_name.gridx = 0;
		gbc_lb_Chat_name.gridy = 3;
		panel_17.add(lb_Chat_name, gbc_lb_Chat_name);

		lb_Chat_end = new JLabel("");
		lb_Chat_end.setForeground(Color.WHITE);
		lb_Chat_end.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		GridBagConstraints gbc_lb_Chat_end = new GridBagConstraints();
		gbc_lb_Chat_end.insets = new Insets(0, 0, 5, 0);
		gbc_lb_Chat_end.gridx = 0;
		gbc_lb_Chat_end.gridy = 4;
		panel_17.add(lb_Chat_end, gbc_lb_Chat_end);

		JPanel panel_19 = new JPanel();
		GridBagConstraints gbc_panel_19 = new GridBagConstraints();
		gbc_panel_19.insets = new Insets(0, 0, 5, 5);
		gbc_panel_19.fill = GridBagConstraints.BOTH;
		gbc_panel_19.gridx = 3;
		gbc_panel_19.gridy = 1;
		panel_8.add(panel_19, gbc_panel_19);
		GridBagLayout gbl_panel_19 = new GridBagLayout();
		gbl_panel_19.columnWidths = new int[] { 0, 0 };
		gbl_panel_19.rowHeights = new int[] { 519, 56, 0 };
		gbl_panel_19.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_19.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panel_19.setLayout(gbl_panel_19);

		// 채팅 에어리어
		textArea = new JTextArea();
		scrollPane_Chat = new JScrollPane(textArea);
		scrollPane_Chat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 0;
		panel_19.add(scrollPane_Chat, gbc_textArea);

		JPanel panel_20 = new JPanel();
		GridBagConstraints gbc_panel_20 = new GridBagConstraints();
		gbc_panel_20.fill = GridBagConstraints.BOTH;
		gbc_panel_20.gridx = 0;
		gbc_panel_20.gridy = 1;
		panel_19.add(panel_20, gbc_panel_20);
		GridBagLayout gbl_panel_20 = new GridBagLayout();
		gbl_panel_20.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_20.rowHeights = new int[] { 0, 0 };
		gbl_panel_20.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_20.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_20.setLayout(gbl_panel_20);

		// 채팅
		textField = new JTextField();
		// 텍스트 입력 액션
		textField.addActionListener(new ActionLister_Chatting("엔터"));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		panel_20.add(textField, gbc_textField);
		textField.setColumns(10);

		JButton btnNewButton_4 = new JButton("전송");
		btnNewButton_4.addActionListener(new ActionLister_Chatting("전송"));
		btnNewButton_4.setBackground(MyColor.w_white);
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_4.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton_4.gridx = 1;
		gbc_btnNewButton_4.gridy = 0;
		panel_20.add(btnNewButton_4, gbc_btnNewButton_4);

		btnTerminate = new JButton("종료");
		btnTerminate.addActionListener(new ActionLister_Chatting("종료"));
		btnTerminate.setBackground(MyColor.w_white);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 0;
		panel_20.add(btnTerminate, gbc_btnNewButton);

	}

	@Override
	public void receive(PacketBase packet) {
		// 채팅연결
		if (packet.getClass() == SMChatConnectSyn.class) {
			SMChatConnectSyn sccAck = (SMChatConnectSyn) packet;
			if (BaseFrame.getInstance().userData.cType == 1) {
				if (sccAck.eResult == EResult.SUCCESS) {
					System.out.println("채팅 연결 성공 하앍");
//						System.out.println(BaseFrame.getInstance().userData.name);
					ChatReqDialog dialog = new ChatReqDialog(mw, sccAck);
					userName = sccAck.userdata.name;

					dialog.lbClientName.setText(sccAck.userdata.name);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} else if (sccAck.eResult == EResult.ALREADY_OTHER_MANAGER_CONNECT) {
					new ChkDialog("이미 다른 관리자가 채팅 중입니다.");
					mw.pnl_Chatting.lb_Chat_name.setText("");
					mw.pnl_Chatting.lb_Chat_end.setText("");
				}
			} else {
				MsChatConnectAck ack = new MsChatConnectAck(false);
				ClientNet.getInstance().sendPacket(ack);
			}
		}

		// 채팅
		if (packet.getClass() == ScChatBroadCast.class) {
			ScChatBroadCast scChat = (ScChatBroadCast) packet;
			if (scChat.isEnd && !amIstopChat) {
				btnTerminate.setEnabled(false);
				textArea.setText(textArea.getText() + "\n" + "[" + userName + "]" + "님이 채팅을 종료하였습니다.");
				ChkDialog endMsg = new ChkDialog("[" + userName + "]" + "님이 채팅을 종료하였습니다.");
				isChatting = false;
				lb_Chat_end.setText("님과 채팅이 종료되었습니다.");

			}
			textArea.setText(textArea.getText() + "\n" + scChat.getText());
			scrollPane_Chat.getVerticalScrollBar().setValue(scrollPane_Chat.getVerticalScrollBar().getMaximum());
		}

	}

}
