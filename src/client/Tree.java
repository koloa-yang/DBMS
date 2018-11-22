package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import DatabaseTree.DataBase;
import DatabaseTree.DbList;
import DatabaseTree.Property;
import DatabaseTree.Table;

public class Tree {
	//��ҳ�洫��
	    private JPanel TreePanel;
	    private JPanel contentPanel;
	    private JPanel tablePanel;
	    private PrintWriter pw;
	    private BufferedReader br;
	//��������
	    private DbList dblist=new DbList();
		private CDatabase cdb = new CDatabase(dblist);
		private CTable ctb = new CTable(dblist);
		private CProperty cpt =new CProperty(dblist);
		private JTree tree= new JTree();
	//���Ҽ��˵�
		private TreeMenu treeMenu;
		private JTable table;
		private JButton btnadd=new JButton("��������");
		private JButton btndelete=new JButton("ɾ������");
		//�հ״�
		private JPopupMenu popMenu_tree_newdb;
		private JMenuItem pop_tree_newdb;
		//���ݿ�
		private JPopupMenu popMenu_tree_db;
		private JMenuItem pop_tree_db_newTable;
		private JMenuItem pop_tree_db_del;
		private JMenuItem pop_tree_db_rename;
		private JMenuItem pop_tree_db_reRead;
		private JMenuItem pop_tree_db_readSQLFile;
		//��
		private JPopupMenu popMenu_tree_table;
		private JMenuItem pop_tree_table_newProperty;
		private JMenuItem pop_tree_table_del;
		private JMenuItem pop_tree_table_rename;
		//�ֶ�
		private JPopupMenu popMenu_tree_property;
		private JMenuItem pop_tree_property_set;
		private JMenuItem pop_tree_property_del;
		private JMenuItem pop_tree_property_rename;
			
		public Tree(JPanel TreePanel,JPanel tablePanel,PrintWriter pw,BufferedReader br){
			this.TreePanel=TreePanel;
			this.tablePanel=tablePanel;
			this.pw=pw;
			this.br=br;
			//��
			popMenu_tree_newdb=new JPopupMenu();
			pop_tree_newdb=new JMenuItem("�½����ݿ�");
			popMenu_tree_newdb.add(pop_tree_newdb);
			
			popMenu_tree_db=new JPopupMenu();
			pop_tree_db_newTable=new JMenuItem("�½���");
			pop_tree_db_del=new JMenuItem("ɾ�����ݿ�");
			pop_tree_db_rename=new JMenuItem("������");
			pop_tree_db_reRead=new JMenuItem("ˢ��");
			pop_tree_db_readSQLFile=new JMenuItem("����SQL�ļ�");
			popMenu_tree_db.add(pop_tree_db_newTable);
			popMenu_tree_db.add(pop_tree_db_del);
			popMenu_tree_db.add(pop_tree_db_rename);
			popMenu_tree_db.add(pop_tree_db_reRead);
			popMenu_tree_db.add(pop_tree_db_readSQLFile);
			
			popMenu_tree_table=new JPopupMenu();
			pop_tree_table_newProperty=new JMenuItem("�½��ֶ�");
			pop_tree_table_del=new JMenuItem("ɾ����");
			pop_tree_table_rename=new JMenuItem("������");
			popMenu_tree_table.add(pop_tree_table_newProperty);
			popMenu_tree_table.add(pop_tree_table_del);
			popMenu_tree_table.add(pop_tree_table_rename);
			
			popMenu_tree_property=new JPopupMenu();
			pop_tree_property_set=new JMenuItem("�޸�����");
			pop_tree_property_del=new JMenuItem("ɾ���ֶ�");
			pop_tree_property_rename=new JMenuItem("������");
			popMenu_tree_property.add(pop_tree_property_set);
			popMenu_tree_property.add(pop_tree_property_del);
			popMenu_tree_property.add(pop_tree_property_rename);
			
		}
	
