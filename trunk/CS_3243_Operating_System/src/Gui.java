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
	
	JLabel empty;
	
	JTextField locateFileField;
	
	JButton start;
	
	JTextArea display;
	
	Border labelBorder;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	public Container create(){

	
		
		JPanel finalLayout = new JPanel();
		
		JPanel pane = new JPanel();
		
		JPanel option = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		
		labelBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		
		Font font = new Font("Verdana", Font.BOLD, 18);
		
		
		
		
		
		
		
		
		finalLayout.setLayout(new GridLayout(1,2));//1 rows and 2 column
		
		option.setLayout(new GridLayout(2,1,2,20));
		
		
		
	
		
		
		
		
		
		
		locateFile = new JLabel("File Name");
		
		locateFile.setFont(font);
		
		locateFile.setOpaque(true);
		
		
		
		
		empty = new JLabel();
		
		
		locateFileField = new JTextField("DataFile2-Cleaned.txt", 15);
		
		locateFileField.setBorder(labelBorder);
		
	
		
		
		
		start = new JButton("Run OS");
		
		start.setBorder(labelBorder);
		
		
		
		display = new JTextArea("Click Run to start", 20, 30);
		
		display.setEditable(false);
		
		
		
		
		DefaultCaret caret = (DefaultCaret)display.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane.setViewportView(display);
		
		
		
		
		
		option.add(locateFile);
		option.add(locateFileField);
		
		option.add(empty);
		option.add(start);
		
		pane.add(option);
		
		finalLayout.add(scrollPane);
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
				
				String selected = locateFileField.getText();
				OSDriver.setFileName(selected);
				OSDriver.main(args);
				
				display.setText("File Selected : " + selected + "\n" + OSDriver.content);
				
				
				
				
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

		frame.setSize(800,700);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}


		


}
