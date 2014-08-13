import java.awt.event.*;

import javax.swing.*;

import acm.program.*;

public class NameSurfer extends ConsoleProgram{
	public void init(){ // why not main here
		name = new JLabel("Name");
		graph = new JButton("Graph");
		clear = new JButton("Clear");
		textField = new JTextField(20);
		textField.addActionListener(this);
		add(name,SOUTH);
		add(textField,SOUTH);
		add(graph,SOUTH);
		add(clear,SOUTH);
		addActionListeners();
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()== graph){
			println("Graph: " + textField.getText());
		}else if (e.getSource() == clear){
			//removeAll();
			println("Clear");
		}
	}
	
	private JLabel name;
	private JTextField textField;
	private JButton graph;
	private JButton clear;
}
