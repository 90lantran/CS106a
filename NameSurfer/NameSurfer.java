import java.awt.event.*;

import javax.swing.*;

import acm.program.*;

public class NameSurfer extends Program{
	public void init(){ // why not main here
		name = new JLabel("Name");
		graphbutton = new JButton("Graph");
		clear = new JButton("Clear");
		textField = new JTextField(20);
		textField.addActionListener(this);
		add(name,SOUTH);
		add(textField,SOUTH);
		add(graphbutton,SOUTH);
		add(clear,SOUTH);
		addActionListeners();
		
		graph = new NameSurferGraph();
		add(graph);
	}
	
	public void actionPerformed(ActionEvent e){
		NameSurferDataBase data = new NameSurferDataBase();
		if(e.getSource()== graphbutton){
			if(data.findEntry(textField.getText()) != null){
				//println("Graph: " + data.findEntry(textField.getText()).toString());
				graph.addEntry(data.findEntry(textField.getText()));
				
			}
			//println("Graph: " + "\"" +textField.getText()+"\"");
		}else if (e.getSource() == clear){
			//removeAll();
			//println("Clear");
		}
	}
	
	private JLabel name;
	private JTextField textField;
	private JButton graphbutton;
	private JButton clear;
	private NameSurferGraph graph;
}
