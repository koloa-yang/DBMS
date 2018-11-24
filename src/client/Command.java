package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Command {
	
	private JPanel commandPanel;
	private JTextPane textArea_out;
	private JTextField textField_in;
	private BufferedReader br;
	private PrintWriter pw;
	
	public Command(JPanel commandPanel,final PrintWriter pw,final BufferedReader br){
		this.commandPanel=commandPanel;
		this.pw=pw;
		this.br=br;
		//设置textArea和textField的属性
		textArea_out=new JTextPane();
		textArea_out.setEditable(false);
		textArea_out.setBackground(Color.black);
		textArea_out.setText("Microsoft Windows [版本 10.0.17134.407]\n(c) 2018 Microsoft Corporation。保留所有权利。\n SQL*Plus: Release 11.2.0.1.0\nCopyright (c) 1982, 2010, SQL.All rights reserved.\nSQL>");
		textArea_out.setForeground(Color.white);
		textField_in=new JTextField();
		textField_in.setPreferredSize(new Dimension(0, 30));
		textField_in.addActionListener(new ActionListener(){
		   public void actionPerformed(ActionEvent arg0) {
			   String content=textField_in.getText();
			   String back="";
			   pw.println(content);
			   try {
				   back=br.readLine();
			  } catch (IOException e) {
				// TODO Auto-generated catch block
				  e.printStackTrace();
			  }
			   String out=textArea_out.getText();
			   if(content.contains("select")){
				   
			   }
			   else{
				   out+=back;
				   textArea_out.setText(out);
			   }
				   
			   textField_in.setText("");
			   }    
			  });
	}
	
	public void setCommand(){
		commandPanel.add(textField_in, BorderLayout.SOUTH);
		commandPanel.add(textArea_out, BorderLayout.CENTER);
	}
	
	public void clear(){
		textArea_out.setText("");
	}
}