		public void setTree() {
			treeMenu=new TreeMenu(cdb,ctb,cpt,dblist,tree);
			tree.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			treeMenu.setTree();
			tree.setEditable(false);
			this.tree.setToggleClickCount(1);
			this.tree.setRootVisible(false);
			this.tree.addMouseListener(new MouseAdapter() {
				 
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					//super.mouseReleased(e);
					TreePath path = tree.getPathForLocation(e.getX(), e.getY());
					tree.setSelectionPath(path);
					DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
					if (e.isPopupTrigger()&&e.getButton() == MouseEvent.BUTTON3) { 
						if(selectNode!=null) {
							if(selectNode.getLevel()==1) {//0�Ǹ��ڵ� 1�����ݿ� 2�Ǳ� 3����
								popMenu_tree_db.show(TreePanel, e.getX(), e.getY()+30);
							}
							else if(selectNode.getLevel()==2) {//0�Ǹ��ڵ� 1�����ݿ� 2�Ǳ� 3����
								popMenu_tree_table.show(TreePanel, e.getX(), e.getY()+30);
							}
							else if(selectNode.getLevel()==3) {//0�Ǹ��ڵ� 1�����ݿ� 2�Ǳ� 3����
								popMenu_tree_property.show(TreePanel, e.getX(), e.getY()+30);
							}
						}
						else {
							popMenu_tree_newdb.show(TreePanel, e.getX(), e.getY()+30);
						}
							
	                }
					else if(e.getClickCount()==2) {
						if(selectNode.getLevel()==2) {//���˫����ڵ� �򿪱�
							//��ʾ�����ݣ���ʾ�Ŷ��Ľ��棩
	//						int rownum=8; //�˴���Ҫ��ü�¼������
							
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
							contentPanel.remove(tablePanel);
							tablePanel = new JPanel();
							contentPanel.add(tablePanel, BorderLayout.CENTER);
							JTableHeader myt=table.getTableHeader();
							tablePanel.add(myt,BorderLayout.NORTH);
							tablePanel.add(table,BorderLayout.CENTER);
							tablePanel.add(btnadd,BorderLayout.SOUTH);
							tablePanel.add(btndelete,BorderLayout.SOUTH);
						}
						else if(selectNode.getLevel()==3) {//���˫���ֶνڵ� �Ҳ���ʾѡ���ֶε�������Ϣ
							//�Ҳ���ʾѡ���ֶε�������Ϣ����ʾ�Ŷ��Ľ��棩 һ��
	//						int rownum=8; //�˴���Ҫ��ü�¼������
							
							table=new JTable();
							String[] columns={"ID"};//�˴���Ҫ����ֶ�����
							DefaultTableModel model=new DefaultTableModel(columns,0);
							table.setModel(model);
							TableColumnModel columnModel=table.getColumnModel();
							int count=columnModel.getColumnCount();
							for(int i=0;i<count;i++){
								javax.swing.table.TableColumn column=columnModel.getColumn(i);
								column.setPreferredWidth(800);//�����еĿ��
								}
							model.addRow(new Object[]{16301001});//�˴���Ҫһ��һ�ж����ݣ��ӽ�ȥ
							model.addRow(new Object[]{16301002});//�˴���Ҫһ��һ�ж����ݣ��ӽ�ȥ
							model.addRow(new Object[]{16301003});//�˴���Ҫһ��һ�ж����ݣ��ӽ�ȥ
							model.addRow(new Object[]{16301004});//�˴���Ҫһ��һ�ж����ݣ��ӽ�ȥ
							
	//						tablePanel.removeAll();
							contentPanel.remove(tablePanel);
							tablePanel = new JPanel();
							contentPanel.add(tablePanel, BorderLayout.CENTER);
							JTableHeader myt=table.getTableHeader();
							tablePanel.add(myt,BorderLayout.NORTH);
							tablePanel.add(table,BorderLayout.SOUTH);
						}
					}
				}
				
			});
			TreePanel.add(tree, BorderLayout.CENTER);
		}
		
		public void test() {
				Property<Integer> id=new Property<Integer>("id","int");
				Table student =new Table("student");
				DataBase db=new DataBase("ggg");
				List<Integer> list=new ArrayList<Integer>();
				list.add(1630);
				id.setContent(list);
				student.addProperty(id);
				db.addTable(student);
				dblist.addDb(db);
			}
		
		public void setListener(){
			pop_tree_newdb.addMouseListener(treeMenu.newdb_mouseListner(pw,br));//�½����ݿ�
			pop_tree_db_newTable.addMouseListener(treeMenu.newTableFrame_mouseListner(treeMenu,pw,br));//�½���
			pop_tree_db_del.addMouseListener(treeMenu.deleteDb_mouseListner(pw,br));//ɾ�����ݿ�
	//		    pop_tree_db_rename.addMouseListener(treeMenu.renameDb_mouseListner(pw,br));//���ݿ�������
			pop_tree_db_reRead.addMouseListener(treeMenu.reReadDb_mouseListner());//ˢ�����ݿ�
	//		pop_tree_db_readSQLFile.addMouseListener(treeMenu.readSQL_mouseListner());//����sql�ļ�
			pop_tree_table_newProperty.addMouseListener(treeMenu.newProperty_mouseListner(pw,br));//�½��ֶ�
			pop_tree_table_del.addMouseListener(treeMenu.deleteTable_mouseListner(pw,br));//ɾ����
			pop_tree_table_rename.addMouseListener(treeMenu.renameTable_mouseListner(pw,br));//��������
	//		pop_tree_property_set.addMouseListener(treeMenu.setProperty_mouseListner(tree));//�޸��ֶ�����
			pop_tree_property_del.addMouseListener(treeMenu.deleteProperty_mouseListner(pw,br));//ɾ���ֶ�
	//		pop_tree_property_rename.addMouseListener(treeMenu.renameProperty_mouseListner(tree));//�ֶ�������
			test();
			treeMenu.setTree();
		}
		
		public void setTreeMenu(){
			treeMenu.setTree();
		}
}
