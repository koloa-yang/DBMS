package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
/**
 * 主要是用来把tablePanel的参数传进来，然后利用这里面的方法对tablePanel进行操作
 * @author Yang
 *
 */
public class contentTable {
	//初始变量
	private String[][] content;
	private JPanel tablePanel;
	private JButton btnadd;
	private JButton btndelete;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	//构造函数
	public contentTable(JPanel tablePanel,String[][] content){
		this.tablePanel=tablePanel;
		this.content=content;
		btnadd=new JButton("增加数据");
		btndelete=new JButton("删除数据");
		btnadd.setPreferredSize(new Dimension(96, 40));
		btndelete.setPreferredSize(new Dimension(96, 40));
		//增加数据
		btnadd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.addRow(new Object[]{});
			}
		});
		
		//删除数据
		btndelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int selectedRow = table.getSelectedRow();//获得选中行的索引
				if(selectedRow!=-1) //存在选中行
				{
				model.removeRow(selectedRow); //删除行
				}
			}
		});
	}
	
	//构建table
	public void setTable(){//一下变注视代码可以实现表格的一般操作
		tablePanel.removeAll();
		table=new JTable();
		String[] columns={"ID","姓名","性别"};//此处需要获得字段名称
		model=new DefaultTableModel(columns,0);
		table.setModel(model);
		TableColumnModel columnModel=table.getColumnModel();
		int count=columnModel.getColumnCount();
		for(int i=0;i<count;i++){
			javax.swing.table.TableColumn column=columnModel.getColumn(i);
			column.setPreferredWidth(800/count);//设置列的宽度
			}
		model.addRow(new Object[]{16301064,"fdd","男"});//此处需要一行一行读数据，加进去
		model.addRow(new Object[]{16301065,"zxc","女"});//此处需要一行一行读数据，加进去
		model.addRow(new Object[]{16301125,"jgb","男"});//此处需要一行一行读数据，加进去
		
		
		JTableHeader myt=table.getTableHeader();
		JPanel jtb=new JPanel();
		jtb.setLayout(new BorderLayout(0, 0));
		jtb.add(myt,BorderLayout.NORTH);
		jtb.add(table,BorderLayout.CENTER);
		scrollPane=new JScrollPane(table);
		scrollPane.add(jtb);
		tablePanel.add(scrollPane,BorderLayout.CENTER);
		JPanel butt=new JPanel();
		butt.setLayout(new FlowLayout(FlowLayout.RIGHT,10,5));
		butt.setPreferredSize(new Dimension(0,50));
		butt.add(btnadd);
		butt.add(btndelete);
		tablePanel.add(butt, BorderLayout.SOUTH);
//		JOptionPane.showMessageDialog(null,"我到了","错误",JOptionPane.PLAIN_MESSAGE); 
	}
	
	public void addProperty(){
		
	}
	
}
