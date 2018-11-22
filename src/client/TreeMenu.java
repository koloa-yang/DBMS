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
	
	//ˢ��tree
	public int setTree() {//�������ݿ����int
		tree.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode("\u670D\u52A1\u5668") {
					{	
						DefaultMutableTreeNode node_db;
						DefaultMutableTreeNode node_table;
						DefaultMutableTreeNode node_column;
						for(int i=0;i<dblist.getDbNum();i++) {//���ݿ�ڵ�
							node_db=new DefaultMutableTreeNode(dblist.getList().get(i).getName());
							for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {//��ڵ�
								node_table=new DefaultMutableTreeNode(dblist.getList().get(i).getDataBase().get(j).getName());
								for(int k=0;k<dblist.getList().get(i).getDataBase().get(j).getPropertyNum();k++) {//�ֶνڵ�
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
	//�½����ݿ� ok
	public MouseListener newdb_mouseListner(final PrintWriter pw,final BufferedReader br) {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				String name;
				System.out.println("�������¼�");
				label:
					while(true) {
						name=JOptionPane.showInputDialog(null,"�������½����ݿ�����֣�\n","�½����ݿ�",JOptionPane.PLAIN_MESSAGE); 
						if(name!=null) {
							String string=cdb.createDatabase(name, pw, br);
							if(string.equals("success")) {
								dblist.addDb(new DataBase(name));
								setTree();
								break;
							}
							else {
								JOptionPane.showMessageDialog(null,string,"����",JOptionPane.PLAIN_MESSAGE); 
								continue label;	
							}
						}
						else return;
					}
			}
		};
		return listener;
	}
	
	//�½��� ok
	public MouseListener newTableFrame_mouseListner(final TreeMenu treeMenu,final PrintWriter pw,final BufferedReader br) {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
		            public void run() {
		                try {
		                	frame = new AddTableFrame(treeMenu,pw,br);
							frame.setLocation(830, 350);
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
	
	//�½����ڵ���
	public MouseListener newTable_mouseListner(final PrintWriter pw,final BufferedReader br) {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				String name;
				System.out.println("�������¼�");
				label:
					while(true) {
						String change=cdb.exchangDatabase(selectNode.toString(), pw, br);//�л����ݿ�
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
								JOptionPane.showMessageDialog(null,string,"����",JOptionPane.PLAIN_MESSAGE); 
								continue label;
							}
						}
						else {
							JOptionPane.showMessageDialog(null,change,"����",JOptionPane.PLAIN_MESSAGE); 
							continue label;
						}
					}
			}
		};
		return listener;
	}
	//ɾ�����ݿ� ok
	public MouseListener deleteDb_mouseListner(final PrintWriter pw,final BufferedReader br) {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				System.out.println("�������¼�");
				String string=cdb.dropDatabase(selectNode.toString(), pw, br);
				if(string.equals("success")) {
					dblist.deleteDb(selectNode.toString());
					JOptionPane.showMessageDialog(null,"ɾ���ɹ���","ɾ�����ݿ�",JOptionPane.PLAIN_MESSAGE); 
					setTree();
				}
				else {
					JOptionPane.showMessageDialog(null,string,"����",JOptionPane.PLAIN_MESSAGE);
					return;
				}
			}
		};
		
		return listener;
	}
//	//���ݿ�������
//	public MouseListener renameDb_mouseListner(final JTree tree,DbList dblist) {
//		MouseListener listener=new MouseAdapter(){
//			@Override
//			public void mousePressed(MouseEvent e) {
//				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
//				String name;
//				System.out.println("�������¼�");
//				label:
//					while(true) {
//						name=JOptionPane.showInputDialog(null,"�������½����ݿ�����֣�\n","���ݿ�������",JOptionPane.PLAIN_MESSAGE);
//						for(int i=0;i<dblist.getDbNum();i++) {
//							if(dblist.getList().get(i).getName().equals(name)) {
//								JOptionPane.showInputDialog(null,"�������Ѵ��ڣ����������룺\n","����",JOptionPane.PLAIN_MESSAGE); 
//								continue label;
//							}	
//						}
//						for(int j=0;j<dblist.getDbNum();j++) {
//							if(dblist.getList().get(j).getName().equals(selectNode.toString())) {
//								dblist.getList().get(j).setName(name);
//								System.out.println("�����������ݿ�");
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
	//���¶������ݿ�
	public MouseListener reReadDb_mouseListner() {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				setTree();
			}
		};
		return listener;
	}
