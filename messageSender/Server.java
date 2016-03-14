package messageSender;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame 
{
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	//constructor
	public Server()
	{
		super("Artur's Message Sender");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						sendMessage(event.getActionCommand());
						userText.setText("");
					}
				}
		);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(400, 200);
		setVisible(true);
	}
	
	//set up and run the server
	public void startRunning()
	{
		try
		{
			server = new ServerSocket(6789, 100);
			while(true)
			{
				try
				{
					//connect and have conversation
					waitForConnection();
					setupStreams();
					whileChatting();
					
				} catch(EOFException eofException)
				{
					showMessage("\n Server ended the connection! ");
				} finally
				{
					closeConnection();
				}
			}
		} catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
	}
	
	//wait for connection, then display connection information
	private void waitForConnection() throws IOException
	{
		showMessage(" Waiting for someone to connect... \n");
		connection = server.accept();
		showMessage(" Now connected to " + connection.getInetAddress().getHostName());
	}
	
	//get stream to send and receive data
	private void setupStreams() throws IOException
	{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush(); //when data left over, flush it out of the stream
		input = new ObjectInputStream(connection.getInputStream());
		showMessage(" \n You've got a stream, buddy! \n");
	}
	
	//during the chat conversation
	private void whileChatting() throws IOException
	{
		String message = " You are connected, dude! ";
		sendMessage(message);
		ableToType(true);
		
		do
		{
			//have a conversation
			try
			{
				message = (String) input.readObject();
				showMessage("\n"+message);
			} catch(ClassNotFoundException classNotFoundException)
			{
				showMessage(" \n wtf what user sent! ");
			}
		} while(!message.equals("CLIENT - END"));
	}
	
	//close streams and sockets after you are done chatting
	private void closeConnection()
	{
		showMessage(" \n Closing connection... \n");
		ableToType(false);
		try
		{
			output.close();
			input.close();
			connection.close();
		} catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
	}
	
	//send a message to client
	private void sendMessage(String message)
	{
		try
		{
			output.writeObject("SERVER - " + message);
			output.flush();
			showMessage("\nSERVER - " + message);
		} catch(IOException ioException)
		{
			chatWindow.append("\n Error: BRO I JUST CAN'T SEND MESSAGE");
		}
	}
	
	//updates chatWindow
	private void showMessage(final String text)
	{
		SwingUtilities.invokeLater( 	//thread updates the part of the GUI
				new Runnable() 	//creates a thread
				{
					public void run()
					{
						chatWindow.append(text);
					}
				}
		);
	}
	
	//let the user type stuff into their box
	private void ableToType(final boolean tof)
	{
		SwingUtilities.invokeLater( 
				new Runnable()
				{
					public void run()
					{
						userText.setEditable(tof);
					}
				}
		);
	}
}
