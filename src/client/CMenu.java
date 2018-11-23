package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CMenu {
	//�˿ں�IP
	private String host="localhost";
	private int port=8000;
	private Socket socket;
	//menu��ť
	private JMenuBar menuBar;
	private JMenu mnNewMenu1;
	private JMenu mnNewMenu2;
	private JMenu mnNewMenu3;
	private JMenu mnNewMenu4;
	private JMenu mnNewMenu5;
	private JMenuItem mntmNewMenuItem;
	//����
	private PrintWriter pw;
	private BufferedReader br;
	//�жϰ�ť
	private boolean connected=false;
	//����
	private Tree tree;
	//��������
	private Command command;
	public CMenu(JMenuBar menuBar,Socket socket,final PrintWriter pw,final BufferedReader br,JPanel commandPanel,JPanel TreePanel,JPanel tablePanel){
		this.menuBar=menuBar;
		this.pw=pw;
		this.br=br;
		this.socket=socket;
		tree=new Tree(TreePanel,tablePanel,pw, br);
		command=new Command(commandPanel);
	}
	
	private PrintWriter getWriter(Socket socket)throws IOException{
		OutputStream socketOut=socket.getOutputStream();	
		return new PrintWriter(socketOut, true);
	}

	private BufferedReader getReader(Socket socket)throws IOException {
		InputStream socketIn=socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));

	}
	
	public void setMenu(){
		//��һ��menu��ǩ
		mnNewMenu1 = new JMenu("������");
		menuBar.add(mnNewMenu1);
		
		mntmNewMenuItem = new JMenuItem("���ӷ�����");
		
		
		mntmNewMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					if(!connected){
						//���ӷ�����
						socket=new Socket(host,port);
						br=getReader(socket);
						pw=getWriter(socket);
						connected=true;
						//����
						tree.setTree();
						tree.setListener();
						tree.setTreeMenu();
						//��������
						command.setCommand();
					}	
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu1.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("����");
		mntmNewMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				@SuppressWarnings("unused")
				String inputValue = JOptionPane.showInputDialog("Please input the table name");
				pw.println(inputValue);
				String ttt;
				try {
					ttt = br.readLine();
					JOptionPane.showConfirmDialog(null, ttt, ttt, JOptionPane.YES_NO_OPTION);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
		mnNewMenu1.add(mntmNewMenuItem);
		
		//�ڶ�����ǩ
		mnNewMenu2 = new JMenu("���ݿ�");
		menuBar.add(mnNewMenu2);
		
		mntmNewMenuItem = new JMenuItem("�½����ݿ�");
		mnNewMenu2.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("ɾ�����ݿ�");
		mnNewMenu2.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("�л����ݿ�");
		mnNewMenu2.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("������");
		mnNewMenu2.add(mntmNewMenuItem);
		
		//������menu��ǩ
		mnNewMenu3 = new JMenu("��");
		menuBar.add(mnNewMenu3);
		
		mntmNewMenuItem = new JMenuItem("������");
		mnNewMenu3.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("ɾ����");
		mnNewMenu3.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("������");
		mnNewMenu3.add(mntmNewMenuItem);
		
		//���ĸ�menu��ǩ
		mnNewMenu4 = new JMenu("�ֶ�");
		menuBar.add(mnNewMenu4);
		
		mntmNewMenuItem = new JMenuItem("���ֶ�");
		mnNewMenu4.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("ɾ���ֶ�");
		mnNewMenu4.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("�޸�����");
		mnNewMenu4.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("������");
		mnNewMenu4.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("�޸�Լ��");
		mnNewMenu4.add(mntmNewMenuItem);
		
		//�����menu��ǩ
		mnNewMenu5 = new JMenu("����");
		menuBar.add(mnNewMenu5);
		
		mntmNewMenuItem = new JMenuItem("ʹ��˵��");
		mnNewMenu5.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("����������");
		mnNewMenu5.add(mntmNewMenuItem);
		mntmNewMenuItem = new JMenuItem("��ϵ����");
		mnNewMenu5.add(mntmNewMenuItem);
	}
	

	
	public boolean getConnected(){
		return connected;
	}
	
	public void connectedClose(){
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
