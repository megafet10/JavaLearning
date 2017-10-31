import java.net.*;
import java.io.*;

public class Client {
	public static void main (String args[] ) {
		String serverName = "localhost";
		int port = 1234; //Integer.parseInt(args[1]);
		BufferedReader in = null;
		PrintWriter out = null;
		BufferedReader inCommd = null;
		String sCommd, sMessage;
		Socket client = null;
		
		try {
			System.out.println ("Connecting to "+ serverName + " on port " + port);
			client = new Socket (serverName, port);
			System.out.println("Just connected to " + client.getRemoteSocketAddress() );
			
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			//out.println("Hello Server");
			
			inCommd = new BufferedReader(new InputStreamReader(System.in));
			
			/*//Test
			out.println("Hello Server");		//Send to Server
			System.out.println("Hello Server");
			sMessage = in.readLine();
			System.out.println("Server replies: " + sMessage);*/
			
			do {
				sCommd = inCommd.readLine();//Get from user input
				out.println(sCommd);		//Send to Server
				sMessage = in.readLine();
				System.out.println("Server replies: " + sMessage);
			} while (!sMessage.equals("end"));

		client.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in!=null) in.close();
				if (out!=null) out.close();
				if (inCommd != null) inCommd.close();
				if (client != null) client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
			
	}

}