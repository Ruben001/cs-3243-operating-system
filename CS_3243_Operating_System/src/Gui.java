import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.*;

/**
 * This class behaves as an Interface to the OSDriver
 * It display results from the cpu to GUI console
 * Group members:Corey Masters
				Mitchell Byrd
				Mohil Patel 
				Rahat Shahwar
				Ruben Munive
				Ivan Mba
 *
 */
public class Gui extends JFrame{	
	
	
	
	JLabel locateFile; 
	
	JLabel cpuSelect;
	
	JLabel algorithmSelect;
	
	JLabel empty;
	
	JTextField locateFileField;
	
	JButton start;
	
	JTextArea display;
	
	JTextArea cpuDisplay;
	
	Border labelBorder;
	
	String [] cpu = { "1", "2", "3", "4"};
	
	String [] algo = {"FCFS","SJF","PRIORITY"};
	
	JComboBox<String> algorithm;
	
	JComboBox<String> cpuNumber;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	public Container create(){

	
		
		JPanel finalLayout = new JPanel();
		
		JPanel pane = new JPanel();
		
		JPanel option = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane cpuScrollPane = new JScrollPane();
		
		
		
		labelBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		
		Font font = new Font("Cambria",17,17);
		
		Font displayFont = new Font("Cambria",13,13);
		
		
		
		
		
		
		
		
		finalLayout.setLayout(new GridLayout(1,3));//2 rows and 2 column
		
		option.setLayout(new GridLayout(4,2,5,20));
		
		
		
	
		
		
		
		
		
		
		locateFile = new JLabel("File Name");
		
		locateFile.setFont(font);
		
		locateFile.setOpaque(true);
		
		
		cpuSelect = new JLabel("Number of CPUs");
		
		cpuSelect.setFont(font);
		
		cpuNumber = new JComboBox<String>(cpu);
		
		cpuNumber.setSelectedIndex(0);
		
		
		algorithmSelect = new JLabel("Algorithm");
		
		algorithmSelect.setFont(font);
		
		algorithm = new JComboBox<String>(algo);
		
		algorithm.setSelectedIndex(0);
		
		
		empty = new JLabel();
		
		
		locateFileField = new JTextField("DataFile2-Cleaned.txt", 12);
		
		locateFileField.setBorder(labelBorder);
		
	
		
		
		
		start = new JButton("Run OS");
		
		start.setBorder(labelBorder);
		
		
		
		display = new JTextArea("Click Run to start", 23, 30);
		
		display.setFont(displayFont);
		
		display.setEditable(false);
		
		
		cpuDisplay = new JTextArea("Various CPU(s) are displayed here\n", 23, 30);
		
		cpuDisplay.setFont(displayFont);
		
		cpuDisplay.setEditable(false);
		
		
		
		
		DefaultCaret caret = (DefaultCaret)display.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(display);
		
		DefaultCaret cpuCaret = (DefaultCaret)cpuDisplay.getCaret();
		cpuCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		cpuScrollPane.setViewportView(cpuDisplay);
		
		
		
		
		
		
		
		
		option.add(locateFile);
		option.add(locateFileField);
		
		option.add(algorithmSelect);
		option.add(algorithm);
		
		option.add(cpuSelect);
		option.add(cpuNumber);
		
		
		option.add(empty);
		option.add(start);
		
		pane.add(option);
		
		finalLayout.add(scrollPane);
		finalLayout.add(cpuScrollPane);
		finalLayout.add(pane);
		
		
		
		
		
		
		StartHandler str = new StartHandler();
		
		start.addActionListener(str);

	

		return finalLayout;

		}
	/**
	 * Action listener for  button in Gui interface to run OS
	 * 
	 *
	 */
	public class StartHandler implements ActionListener
	
	{

	
		public void actionPerformed(ActionEvent ae) {
			
			if(ae.getSource().equals(start)){
				
				
				display.setText("");
				
				String[] args = {};
				
				int cpu = Integer.parseInt((String)cpuNumber.getSelectedItem());
				
				String algo = (String)algorithm.getSelectedItem();
				
				String selected = locateFileField.getText();
				
				OSDriver.setFileName(selected);
				OSDriver.setParameters(selected,algo,cpu);
				OSDriver.main(args);
				
				display.setText("Start \nFile Selected : " + selected + "\n" + "Algorithm Selected : " + algo + "\n" + "Number of CPU : " + cpu + "\n" + OSDriver.content + "\n\nComplete");
				
				cpuDisplay.setText("Start " + "\n" + OSDriver.cpuContent + "\n\nComplete");
				
				
				
				
				
			}
			
		}
		
		
		
		
	}
	/**
	 * Main method for Gui.java
	 * @param args
	 */
	
	public static void main(String[] args) {

		JFrame frame = new JFrame("OS Simulator");

		Gui ui = new Gui();

		frame.setContentPane(ui.create());

		frame.setVisible(true);

		frame.setSize(1100,600);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}


		


}
