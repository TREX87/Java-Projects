package messageSender;

import javax.swing.JFrame;

public class ServerTest
{
	public static void main(String[] args)
	{
		Server artur = new Server();
		artur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		artur.startRunning();
	}
}