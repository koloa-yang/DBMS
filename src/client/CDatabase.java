package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class CDatabase {
	
	public CDatabase(){
	}
	
	//�л����ݿ�
	public String exchangDatabase(String name,PrintWriter pw,BufferedReader br){
		pw.println("use "+name);
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
	
	//�������ݿ�
	public String createDatabase(String name,PrintWriter pw,BufferedReader br){
		pw.println("create database "+name);
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
	
	//�Ƴ����ݿ�
	public String dropDatabase(String name,PrintWriter pw,BufferedReader br){
		pw.println("drop database "+name);
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