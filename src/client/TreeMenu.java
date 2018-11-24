package client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import DatabaseTree.*;
import frames.AddTableFrame;
import frames.Ziduan;

public class TreeMenu {
	AddTableFrame frame;
	CDatabase cdb;
	CTable ctb;
	CProperty cpt;
	DbList dblist;
	JTree tree;
	public TreeMenu(CDatabase cdb,CTable ctb,CProperty cpt,DbList dblist,JTree tree) {
		this.cdb=cdb;
		this.ctb=ctb;
		this.cpt=cpt;
		this.dblist=dblist;
		this.tree=tree;
	}
	
	//刷新tree
	public int setTree() {//返回数据库个数int
		tree.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode("\u670D\u52A1\u5668") {
					{	
						DefaultMutableTreeNode node_db;
						DefaultMutableTreeNode node_table;
						DefaultMutableTreeNode node_column;
						for(int i=0;i<dblist.getDbNum();i++) {//数据库节点
							node_db=new DefaultMutableTreeNode(dblist.getList().get(i).getName());
							for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {//表节点
								node_table=new DefaultMutableTreeNode(dblist.getList().get(i).getDataBase().get(j).getName());
								for(int k=0;k<dblist.getList().get(i).getDataBase().get(j).getPropertyNum();k++) {//字段节点
									node_column=new DefaultMutableTreeNode(dblist.getList().get(i).getDataBase().get(j).getPropertyList().get(k).getName());
									node_table.add(node_column);
								}
								node_db.add(node_table);
							}
							add(node_db);
						}
					}
				}
			));				
		return dblist.getList().size();
	}
	//新建数据库 ok
	public ActionListener newdb_mouseListner(final PrintWriter pw,final BufferedReader br) {
		ActionListener listener=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name;
				System.out.println("进入点击事件");
				label:
					while(true) {
						name=JOptionPane.showInputDialog(null,"请输入新建数据库的名字：\n","新建数据库",JOptionPane.PLAIN_MESSAGE); 
						if(name!=null) { 
							String string=cdb.createDatabase(name, pw, br);
							if(string.equals("success")) {
								DataBase new_db=new DataBase(name);
								new_db.setRead(true);
								dblist.addDb(new_db);
								setTree();
								break;
							}
							else {
								JOptionPane.showMessageDialog(null,string,"错误",JOptionPane.PLAIN_MESSAGE); 
								continue label;	
							}
						}
						else return;
					}
			}
		};
		return listener;
	}
	
	//新建表 ok
	public ActionListener newTableFrame_mouseListner(final TreeMenu treeMenu,final PrintWriter pw,final BufferedReader br) {
		ActionListener listener=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
		            public void run() {
		                try {
		                	frame = new AddTableFrame(treeMenu,pw,br);
							frame.setLocation(830, 350);
							frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
							frame.setVisible(true);
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }
		        });
			}
		};
		return listener;
	}
	
	//新建表窗口调用
	public MouseListener newTable_mouseListner(final PrintWriter pw,final BufferedReader br) {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				String name;
				System.out.println("进入点击事件");
				label:
					while(true) {
						String change=cdb.exchangDatabase(selectNode.toString(), pw, br);//切换数据库
						if(change.equals("success")) {
							String[] str; 
							String propertys;
							str=frame.getStr();		
							name=str[0];
							System.out.println(name);
							propertys=str[1];
							System.out.println(propertys);
							frame.dispose();
							String string=ctb.createTable(selectNode.toString(), name, propertys, pw, br);
							if(string.equals("success")) {
								for(int i=0;i<dblist.getDbNum();i++) {
									if(dblist.getList().get(i).getName().equals(selectNode.toString())) {
										Table table=new Table(name);
										table.setRead(true);
										Property property;
										String[] str2;
										String[] strings=new String[2];
										str2=propertys.split(",");
										for(String temp:str2) {
											strings=temp.split(" ");
											property=new Property<>(strings[0], strings[1]);
											table.addProperty(property);
										}
										dblist.getList().get(i).addTable(table);
										break;
									}
								}
								setTree();
								break;
							}
							else {
								JOptionPane.showMessageDialog(null,string,"错误",JOptionPane.PLAIN_MESSAGE); 
								continue label;
							}
						}
						else {
							JOptionPane.showMessageDialog(null,change,"错误",JOptionPane.PLAIN_MESSAGE); 
							continue label;
						}
					}
			}
		};
		return listener;
	}
	//删除数据库 ok
	public ActionListener deleteDb_mouseListner(final PrintWriter pw,final BufferedReader br) {
		ActionListener listener=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				System.out.println("进入点击事件");
				String string=cdb.dropDatabase(selectNode.toString(), pw, br);
				if(string.equals("success")) {
					dblist.deleteDb(selectNode.toString());
					JOptionPane.showMessageDialog(null,"删除成功！","删除数据库",JOptionPane.PLAIN_MESSAGE); 
					setTree();
				}
				else {
					JOptionPane.showMessageDialog(null,string,"错误",JOptionPane.PLAIN_MESSAGE);
					return;
				}
			}
		};
		
		return listener;
	}
