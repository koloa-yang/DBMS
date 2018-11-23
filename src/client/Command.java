package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Command {
	
	private JPanel commandPanel;
	private JTextArea textArea_out;
	private JTextField textField_in;
	
	public Command(JPanel commandPanel){
		this.commandPanel=commandPanel;
		//设置textArea和textField的属性
		textArea_out=new JTextArea();
		textArea_out.setEditable(false);
		textArea_out.setBackground(Color.black);
		
		textField_in=new JTextField();
		textField_in.setPreferredSize(new Dimension(0, 30));
	}
	
	public void setCommand(){
		commandPanel.add(textField_in, BorderLayout.SOUTH);
		commandPanel.add(textArea_out, BorderLayout.CENTER);
	}
	
	public void clear(){
		textArea_out.setText("");
	}
}