//	//����sql�ļ�
//	public MouseListener readSQL_mouseListner(final JTree tree,DbList dblist) {
//		MouseListener listener=new MouseAdapter(){
//			@Override
//			public void mousePressed(MouseEvent e) {
//				JFileChooser jf = new JFileChooser();
//		        jf.setFileSelectionMode(JFileChooser.OPEN_DIALOG | JFileChooser.FILES_ONLY);
//		        jf.showDialog(null,null);
//		        File file = jf.getSelectedFile();
//		        //sql�ļ�����������������������������������������������������������������������������������������������������������������������
//		        //setTree(tree);
//			}
//		};
//		return listener;
//	}
	
	//�½��ֶ�
	public MouseListener newProperty_mouseListner(final PrintWriter pw,final BufferedReader br) {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//ѡ��ĳ��
				for(int i=0;i<dblist.getDbNum();i++) {
					if(dblist.getList().get(i).getName().equals(selectNode.getParent().toString())) {
						for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
							if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.toString())) {
								Ziduan property=new Ziduan();
								property.run();
								dblist.getList().get(i).getDataBase().get(j).addProperty(new Property(property.getName(),property.getType()));
								/**
								 * ��������
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
	
	//ɾ���� ok
	public MouseListener deleteTable_mouseListner(final PrintWriter pw,final BufferedReader br) {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//ѡ�б�
				System.out.println("�������¼�");
				String change=cdb.exchangDatabase(selectNode.getParent().toString(), pw, br);//�л����ݿ�
				if(change.equals("success")) {
					String string=ctb.dropTable(selectNode.toString(), pw, br);
					if(string.equals("success")) {
						for(int i=0;i<dblist.getDbNum();i++) {
							if(dblist.getList().get(i).getName().equals(selectNode.getParent().toString())) {//�ж����ݿ�
								for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
									if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.toString())) {//�жϱ�
										dblist.getList().get(i).deleteTable(selectNode.toString());
										JOptionPane.showMessageDialog(null,"ɾ���ɹ���","ɾ����",JOptionPane.PLAIN_MESSAGE); 
										setTree();
										return;
									}
								}
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(null,string,"����",JOptionPane.PLAIN_MESSAGE); 
						return;
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"change","����",JOptionPane.PLAIN_MESSAGE); 
					return;
				}
			}
		};
		return listener;
	}
	//�������� �����������������������������﷨����
	public MouseListener renameTable_mouseListner(final PrintWriter pw,final BufferedReader br) {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//ѡ�б�
				String newTableName;
				System.out.println("�������¼�");
				label:
					while(true) {
						newTableName=JOptionPane.showInputDialog(null,"�������������ı�����֣�\n","��������",JOptionPane.PLAIN_MESSAGE);
						if(newTableName!=null) {
							String change=cdb.exchangDatabase(selectNode.getParent().toString(), pw, br);//�л����ݿ�
							if(change.equals("success")) {
								String string=ctb.updateName(selectNode.toString(), newTableName, pw, br);
								if(string.equals("success")) {
									for(int i=0;i<dblist.getDbNum();i++) {
										if(dblist.getList().get(i).getName().equals(selectNode.getParent().toString())) {//�ж����ݿ�
											for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
												if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.toString())) {
													dblist.getList().get(i).getDataBase().get(j).setName(newTableName);
													JOptionPane.showMessageDialog(null,"�������ɹ���","��������",JOptionPane.PLAIN_MESSAGE); 
													setTree();
													return;
												}
											}
										}
									}	
								}
								else {
									JOptionPane.showMessageDialog(null,string,"����",JOptionPane.PLAIN_MESSAGE); 
									continue label;
								}
							}
							else {
								JOptionPane.showMessageDialog(null,change,"����",JOptionPane.PLAIN_MESSAGE); 
								continue label;
							}
						}
						else return;
					}
			}
		};
		return listener;
	}
//	//�޸��ֶ�����
//	public MouseListener setProperty_mouseListner(final JTree tree,DbList dblist) {
//		MouseListener listener=new MouseAdapter(){
//			@Override
//			public void mousePressed(MouseEvent e) {
//				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//ѡ��ĳ�ֶ�
//				for(int i=0;i<dblist.getDbNum();i++) {
//					if(dblist.getList().get(i).getName().equals(selectNode.getParent().getParent().toString())) {//�ж����ݿ�
//						for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
//							if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.getParent().toString())) {//�жϱ�
//								for(int k=0;k<dblist.getList().get(i).getDataBase().get(j).getPropertyNum();k++)
//									if(dblist.getList().get(i).getDataBase().get(j).getPropertyList().get(k).equals(selectNode.toString())) {//�ж��ֶ�
//										//�ֶ����Ե���������������������������������������������������������������������������������������������������������
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
	//ɾ���ֶ� �����������������������������޷��ٽ�����ɾ��
	public MouseListener deleteProperty_mouseListner(final PrintWriter pw,final BufferedReader br) {
		MouseListener listener=new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//ѡ���ֶ�
				System.out.println("�������¼�");
				String change=cdb.exchangDatabase(selectNode.getParent().getParent().toString(), pw, br);//�л����ݿ�
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
	  											JOptionPane.showMessageDialog(null,"ɾ���ɹ���","ɾ���ֶ�",JOptionPane.PLAIN_MESSAGE); 
	  											setTree();
	  											return;
	  										}
	  								}
	  							}
	  						}
						}
					}
					else {
						JOptionPane.showMessageDialog(null,string,"����",JOptionPane.PLAIN_MESSAGE); 
						return;
					}
				}
				else {
					JOptionPane.showMessageDialog(null,change,"����",JOptionPane.PLAIN_MESSAGE); 
					return;
				}
			}
		};
		return listener;
	}
//	//�ֶ�������
//	public MouseListener renameProperty_mouseListner(final JTree tree,DbList dblist) {
//		MouseListener listener=new MouseAdapter(){
//			@Override
//			public void mousePressed(MouseEvent e) {
//				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
//				String name;
//				System.out.println("�������¼�");
//				label:
//					while(true) {
//						name=JOptionPane.showInputDialog(null,"�������½����ݿ�����֣�\n","���ݿ�������",JOptionPane.PLAIN_MESSAGE);
//						for(int i=0;i<dblist.getDbNum();i++) {
//							if(dblist.getList().get(i).getName().equals(selectNode.getParent().getParent().toString())) {
//								for(int j=0;j<dblist.getList().get(i).getTableNum();j++) {
//									if(dblist.getList().get(i).getDataBase().get(j).getName().equals(selectNode.getParent().toString())) {
//										for(int k=0;k<dblist.getList().get(i).getDataBase().get(j).getPropertyNum();k++)
//											if(dblist.getList().get(i).getDataBase().get(j).getPropertyList().get(k).getName().equals(name)) {
//												JOptionPane.showInputDialog(null,"�������Ѵ��ڣ����������룺\n","����",JOptionPane.PLAIN_MESSAGE); 
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
//												System.out.println("�����������ֶ�");
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
