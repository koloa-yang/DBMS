package frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import client.TreeMenu;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class AddTableFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField_tableName;
	private JTable table;
	private int propertyNum=1;
	private Vector vData = new Vector();
	public JButton btnOK;

	/**
	 * Create the frame.
	 */
	public AddTableFrame(TreeMenu treeMenu,PrintWriter pw,BufferedReader br) {
		setTitle("\u65B0\u5EFA\u8868");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 432, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_tableName = new JPanel();
		contentPane.add(panel_tableName, BorderLayout.NORTH);
		panel_tableName.setLayout(new BoxLayout(panel_tableName, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("\u8BF7\u8F93\u5165\u8868\u540D\uFF1A");
		panel_tableName.add(lblNewLabel);
		
		textField_tableName = new JTextField();
		panel_tableName.add(textField_tableName);
		textField_tableName.setColumns(10);
		
		JPanel panel_table = new JPanel();
		contentPane.add(panel_table, BorderLayout.CENTER);
		
		
		Vector vName = new Vector();
		vName.add("字段名");
		vName.add("字段属性");
		final Vector vColumn = new Vector();
		vColumn.add("aaa");
		vColumn.add("int");
		vData.add(vColumn.clone());
		System.out.println(vData.get(0));
		DefaultTableModel model = new DefaultTableModel(vData, vName);
		
		table = new JTable();
		table.setModel(model);
		  /* 
         * 设置JTable的列默认的宽度和高度 
         */  
        TableColumn column = null;  
        int colunms = table.getColumnCount();  
        for(int i = 0; i < colunms; i++)  
        {  
            column = table.getColumnModel().getColumn(i);  
            /*将每一列的默认宽度设置为100*/  
            column.setPreferredWidth(200);  
        }  
        /* 
         * 设置JTable自动调整列表的状态，此处设置为关闭 
         */  
        panel_table.setLayout(new BorderLayout(0, 0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
          
        /*用JScrollPane装载JTable，这样超出范围的列就可以通过滚动条来查看*/  
        JScrollPane scroll = new JScrollPane(table); 
        panel_table.add(scroll);
        
        JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btn_addProperty = new JButton("\u6DFB\u52A0\u5B57\u6BB5");
		btn_addProperty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				propertyNum++;
				vData.add(vColumn.clone());
				table.updateUI();
			}
		});
		panel.add(btn_addProperty);
		
		btnOK = new JButton("\u786E\u8BA4\u521B\u5EFA\u8868");
		btnOK.addMouseListener(treeMenu.newTable_mouseListner(pw, br));
		
		panel.add(btnOK);
	}
	public String[] getStr(){
		String[] name_pt=new String[2];
		String str="";
		String tempv="";
		Vector column;
		for(int i=0;i<vData.size();i++) {
			if(i!=0) {
				str+=",";
			}
			column=(Vector)vData.get(i);
			str+=column.get(0);
			str+=" ";
			str+=column.get(1);
		}
		name_pt[0]=textField_tableName.getText();
		name_pt[1]=str;
		return name_pt;
	}
}
