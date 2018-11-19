package client;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.*;

public class Client extends JFrame{

	
	
	// network
	private String host="localhost";
	private int port=8000;
	private Socket socket;
	Socket[]sockets=new Socket[1000];
	BufferedReader br;
	PrintWriter pw;
	CDatabase cdb = new CDatabase();
	CTable ctb = new CTable();
	CProperty cpt =new CProperty();
	
	private PrintWriter getWriter(Socket socket)throws IOException{
		OutputStream socketOut=socket.getOutputStream();	
		return new PrintWriter(socketOut, true);
	}

	private BufferedReader getReader(Socket socket)throws IOException {
		InputStream socketIn=socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));

	}
	
	
	// frame
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;
	private JTree tree;
	private JPanel panel;
	private JButton btnConnect;
	private JButton btnNewDB;
	private JButton btnNewTable;
	private JButton btnSelete;
	private JButton testbtn;
	private JTable table;
	private JPopupMenu popMenu_tree_db;
	private JMenuItem pop_tree_db_new;
	private JMenuItem pop_tree_db_del;
	private JMenuItem pop_tree_db_rename;
	private JPopupMenu popMenu_tree_table;
	private JMenuItem pop_tree_table_new;
	private JMenuItem pop_tree_table_del;
	private JMenuItem pop_tree_table_rename;
	private JPopupMenu popMenu_tree_column;
	private JMenuItem pop_tree_column_new;
	private JMenuItem pop_tree_column_del;
	private JMenuItem pop_tree_column_rename;
	
	public JTree getTree() {
		return tree;
	}

	public void setTree() {
		this.tree.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode("\u670D\u52A1\u5668") {
					{
						DefaultMutableTreeNode node_db;
						DefaultMutableTreeNode node_table;
						DefaultMutableTreeNode node_column;
						node_db = new DefaultMutableTreeNode("\u6570\u636E\u5E93\u540D");
							node_table = new DefaultMutableTreeNode("\u8868\u540D");
								node_column=new DefaultMutableTreeNode("\u5B57\u6BB5\u540D");
								node_table.add(node_column);
							node_db.add(node_table);
						add(node_db);
						node_db = new DefaultMutableTreeNode("\u6570\u636E\u5E93\u540D");
							node_table = new DefaultMutableTreeNode("\u8868\u540D");
								node_column=new DefaultMutableTreeNode("\u5B57\u6BB5\u540D");
								node_table.add(node_column);
								node_db.add(node_table);
						add(node_db);
						
					}
				}
			));
		this.tree.setToggleClickCount(1);
		this.tree.setRootVisible(false);
		this.tree.addMouseListener(new MouseAdapter() {
							 
							@Override
							public void mouseReleased(MouseEvent e) {
								// TODO Auto-generated method stub
								//super.mouseReleased(e);
								if (e.isPopupTrigger()&&e.getButton() == MouseEvent.BUTTON3) { 
									TreePath path = tree.getPathForLocation(e.getX(), e.getY());
									tree.setSelectionPath(path);
									DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
									if(selectNode!=null)
										if(selectNode.getLevel()==1) {//0是根节点 1是数据库 2是表 3是列
											popMenu_tree_db.show(panel, e.getX(), e.getY());
										}
										else if(selectNode.getLevel()==2) {//0是根节点 1是数据库 2是表 3是列
											popMenu_tree_table.show(panel, e.getX(), e.getY());
										}
										else if(selectNode.getLevel()==3) {//0是根节点 1是数据库 2是表 3是列
											popMenu_tree_column.show(panel, e.getX(), e.getY());
										}
				                }
							}
							
						});
	}

	public JButton getBtnConnect() {
		return btnConnect;
	}

	public void setBtnConnect(JButton btnConnect) {
		this.btnConnect = btnConnect;
	}

	public JButton getBtnNewDB() {
		return btnNewDB;
	}

	public void setBtnNewDB(JButton btnNewDB) {
		this.btnNewDB = btnNewDB;
	}

	public JButton getBtnNewTable() {
		return btnNewTable;
	}

	public void setBtnNewTable(JButton btnNewTable) {
		this.btnNewTable = btnNewTable;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}
	
	public JPopupMenu getPopMenu_tree_db() {
		return popMenu_tree_db;
	}

	public void setPopMenu_tree_db(JPopupMenu popMenu_tree_db) {
		this.popMenu_tree_db = popMenu_tree_db;
	}

	public JMenuItem getPop_tree_db_new() {
		return pop_tree_db_new;
	}

	public void setPop_tree_db_new(JMenuItem pop_tree_db_new) {
		this.pop_tree_db_new = pop_tree_db_new;
	}

	public JMenuItem getPop_tree_db_del() {
		return pop_tree_db_del;
	}

	public void setPop_tree_db_del(JMenuItem pop_tree_db_del) {
		this.pop_tree_db_del = pop_tree_db_del;
	}

	public JMenuItem getPop_tree_db_rename() {
		return pop_tree_db_rename;
	}

	public void setPop_tree_db_rename(JMenuItem pop_tree_db_rename) {
		this.pop_tree_db_rename = pop_tree_db_rename;
	}

	public JPopupMenu getPopMenu_tree_table() {
		return popMenu_tree_table;
	}

	public void setPopMenu_tree_table(JPopupMenu popMenu_tree_table) {
		this.popMenu_tree_table = popMenu_tree_table;
	}

	public JMenuItem getPop_tree_table_new() {
		return pop_tree_table_new;
	}

	public void setPop_tree_table_new(JMenuItem pop_tree_table_new) {
		this.pop_tree_table_new = pop_tree_table_new;
	}

	public JMenuItem getPop_tree_table_del() {
		return pop_tree_table_del;
	}

	public void setPop_tree_table_del(JMenuItem pop_tree_table_del) {
		this.pop_tree_table_del = pop_tree_table_del;
	}

	public JMenuItem getPop_tree_table_rename() {
		return pop_tree_table_rename;
	}

	public void setPop_tree_table_rename(JMenuItem pop_tree_table_rename) {
		this.pop_tree_table_rename = pop_tree_table_rename;
	}

	public JPopupMenu getPopMenu_tree_column() {
		return popMenu_tree_column;
	}

	public void setPopMenu_tree_column(JPopupMenu popMenu_tree_column) {
		this.popMenu_tree_column = popMenu_tree_column;
	}

	public JMenuItem getPop_tree_column_new() {
		return pop_tree_column_new;
	}

	public void setPop_tree_column_new(JMenuItem pop_tree_column_new) {
		this.pop_tree_column_new = pop_tree_column_new;
	}

	public JMenuItem getPop_tree_column_del() {
		return pop_tree_column_del;
	}

	public void setPop_tree_column_del(JMenuItem pop_tree_column_del) {
		this.pop_tree_column_del = pop_tree_column_del;
	}

	public JMenuItem getPop_tree_column_rename() {
		return pop_tree_column_rename;
	}

	public void setPop_tree_column_rename(JMenuItem pop_tree_column_rename) {
		this.pop_tree_column_rename = pop_tree_column_rename;
	}

	
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
	 * 窗口绘画
	 * @throws IOException 
	 */
	private void initialize() throws IOException {

		addWindowListener(new WindowAdapter() {             //窗口关闭前关闭socket
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if(socket!=null)
						socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		setTitle("Database Management System ");
		setBounds(100, 100, 895, 688);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("\u7CFB\u7EDF");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem);
		
		popMenu_tree_db=new JPopupMenu();
		pop_tree_db_new=new JMenuItem("新建数据库");
		pop_tree_db_del=new JMenuItem("删除数据库");
		pop_tree_db_rename=new JMenuItem("重命名");
		popMenu_tree_db.add(pop_tree_db_new);
		popMenu_tree_db.add(pop_tree_db_del);
		popMenu_tree_db.add(pop_tree_db_rename);
		
		popMenu_tree_table=new JPopupMenu();
		pop_tree_table_new=new JMenuItem("新建表");
		pop_tree_table_del=new JMenuItem("删除表");
		pop_tree_table_rename=new JMenuItem("重命名");
		popMenu_tree_table.add(pop_tree_table_new);
		popMenu_tree_table.add(pop_tree_table_del);
		popMenu_tree_table.add(pop_tree_table_rename);
		
		popMenu_tree_column=new JPopupMenu();
		pop_tree_column_new=new JMenuItem("新建字段");
		pop_tree_column_del=new JMenuItem("删除字段");
		pop_tree_column_rename=new JMenuItem("重命名");
		popMenu_tree_column.add(pop_tree_column_new);
		popMenu_tree_column.add(pop_tree_column_del);
		popMenu_tree_column.add(pop_tree_column_rename);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		//连接数据库
		btnConnect = new JButton("\u8FDE\u63A5\u670D\u52A1\u5668");
		panel.add(btnConnect);
		btnConnect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					socket=new Socket(host,port);
					br=getReader(socket);
					pw=getWriter(socket);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//创建数据库
		btnNewDB = new JButton("\u521B\u5EFA\u6570\u636E\u5E93");
		panel.add(btnNewDB);
		btnNewDB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				@SuppressWarnings("unused")
				String inputValue = JOptionPane.showInputDialog("Please input the database name");
				String ttt=cdb.createDatabase(inputValue, pw, br);
				JOptionPane.showConfirmDialog(null, ttt, ttt, JOptionPane.YES_NO_OPTION);	
			}
		});
		
		//创建表格
		btnNewTable = new JButton("\u521B\u5EFA\u8868");
		panel.add(btnNewTable);
		btnNewTable.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				@SuppressWarnings("unused")
				String inputValue = JOptionPane.showInputDialog("Please input the table name");
				String ttt=ctb.createTable(inputValue, pw, br);
				JOptionPane.showConfirmDialog(null, ttt, ttt, JOptionPane.YES_NO_OPTION);		
			}
		});
		
		
		//select语句的解析
		btnSelete = new JButton("select");
		panel.add(btnSelete);
		btnSelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				@SuppressWarnings("unused")
				//String inputValue = JOptionPane.showInputDialog("Please input the table name");
				String[][] ttt=cpt.select("test", "id",null, pw, br);//如果没有用到where的话就写null
				for(int i=0;i<ttt.length;i++){
					for(int j=0;j<ttt[i].length;j++){
						System.out.print(ttt[i][j]+"   ");
					}
					System.out.println();
				}
			}
		});
		
		//测试数据的按钮，可修改和删除
		testbtn = new JButton("test");
		panel.add(testbtn);
		testbtn.addActionListener(new ActionListener(){
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
		
		
		tree = new JTree();
		tree.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setTree();
		
		contentPane.add(tree, BorderLayout.WEST);
		
		table = new JTable();
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(table, BorderLayout.CENTER);
		
	}
}
