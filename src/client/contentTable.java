package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
/**
 * ��Ҫ��������tablePanel�Ĳ�����������Ȼ������������ķ�����tablePanel���в���
 * @author Yang
 *
 */
public class contentTable {
	//��ʼ����
	private String[][] content;
	private JPanel tablePanel;
	private JButton btnadd;
	private JButton btndelete;
	private JTable table;
	
	//���캯��
	public contentTable(JPanel tablePanel,String[][] content){
		this.tablePanel=tablePanel;
		this.content=content;
		btnadd=new JButton("��������");
		btndelete=new JButton("ɾ������");
		btnadd.setPreferredSize(new Dimension(96, 40));
		btndelete.setPreferredSize(new Dimension(96, 40));
	}
	
	//����table
	public void setTable(){//һ�±�ע�Ӵ������ʵ�ֱ���һ�����
		tablePanel.removeAll();
		table=new JTable();
		String[] columns={"ID","����","�Ա�"};//�˴���Ҫ����ֶ�����
		final DefaultTableModel model=new DefaultTableModel(columns,0);
		table.setModel(model);
		TableColumnModel columnModel=table.getColumnModel();
		int count=columnModel.getColumnCount();
		for(int i=0;i<count;i++){
			javax.swing.table.TableColumn column=columnModel.getColumn(i);
			column.setPreferredWidth(800/count);//�����еĿ��
			}
		model.addRow(new Object[]{16301064,"fdd","��"});//�˴���Ҫһ��һ�ж����ݣ��ӽ�ȥ
		model.addRow(new Object[]{16301065,"zxc","Ů"});//�˴���Ҫһ��һ�ж����ݣ��ӽ�ȥ
		model.addRow(new Object[]{16301125,"jgb","��"});//�˴���Ҫһ��һ�ж����ݣ��ӽ�ȥ
		
		//��������
		btnadd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.addRow(new Object[]{});
			}
		});
		
		//ɾ������
		btndelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int selectedRow = table.getSelectedRow();//���ѡ���е�����
				if(selectedRow!=-1) //����ѡ����
				{
				model.removeRow(selectedRow); //ɾ����
				}
			}
		});
		JTableHeader myt=table.getTableHeader();
		JPanel jtb=new JPanel();
		jtb.setLayout(new BorderLayout(0, 0));
		jtb.add(myt,BorderLayout.NORTH);
		jtb.add(table,BorderLayout.CENTER);
		tablePanel.add(jtb,BorderLayout.NORTH);
		JPanel butt=new JPanel();
		butt.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
		butt.add(btnadd);
		butt.add(btndelete);
		tablePanel.add(butt, BorderLayout.CENTER);
	}
	
	public void addProperty(){
		
	}
	
}
