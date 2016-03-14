package clientMessageSender;

import javax.swing.JFrame;

public class ClientTest 
{
	public static void main(String[] args)
	{
		Client josh;
		josh = new Client("127.0.0.1");
		josh.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		josh.startRunning();
	}
}
