package frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Checkbox;
import javax.swing.JCheckBox;

public class Ziduan {

	private JFrame frame;
	private JTextField textField;
	private String get;
	private String name;
	private String type;

	
	public Ziduan() {
	
		initialize();
	}
	/**
	 * Launch the application.
	 */
	public  void run() {
		EventQueue.invokeLater(new Runnable() {
			

			public void run() {
				try {
					frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u5B57\u6BB5");
		frame.setBounds(100, 100, 600, 600);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u5B57\u6BB5\u540D");
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		label.setBounds(82, 94, 82, 41);
		frame.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(233, 94, 224, 41);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("\u7C7B\u578B");
		label_1.setFont(new Font("宋体", Font.PLAIN, 20));
		label_1.setBounds(82, 239, 82, 41);
		frame.getContentPane().add(label_1);
		
		
		final ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton intrdbtn = new JRadioButton("int");
		intrdbtn.setFont(new Font("宋体", Font.PLAIN, 20));
		intrdbtn.setBounds(233, 239, 224, 41);
		frame.getContentPane().add(intrdbtn);
		buttonGroup.add(intrdbtn);
		
		JRadioButton varcharrdbtn = new JRadioButton("varchar");
		varcharrdbtn.setFont(new Font("宋体", Font.PLAIN, 20));
		varcharrdbtn.setBounds(233, 302, 224, 41);
		frame.getContentPane().add(varcharrdbtn);
		buttonGroup.add(varcharrdbtn);
		
		JRadioButton doublerdbtn = new JRadioButton("double");
		doublerdbtn.setFont(new Font("宋体", Font.PLAIN, 20));
		doublerdbtn.setBounds(233, 365, 224, 41);
		frame.getContentPane().add(doublerdbtn);
		buttonGroup.add(doublerdbtn);
		
		JButton handin = new JButton("\u63D0\u4EA4");
		handin.setFont(new Font("宋体", Font.PLAIN, 20));
		handin.setBounds(124, 435, 110, 33);
		frame.getContentPane().add(handin);
		
		JButton cancel = new JButton("\u53D6\u6D88");
		cancel.setFont(new Font("宋体", Font.PLAIN, 20));
		cancel.setBounds(360, 435, 110, 33);
		frame.getContentPane().add(cancel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		handin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
			name=textField.getText();
			Enumeration<AbstractButton> radioBtns=buttonGroup.getElements();
			type="int";
			while (radioBtns.hasMoreElements()) {      
				AbstractButton btn = radioBtns.nextElement();     
				if(btn.isSelected()){          
					type=btn.getText();  
					break;     
					}  
				} 
			get=name+" "+type;
			frame.dispose();
			}
			
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}
	
	public String getName(){
		return name;
	}
	
	public String getType(){
		return type;
	}
	
	public String getstr() {
		return get;
	}
}
