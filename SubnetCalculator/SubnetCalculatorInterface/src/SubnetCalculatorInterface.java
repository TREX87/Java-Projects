import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

import SubnetCalculator.ConvertBinaryToDecimalAddress;
import SubnetCalculator.SubnetCalculator;
import net.miginfocom.swing.MigLayout;



public class SubnetCalculatorInterface {
	
	JFrame frame = new JFrame("Subnet Calculator");
	
	JToolBar toolBar = new JToolBar();
	
	JTextField textField = new JTextField(15);
	
	JButton calculate = new JButton("Calculate");
	JButton remove = new JButton("Remove");
	
	JPanel panel = new JPanel();
	
	JTextArea textArea1 = new JTextArea(1, 10);
	JTextArea textArea2 = new JTextArea(1, 10);
	JTextArea textArea3 = new JTextArea(1, 10);
	JTextArea textArea4 = new JTextArea(1, 10);
	JTextArea textArea5 = new JTextArea(1, 10);
	JTextArea textAreaBinary1 = new JTextArea(1, 22);
	JTextArea textAreaBinary2 = new JTextArea(1, 22);
	JTextArea textAreaBinary3 = new JTextArea(1, 22);
	
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenu help = new JMenu("Help");
	JMenuItem fileItem = new JMenuItem("Export VLSM to text file...");
	JMenuItem exitItem = new JMenuItem("Exit");
	JMenuItem helpItem = new JMenuItem("About us");
	
	JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
	JSeparator separatorLeft = new JSeparator(SwingConstants.HORIZONTAL);
	JSeparator separatorRight = new JSeparator(SwingConstants.HORIZONTAL);
	JSeparator lastSeparator1 = new JSeparator(SwingConstants.HORIZONTAL);
	JSeparator lastSeparator2 = new JSeparator(SwingConstants.HORIZONTAL);
	JSeparator lastSeparator3 = new JSeparator(SwingConstants.HORIZONTAL);
	
	JLabel result = new JLabel("Results");
	JLabel binarySection = new JLabel("Binary Section");
	JLabel networkAddress = new JLabel("Network address: ");
	JLabel broadcastAddress = new JLabel("Broadcast address: ");
	JLabel hosts = new JLabel("# Hosts: ");
	JLabel subnetMask = new JLabel("Subnet Mask: ");
	JLabel networkBinaryAddress = new JLabel("Network address: ");
	JLabel broadcastbinaryAddress = new JLabel("Broadcast address: ");
	JLabel subnetMaskBinary = new JLabel("Subnet Mask: ");
	JLabel subnetBits = new JLabel("Subnet Bits: ");
	
	String subnetNetwork = "";
	String networkAddressText = "";
	String broadcastAddressText = "";
	String hostsText = "";
	String subnetMaskText = "";
	String subnetBitsText = "";
	String networkAddressBinaryText = "";
	String broadcastAddressBinaryText = "";
	String subnetMaskBinaryText = "";
	
