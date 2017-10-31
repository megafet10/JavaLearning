import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MultiThreadServer {
	
	ServerSocket serverSocket = null;
	private static boolean bServerOn = true;
	
	public static void SutdownServer( boolean b) {
		bServerOn = !b;
		//System.out.println("Set sutdown point");
	}
	public static boolean isServerOn() {
		return bServerOn;
	}
	public MultiThreadServer () {
		//Create new listening socket
		try {
			serverSocket = new ServerSocket(1234);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(-1);
		}
		
		//Prepare data to send out to client
		Calendar now = Calendar.getInstance();
	    SimpleDateFormat formatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
	    System.out.println("It is now : " + formatter.format(now.getTime()));
	    
	    //Run the Server
	    while (bServerOn) {
	    	//Create a new Thread to handle communication with Client
	    	try {
	    		//System.out.println("While Server ON");
	    		Socket clientSocket = serverSocket.accept();
	    		//TODO Sometime another Client may Shutdown already, but main thread still be blocked in accept()
	    		//Then we have to check bServerOn again...
	    		ClientServiceThread clientThread = new ClientServiceThread(clientSocket);
	    		clientThread.start();
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    //When something make Server Stop, then we Stop it
	    try {
	    	serverSocket.close();
	    	System.out.println("Stopped Server");
	    } catch (Exception e) {
	    	e.printStackTrace();
    		System.exit(-1);
	    }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MultiThreadServer();
	}

}

//Thread to serve connection from Client
class ClientServiceThread extends Thread {
	Socket clientSocket;
	boolean bRunThread = true;
	public ClientServiceThread () {
		super();
	}
	
	public ClientServiceThread (Socket s) {
		clientSocket = s;
	}
	
	public void run () {
		BufferedReader in = null;
		PrintWriter out = null;
		String clientCommand;
		//System.out.println("Accepted client: " + clientSocket.getInetAddress().getHostName());
		System.out.println("Accepted client: " + clientSocket.getRemoteSocketAddress());
		
		try {
			//Assign ClientSocket Input/Output Stream to Reader/Writer of Thread
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			//Exchange message with Client
			while (bRunThread) {        
				//Get command from Client
				clientCommand = in.readLine();
	            System.out.println("Thread "+ this.getName() + " received: "+ clientCommand);
				
				//Set End point if another Client Stopped Server already
	            //Else reply the received message
	            if (! MultiThreadServer.isServerOn()) {
	            	// System.out.println("Server already Stopped");
	            	out.println("Server already Stopped"); 
	            	out.println("end");
	            	 bRunThread = false;
	            	 out.flush();
	            	 break;
	            }
	                       
	            if (clientCommand.equalsIgnoreCase("quit")) {
	            	bRunThread = false;
	            	out.println("end");
	            	out.flush();
	            	System.out.println("Thread "+ this.getName() + " stopping Client serving thread");
	            }
	            else if (clientCommand.equalsIgnoreCase("end")) {
	            	bRunThread = false;
	            	out.println("end");
	            	out.flush();
	            	MultiThreadServer.SutdownServer (true);
	            } else {
	            	out.println("Thread "+ this.getName() + " received: " + clientCommand);
	            	//out.flush();
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				clientSocket.close();
	             System.out.println("...Stopped"); 

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}