//	//数据库重命名
//	public MouseListener renameDb_mouseListner(final JTree tree,DbList dblist) {
//		MouseListener listener=new MouseAdapter(){
//			@Override
//			public void mousePressed(MouseEvent e) {
//				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
//				String name;
//				System.out.println("进入点击事件");
//				label:
//					while(true) {
//						name=JOptionPane.showInputDialog(null,"请输入新建数据库的名字：\n","数据库重命名",JOptionPane.PLAIN_MESSAGE);
//						for(int i=0;i<dblist.getDbNum();i++) {
//							if(dblist.getList().get(i).getName().equals(name)) {
//								JOptionPane.showInputDialog(null,"此名字已存在，请重新输入：\n","错误",JOptionPane.PLAIN_MESSAGE); 
//								continue label;
//							}	
//						}
//						for(int j=0;j<dblist.getDbNum();j++) {
//							if(dblist.getList().get(j).getName().equals(selectNode.toString())) {
//								dblist.getList().get(j).setName(name);
//								System.out.println("已重命名数据库");
//								break;
//							}
//						}
//						break;
//					}
//				//setTree(tree);	
//			}
//		};
//		return listener;
//	}
	//重新读入数据库
	public MouseListener reReadDb_mouseListner() {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				setTree();
			}
		};
		return listener;
	}
//	//读入sql文件
//	public MouseListener readSQL_mouseListner(final JTree tree,DbList dblist) {
//		MouseListener listener=new MouseAdapter(){
//			@Override
//			public void mousePressed(MouseEvent e) {
//				JFileChooser jf = new JFileChooser();
//		        jf.setFileSelectionMode(JFileChooser.OPEN_DIALOG | JFileChooser.FILES_ONLY);
//		        jf.showDialog(null,null);
//		        File file = jf.getSelectedFile();
//		        //sql文件处理！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
//		        //setTree(tree);
//			}
//		};
//		return listener;
//	}
	
	//新建字段
	public ActionListener newProperty_mouseListner(final PrintWriter pw,final BufferedReader br) {
		ActionListener listener=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//选中某表
				for(int i=0;i<dblist.getDbNum();i++) {
					if(dblist.getList().get(i).getName().equals(selectNode.getParent().toString())) {
						for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
							if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.toString())) {
								Ziduan property=new Ziduan();
								property.run();
								dblist.getList().get(i).getDataBase().get(j).addProperty(new Property(property.getName(),property.getType()));
								/**
								 * 后续操作 晓宇加个循环
								 */
								break;
							}
						}
						break;
					}
				}
		        //setTree(tree);
			}
		};
		return listener;
	}
	
	//删除表 ok
	public ActionListener deleteTable_mouseListner(final PrintWriter pw,final BufferedReader br) {
		ActionListener listener=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//选中表
				System.out.println("进入点击事件");
				String change=cdb.exchangDatabase(selectNode.getParent().toString(), pw, br);//切换数据库
				if(change.equals("success")) {
					String string=ctb.dropTable(selectNode.toString(), pw, br);
					if(string.equals("success")) {
						for(int i=0;i<dblist.getDbNum();i++) {
							if(dblist.getList().get(i).getName().equals(selectNode.getParent().toString())) {//判断数据库
								for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
									if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.toString())) {//判断表
										dblist.getList().get(i).deleteTable(selectNode.toString());
										JOptionPane.showMessageDialog(null,"删除成功！","删除表",JOptionPane.PLAIN_MESSAGE); 
										setTree();
										return;
									}
								}
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(null,string,"错误",JOptionPane.PLAIN_MESSAGE); 
						return;
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"change","错误",JOptionPane.PLAIN_MESSAGE); 
					return;
				}
			}
		};
		return listener;
	}
	//表重命名
	public MouseListener renameTable_mouseListner(final PrintWriter pw,final BufferedReader br) {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//选中表
				String newTableName;
				System.out.println("进入点击事件");
				label:
					while(true) {
						newTableName=JOptionPane.showInputDialog(null,"请输入重命名的表的名字：\n","表重命名",JOptionPane.PLAIN_MESSAGE);
						if(newTableName!=null) {
							String change=cdb.exchangDatabase(selectNode.getParent().toString(), pw, br);//切换数据库
							if(change.equals("success")) {
								String string=ctb.updateName(selectNode.toString(), newTableName, pw, br);
								if(string.equals("success")) {
									for(int i=0;i<dblist.getDbNum();i++) {
										if(dblist.getList().get(i).getName().equals(selectNode.getParent().toString())) {//判断数据库
											for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
												if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.toString())) {
													dblist.getList().get(i).getDataBase().get(j).setName(newTableName);
													JOptionPane.showMessageDialog(null,"重命名成功！","表重命名",JOptionPane.PLAIN_MESSAGE); 
													setTree();
													return;
												}
											}
										}
									}	
								}
								else {
									JOptionPane.showMessageDialog(null,string,"错误",JOptionPane.PLAIN_MESSAGE); 
									continue label;
								}
							}
							else {
								JOptionPane.showMessageDialog(null,change,"错误",JOptionPane.PLAIN_MESSAGE); 
								continue label;
							}
						}
						else return;
					}
			}
		};
		return listener;
	}