	public SubnetCalculatorInterface() {
		
		result.setFont(new Font("Elephant", Font.PLAIN, 14));
		binarySection.setFont(new Font("Elephant", Font.PLAIN, 14));
		
		frame.setJMenuBar(menuBar);
		menuBar.add(file);
		menuBar.add(help);
		
		file.add(fileItem);
		file.add(exitItem);
		help.add(helpItem);
		
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		helpItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Name: Artur Mukhametyanov / StudentID: 059431148",
							"Student Info", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		fileItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//string that will be written into the file
					String ultimateText = "Network Address: " + networkAddressText;
					
					
					File newTextFile = new File("C:/Users/Artur/workspace/SubnetCalculator/Address.txt");
					
					FileWriter fw = new FileWriter(newTextFile);
					fw.write(ultimateText);
					fw.write(System.lineSeparator());
					
					//we divide string on many pieces because we need every piece in the new line
					ultimateText = "Broadcast Address: " + broadcastAddressText;
					fw.write(ultimateText);
					fw.write(System.lineSeparator()); //this line demonstrates method that inserts new line to the intput flow
					ultimateText = "# Hosts: " + hostsText;
					fw.write(ultimateText);
					fw.write(System.lineSeparator());
					ultimateText = "Subnet Mask: " + subnetMaskText;
					fw.write(ultimateText);
					fw.write(System.lineSeparator());
					ultimateText = "Mask Bits: " + subnetBitsText;
					fw.write(ultimateText);
					fw.write(System.lineSeparator());
					ultimateText = "Binary network address: " + networkAddressBinaryText;
					fw.write(ultimateText);
					fw.write(System.lineSeparator());
					ultimateText = "Binary broadcast address: " + broadcastAddressBinaryText;
					fw.write(ultimateText);
					fw.write(System.lineSeparator());
					ultimateText = "Binary subnet mask: " + subnetMaskBinaryText;
					fw.write(ultimateText);
					
					fw.close();
					
				} catch(IOException iox) {
					//do stuff with exception
					iox.printStackTrace();
				}
			}
		});
		
		toolBar.add(textField);
		toolBar.add(calculate);
		toolBar.add(remove);
		
		calculate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				subnetNetwork = textField.getText();
				networkAddressText = subnetNetwork;
				
				textArea1.setText(networkAddressText);
				
				SubnetCalculator calculator = new SubnetCalculator(subnetNetwork);
				
				//calculates all necessary items through methods of SubnetCalculator class and puts values
				//into suitable area in the interface
				broadcastAddressText = calculator.calculateBroadcastAddress();
				textArea2.setText(broadcastAddressText);
				hostsText = calculator.calculateHostAddresses();
				textArea3.setText(hostsText);
				subnetMaskText = calculator.calculateSubnetMask();
				textArea4.setText(subnetMaskText);
				subnetBitsText = Integer.toString(calculator.getMaskBits());
				textArea5.setText(subnetBitsText);
				
				ConvertBinaryToDecimalAddress c = new ConvertBinaryToDecimalAddress();
				networkAddressBinaryText = c.convertDecimalToBinary(calculator.getFormatedSubnet());
				textAreaBinary1.setText(networkAddressBinaryText);
				
				broadcastAddressBinaryText = calculator.getBinaryBroadcastAddress();
				textAreaBinary2.setText(broadcastAddressBinaryText);
				subnetMaskBinaryText = calculator.getBinarySubnetMaskString();
				textAreaBinary3.setText(subnetMaskBinaryText);
			}
		});
		
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textArea1.setText("");
				textArea2.setText("");
				textArea3.setText("");
				textArea4.setText("");
				textArea5.setText("");
				textAreaBinary1.setText("");
				textAreaBinary2.setText("");
				textAreaBinary3.setText("");
			}
		});
		
		textArea1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textArea2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textArea3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textArea4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textArea5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textAreaBinary1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textAreaBinary2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textAreaBinary3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//makes all textAreas not available for user's input 
		textArea1.setEditable(false);
		textArea2.setEditable(false);
		textArea3.setEditable(false);
		textArea4.setEditable(false);
		textArea5.setEditable(false);
		textAreaBinary1.setEditable(false);
		textAreaBinary2.setEditable(false);
		textAreaBinary3.setEditable(false);
		
		
		panel.setLayout(new MigLayout());
		panel.add(toolBar, "wrap");
		panel.add(separatorLeft, "split 3, growx");
		panel.add(result);
		panel.add(separatorRight, "growx, wrap");
		panel.add(separator, "growx, wrap 15px");
		
		panel.add(networkAddress, "split 2, gapx 0px 95px");
		panel.add(textArea1, "wrap");
		panel.add(broadcastAddress, "split2, gapx 0px 84px");
		panel.add(textArea2, "wrap");
		panel.add(hosts, "split2, gapx 0px 150px");
		panel.add(textArea3, "wrap");
		panel.add(subnetMask, "split2, gapx 0px 119px");
		panel.add(textArea4, "wrap");
		panel.add(subnetBits, "split2, gapx 0px 128px");
		panel.add(textArea5, "wrap 15px");
		
		panel.add(lastSeparator1, "growx, split 3");
		panel.add(binarySection);
		panel.add(lastSeparator2, "growx, wrap");
		panel.add(lastSeparator3, "growx, wrap 15px");
		
		panel.add(networkBinaryAddress, "align center, wrap");
		panel.add(textAreaBinary1, "align center, wrap");
		panel.add(broadcastbinaryAddress, "align center, wrap");
		panel.add(textAreaBinary2, "align center, wrap");
		panel.add(subnetMaskBinary, "align center, wrap");
		panel.add(textAreaBinary3, "align center, wrap");
		
		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SubnetCalculatorInterface();
			}
		});
	}
}
