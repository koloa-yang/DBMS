package client;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Command {
	
	private JPanel commandPanel;
	private JTextArea textArea_out;
	private JTextArea textArea_in;
	
	public Command(JPanel commandPanel){
		this.commandPanel=commandPanel;
		textArea_out=new JTextArea();
		textArea_out.setEditable(false);
		textArea_in=new JTextArea();
	}
	
	public void setCommand(){
		commandPanel.add(textArea_in, BorderLayout.SOUTH);
		commandPanel.add(textArea_out, BorderLayout.CENTER);
	}
	
	public void clear(){
		textArea_out.setText("");
	}
}