//	//修改字段属性
//	public MouseListener setProperty_mouseListner(final JTree tree,DbList dblist) {
//		MouseListener listener=new MouseAdapter(){
//			@Override
//			public void mousePressed(MouseEvent e) {
//				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//选中某字段
//				for(int i=0;i<dblist.getDbNum();i++) {
//					if(dblist.getList().get(i).getName().equals(selectNode.getParent().getParent().toString())) {//判断数据库
//						for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
//							if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.getParent().toString())) {//判断表
//								for(int k=0;k<dblist.getList().get(i).getDataBase().get(j).getPropertyNum();k++)
//									if(dblist.getList().get(i).getDataBase().get(j).getPropertyList().get(k).equals(selectNode.toString())) {//判断字段
//										//字段属性弹窗！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
//										dblist.getList().get(i).getDataBase().get(j).getPropertyList().get(k).
//										break;
//									}
//								break;
//							}
//							
//						}
//						break;
//					}
//				}
//		        //setTree(tree);
//			}
//		};
//		return listener;
//	}
	//删除字段 
	public ActionListener deleteProperty_mouseListner(final PrintWriter pw,final BufferedReader br) {
		ActionListener listener=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//选中字段
				System.out.println("进入点击事件");
				String change=cdb.exchangDatabase(selectNode.getParent().getParent().toString(), pw, br);//切换数据库
				if(change.equals("success")) {
					String string=cpt.dropColumn(selectNode.getParent().toString(), selectNode.toString(), pw, br);
					if(string.equals("success")) {
						for(int i=0;i<dblist.getDbNum();i++) {
	  						if(dblist.getList().get(i).getName().equals(selectNode.getParent().getParent().toString())) {
	  							for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
	  								if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.getParent().toString())) {
	  									for(int k=0;k<dblist.getList().get(i).getDataBase().get(j).getPropertyNum();k++)
	  										if(dblist.getList().get(i).getDataBase().get(j).getPropertyList().get(k).getName().equals(selectNode.toString())) {
	  											dblist.getList().get(i).getDataBase().get(j).deleteProperty(selectNode.toString());
	  											JOptionPane.showMessageDialog(null,"删除成功！","删除字段",JOptionPane.PLAIN_MESSAGE); 
	  											setTree();
	  											return;
	  										}
	  								}
	  							}
	  						}
						}
					}
					else {
						JOptionPane.showMessageDialog(null,string,"错误",JOptionPane.PLAIN_MESSAGE); 
						return;
					}
				}
				else {
					JOptionPane.showMessageDialog(null,change,"错误",JOptionPane.PLAIN_MESSAGE); 
					return;
				}
			}
		};
		return listener;
	}
	
	//获取数据库中的数据库个数及名称
	public void getDatabase(PrintWriter pw,BufferedReader br){
		pw.println("get database");
		String[] databaseName = null;
		try {
			int num=Integer.valueOf(br.readLine());
			databaseName=new String[num];
			for(int i=0;i<num;i++){
				databaseName[i]=br.readLine();
				dblist.addDb(new DataBase(databaseName[i]));
			}
			setTree();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"出错了","错误",JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	//获取数据库中的表个数及名称
	public void getTable(PrintWriter pw,BufferedReader br){
		String[] tableName = null;
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		String change=cdb.exchangDatabase(selectNode.toString(), pw, br);//切换数据库
		DataBase db = null;
		for(int i=0;i<dblist.getDbNum();i++) {
			if(dblist.getList().get(i).getName().equals(selectNode.toString())) {
				db = dblist.getList().get(i);
				break;
			}
		}
		if(!db.getRead()){
			if(change.equals("success")){
				pw.println("get table");
				try {
					int num=Integer.valueOf(br.readLine());
					tableName=new String[num];
					for(int i=0;i<num;i++){
						tableName[i]=br.readLine(); 
						db.addTable(new Table(tableName[i]));
					}
					db.setRead(true);
					setTree();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"出错了","错误",JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
		
	//获取数据库中的表中字段数
	public void getProperty(String tableName,PrintWriter pw,BufferedReader br){
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		String change=cdb.exchangDatabase(selectNode.getParent().toString(), pw, br);//切换数据库
		Table table=null;
		for(int i=0;i<dblist.getDbNum();i++) {
			if(dblist.getList().get(i).getName().equals(selectNode.getParent().toString())) {
				for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
					if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.toString())) {
						table=dblist.getList().get(i).getDataBase().get(j);
						break;
					}
				}
				break;
			}
		}
		if(!table.getRead()){
			if(change.equals("success")){
				pw.println("get property on "+tableName);
				String[] propertyName = null;
				try {
					int num=Integer.valueOf(br.readLine());
					propertyName=new String[num];
					for(int i=0;i<num;i++){
						propertyName[i]=br.readLine(); 
						table.addProperty(new Property(propertyName[i],"varchar"));
					}
					table.setRead(true);
					setTree();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"你出错了","错误",JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
//	//字段重命名
//	public MouseListener renameProperty_mouseListner(final JTree tree,DbList dblist) {
//		MouseListener listener=new MouseAdapter(){
//			@Override
//			public void mousePressed(MouseEvent e) {
//				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
//				String name;
//				System.out.println("进入点击事件");
//				label:
//					while(true) {
//						name=JOptionPane.showInputDialog(null,"请输入新建数据库的名字：\n","数据库重命名",JOptionPane.PLAIN_MESSAGE);
//						for(int i=0;i<dblist.getDbNum();i++) {
//							if(dblist.getList().get(i).getName().equals(selectNode.getParent().getParent().toString())) {
//								for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
//									if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.getParent().toString())) {
//										for(int k=0;k<dblist.getList().get(i).getDataBase().get(j).getPropertyNum();k++)
//											if(dblist.getList().get(i).getDataBase().get(j).getPropertyList().get(k).getName().equals(name)) {
//												JOptionPane.showInputDialog(null,"此名字已存在，请重新输入：\n","错误",JOptionPane.PLAIN_MESSAGE); 
//												continue label;
//											}
//									}
//								}
//							}
//						}
//						for(int i=0;i<dblist.getDbNum();i++) {
//							if(dblist.getList().get(i).getName().equals(selectNode.getParent().getParent().toString())) {
//								for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
//									if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.getParent().toString())) {
//										for(int k=0;k<dblist.getList().get(i).getDataBase().get(j).getPropertyNum();k++)
//											if(dblist.getList().get(i).getDataBase().get(j).getPropertyList().get(k).equals(selectNode.toString())) {
//												dblist.getList().get(i).getDataBase().get(j).getPropertyList().get(k).setName(name);
//												System.out.println("已重命名该字段");
//												break;
//											}
//										break;
//									}
//								}
//								break;
//							}
//						}
//						break;
//					}
//				//setTree(tree);	
//			}
//		};
//		return listener;
//	}
}
