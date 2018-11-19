package DatabaseTree;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
	//��ʼ������
	private String name;
	private List<Table> tableList;
	/**
	 * ��ʼ�����ݿ�
	 * @return
	 */
	public DataBase(){
	}
	
	/**
	 * ���÷���
	 * @return
	 */
	
	public void setDataBase(ArrayList<Table> list){
		tableList=list;
	}
	
	public List<Table> getDataBase(){
		return tableList;
	}
	
	public int getTableNum() {
		return tableList.size();
	}
	public String getName(){
		return name;
	}
	
	public void setName(String new_name){
		name=new_name;
	}
	
	public boolean addTable(Table table){
		tableList.add(table);
		return true;
	}
	
	public boolean deleteTable(String TableName){
		int num=-1;
		for(int i=0;i<tableList.size();i++){
			if(tableList.get(i).getName().equals(TableName)){
				num=i;
				break;
			}
		}
		if(num>-1){
			tableList.remove(num);
			return true;
		}
		else
			return false;
	}
}
