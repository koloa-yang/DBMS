package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CTable {
	
	public CTable(){
	}
	//创建表
	public String createTable(String name,PrintWriter pw,BufferedReader br){
		pw.println("create table "+name);
		try {
			String get=br.readLine();
			if(get!=null)
				return get;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "the server has some question";
	}
	
	//删除表
	public String dropTable(String name,PrintWriter pw,BufferedReader br){
		pw.println("drop table "+name);
		try {
			String get=br.readLine();
			if(get!=null)
				return get;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "the server has some question";
	}
	
	//更新表名
	public String updateName(String name,String newName,PrintWriter pw,BufferedReader br){
		pw.println("alert table "+name+" rename to "+newName);
		try {
			String get=br.readLine();
			if(get!=null)
				return get;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "the server has some question";
	}
	
	//添加列
	public String addColumn(String name,String columnName,String type,PrintWriter pw,BufferedReader br){
		pw.println("alert table "+name+" add ("+columnName+" "+type+")");
		try {
			String get=br.readLine();
			if(get!=null)
				return get;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "the server has some question";
	} 
	
	//删除列
	public String dropColumn(String name,String columnName,PrintWriter pw,BufferedReader br){
		pw.println("alert table "+name+" drop "+columnName);
		try {
			String get=br.readLine();
			if(get!=null)
				return get;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "the server has some question";
	} 
}
