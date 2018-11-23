package client;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;
import DatabaseTree.DbList;

public class Client extends JFrame{

	
	// network
	private Socket socket;
	private Socket[]sockets=new Socket[1000];
	private BufferedReader br;
	private PrintWriter pw;
	private DbList dblist=new DbList();
	private CDatabase cdb = new CDatabase(dblist);
	private CTable ctb = new CTable(dblist);
	private CProperty cpt =new CProperty(dblist);
	private CMenu cm;
	private Command command;
		
	// frame
	private JPanel contentPanel;
	private JPanel rightPanel;
	private JPanel TreePanel;
	private JPanel tablePanel;
	private JPanel commandPanel;
	private JMenuBar menuBar;
	private JTree tree= new JTree();
		
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
	public Client() throws UnknownHostException, IOException {
		initialize();
	}

	/**
	 * ���ڻ滭
	 * @throws IOException 
	 */
	private void initialize() throws IOException {

		addWindowListener(new WindowAdapter() {             //���ڹر�ǰ�ر�socket
			@Override
			public void windowClosing(WindowEvent e) {
				if(cm.getConnected())
					cm.connectedClose();
			}
		});
		setTitle("Database Management System ");
		setBounds(100, 100, 1280, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPanel = new JPanel();
		setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		rightPanel=new JPanel();
		contentPanel.add(rightPanel, BorderLayout.CENTER);
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		TreePanel = new JPanel();
		contentPanel.add(TreePanel, BorderLayout.WEST);
		TreePanel.setLayout(new BorderLayout(0, 0));
		TreePanel.setPreferredSize(new Dimension(180, 700));//��������JPanel�Ĵ�С
		
		tablePanel = new JPanel();
		rightPanel.add(tablePanel, BorderLayout.CENTER);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		commandPanel = new JPanel();
		rightPanel.add(commandPanel, BorderLayout.SOUTH);
		commandPanel.setLayout(new BorderLayout(0, 0));
		commandPanel.setPreferredSize(new Dimension(1000, 200));//��������JPanel�Ĵ�С
			
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);	
//		contentPanel.add(tree, BorderLayout.WEST);
		cm=new CMenu(menuBar,socket,pw,br,commandPanel,TreePanel,tablePanel);
		cm.setMenu();
	}
}
