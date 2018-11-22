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
	//主页面传参
	    private JPanel TreePanel;
	    private JPanel contentPanel;
	    private JPanel tablePanel;
	    private PrintWriter pw;
	    private BufferedReader br;
	//方法调用
	    private DbList dblist=new DbList();
		private CDatabase cdb = new CDatabase(dblist);
		private CTable ctb = new CTable(dblist);
		private CProperty cpt =new CProperty(dblist);
		private JTree tree= new JTree();
	//树右键菜单
		private TreeMenu treeMenu;
		private JTable table;
		private JButton btnadd=new JButton("增加数据");
		private JButton btndelete=new JButton("删除数据");
		//空白处
		private JPopupMenu popMenu_tree_newdb;
		private JMenuItem pop_tree_newdb;
		//数据库
		private JPopupMenu popMenu_tree_db;
		private JMenuItem pop_tree_db_newTable;
		private JMenuItem pop_tree_db_del;
		private JMenuItem pop_tree_db_rename;
		private JMenuItem pop_tree_db_reRead;
		private JMenuItem pop_tree_db_readSQLFile;
		//表
		private JPopupMenu popMenu_tree_table;
		private JMenuItem pop_tree_table_newProperty;
		private JMenuItem pop_tree_table_del;
		private JMenuItem pop_tree_table_rename;
		//字段
		private JPopupMenu popMenu_tree_property;
		private JMenuItem pop_tree_property_set;
		private JMenuItem pop_tree_property_del;
		private JMenuItem pop_tree_property_rename;
			
		public Tree(JPanel TreePanel,JPanel tablePanel,PrintWriter pw,BufferedReader br){
			this.TreePanel=TreePanel;
			this.tablePanel=tablePanel;
			this.pw=pw;
			this.br=br;
			//树
			popMenu_tree_newdb=new JPopupMenu();
			pop_tree_newdb=new JMenuItem("新建数据库");
			popMenu_tree_newdb.add(pop_tree_newdb);
			
			popMenu_tree_db=new JPopupMenu();
			pop_tree_db_newTable=new JMenuItem("新建表");
			pop_tree_db_del=new JMenuItem("删除数据库");
			pop_tree_db_rename=new JMenuItem("重命名");
			pop_tree_db_reRead=new JMenuItem("刷新");
			pop_tree_db_readSQLFile=new JMenuItem("运行SQL文件");
			popMenu_tree_db.add(pop_tree_db_newTable);
			popMenu_tree_db.add(pop_tree_db_del);
			popMenu_tree_db.add(pop_tree_db_rename);
			popMenu_tree_db.add(pop_tree_db_reRead);
			popMenu_tree_db.add(pop_tree_db_readSQLFile);
			
			popMenu_tree_table=new JPopupMenu();
			pop_tree_table_newProperty=new JMenuItem("新建字段");
			pop_tree_table_del=new JMenuItem("删除表");
			pop_tree_table_rename=new JMenuItem("重命名");
			popMenu_tree_table.add(pop_tree_table_newProperty);
			popMenu_tree_table.add(pop_tree_table_del);
			popMenu_tree_table.add(pop_tree_table_rename);
			
			popMenu_tree_property=new JPopupMenu();
			pop_tree_property_set=new JMenuItem("修改属性");
			pop_tree_property_del=new JMenuItem("删除字段");
			pop_tree_property_rename=new JMenuItem("重命名");
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
							if(selectNode.getLevel()==1) {//0是根节点 1是数据库 2是表 3是列
								popMenu_tree_db.show(TreePanel, e.getX(), e.getY()+30);
							}
							else if(selectNode.getLevel()==2) {//0是根节点 1是数据库 2是表 3是列
								popMenu_tree_table.show(TreePanel, e.getX(), e.getY()+30);
							}
							else if(selectNode.getLevel()==3) {//0是根节点 1是数据库 2是表 3是列
								popMenu_tree_property.show(TreePanel, e.getX(), e.getY()+30);
							}
						}
						else {
							popMenu_tree_newdb.show(TreePanel, e.getX(), e.getY()+30);
						}
							
	                }
					else if(e.getClickCount()==2) {
						if(selectNode.getLevel()==2) {//如果双击表节点 打开表
							//显示表内容（显示榕儿的界面）
	//						int rownum=8; //此处需要获得记录的数量
							
							table=new JTable();
							String[] columns={"ID","姓名","性别"};//此处需要获得字段名称
							final DefaultTableModel model=new DefaultTableModel(columns,0);
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
							contentPanel.remove(tablePanel);
							tablePanel = new JPanel();
							contentPanel.add(tablePanel, BorderLayout.CENTER);
							JTableHeader myt=table.getTableHeader();
							tablePanel.add(myt,BorderLayout.NORTH);
							tablePanel.add(table,BorderLayout.CENTER);
							tablePanel.add(btnadd,BorderLayout.SOUTH);
							tablePanel.add(btndelete,BorderLayout.SOUTH);
						}
						else if(selectNode.getLevel()==3) {//如果双击字段节点 右侧显示选中字段的数据信息
							//右侧显示选中字段的数据信息（显示榕儿的界面） 一列
	//						int rownum=8; //此处需要获得记录的数量
							
							table=new JTable();
							String[] columns={"ID"};//此处需要获得字段名称
							DefaultTableModel model=new DefaultTableModel(columns,0);
							table.setModel(model);
							TableColumnModel columnModel=table.getColumnModel();
							int count=columnModel.getColumnCount();
							for(int i=0;i<count;i++){
								javax.swing.table.TableColumn column=columnModel.getColumn(i);
								column.setPreferredWidth(800);//设置列的宽度
								}
							model.addRow(new Object[]{16301001});//此处需要一行一行读数据，加进去
							model.addRow(new Object[]{16301002});//此处需要一行一行读数据，加进去
							model.addRow(new Object[]{16301003});//此处需要一行一行读数据，加进去
							model.addRow(new Object[]{16301004});//此处需要一行一行读数据，加进去
							
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
			pop_tree_newdb.addMouseListener(treeMenu.newdb_mouseListner(pw,br));//新建数据库
			pop_tree_db_newTable.addMouseListener(treeMenu.newTableFrame_mouseListner(treeMenu,pw,br));//新建表
			pop_tree_db_del.addMouseListener(treeMenu.deleteDb_mouseListner(pw,br));//删除数据库
	//		    pop_tree_db_rename.addMouseListener(treeMenu.renameDb_mouseListner(pw,br));//数据库重命名
			pop_tree_db_reRead.addMouseListener(treeMenu.reReadDb_mouseListner());//刷新数据库
	//		pop_tree_db_readSQLFile.addMouseListener(treeMenu.readSQL_mouseListner());//运行sql文件
			pop_tree_table_newProperty.addMouseListener(treeMenu.newProperty_mouseListner(pw,br));//新建字段
			pop_tree_table_del.addMouseListener(treeMenu.deleteTable_mouseListner(pw,br));//删除表
			pop_tree_table_rename.addMouseListener(treeMenu.renameTable_mouseListner(pw,br));//表重命名
	//		pop_tree_property_set.addMouseListener(treeMenu.setProperty_mouseListner(tree));//修改字段属性
			pop_tree_property_del.addMouseListener(treeMenu.deleteProperty_mouseListner(pw,br));//删除字段
	//		pop_tree_property_rename.addMouseListener(treeMenu.renameProperty_mouseListner(tree));//字段重命名
			test();
			treeMenu.setTree();
		}
		
		public void setTreeMenu(){
			treeMenu.setTree();
		}
}
