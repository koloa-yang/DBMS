package DatabaseTree;

import java.util.ArrayList;
import java.util.List;

public class Property<T> {
	/**
	 * ���ñ���
	 */
	private String name;//����
	private String type;//����
	private List<T> content;//����
	private boolean primaryKey;//�ж��Ƿ�Ϊ����
	private boolean foreignKey;//�ж��Ƿ�Ϊ���
	private T defaultKey;//Ĭ��ֵ
	/**
	 * Լ����������
	 */
	//��ֵ��СԼ��
	private int Max;//��󳤶�
	private int Min=0;//��С����
	//��ֵ����Լ��
	private T[] limit;//��ֹ��������
	private boolean isNull;//�ж��Ƿ�Ϊ��
	private boolean unique;//�ж��ɷ����ظ�����
	
	/**
	 * ���÷���
	 */
	public Property(String name){
		content=new ArrayList<T>();
		this.name=name;
	}
	
	public List<T> getAllContent(){
		return content;
	}
	
	public T getContent(int num){
		return content.get(num);
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String new_name){
		name=new_name;
	}
	
	public void updateContent(int num,T new_content){
		content.set(num, new_content);
	}
	
	public void updateMax(int num){
		Max=num;
	}
	
	public void updateMin(int num){
		Min=num;
	}
	
	public void setPrimaryKey(boolean judge){
		primaryKey=judge;
	}
	
	public void setForeignKey(boolean judge){
		foreignKey=judge;
	}
	
	public void setDefaultKey(T defaultkey){
		defaultKey=defaultkey;
	}
	
	public void setNull(boolean judge){
		isNull=judge;
	}
	
	public void setUnique(boolean judge){
		unique=judge;
	}
}
