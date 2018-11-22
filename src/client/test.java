package client;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import DatabaseTree.DbList;

public class test extends JFrame{

	
	// network
	private Socket socket;
	Socket[]sockets=new Socket[1000];
	BufferedReader br;
	PrintWriter pw;
	DbList dblist=new DbList();
	CDatabase cdb = new CDatabase(dblist);
	CTable ctb = new CTable(dblist);
	CProperty cpt =new CProperty(dblist);
	CMenu cm;
		
	// frame
	private JPanel contentPanel;
	private JPanel TreePanel;
	private JPanel tablePanel;
	private JMenuBar menuBar;
//	private JTree tree= new JTree();
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client client = new Client();
					client.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public test() throws UnknownHostException, IOException {
		initialize();
	}

	/**
	 * 窗口绘画
	 * @throws IOException 
	 */
	private void initialize() throws IOException {

		addWindowListener(new WindowAdapter() {             //窗口关闭前关闭socket
			@Override
			public void windowClosing(WindowEvent e) {
//				if(cm.getConnected())
//					cm.connectedClose();
					try {
						if(socket!=null)
							socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		setTitle("Database Management System ");
		setBounds(100, 100, 1280, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPanel = new JPanel();
		setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
//		TreePanel = new JPanel();
//		contentPanel.add(TreePanel, BorderLayout.WEST);
//		TreePanel.setLayout(new BorderLayout(0, 0));
//		
//		tablePanel = new JPanel();
//		contentPanel.add(tablePanel, BorderLayout.CENTER);
//		tablePanel.setLayout(new BorderLayout(0, 0));
			
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);	
		cm=new CMenu(menuBar,socket,pw,br,contentPanel,TreePanel,tablePanel);
		cm.setMenu();
		
	}
}
