package dsa.mus.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import dsa.mus.lib.MySQL;


public class Server {
	
	private static List<AttendClientThread> attendClient = new ArrayList<AttendClientThread>();
	
	public static List<AttendClientThread> getServerThreads()
	{
		return attendClient;
	}
	
	public static void runServer(ServerSocket MyService, MySQL mySQL)
	{
		Socket serviceSocket = null;	
		
		try {
			int n = 0;
			while(true)
		    {	    	
				serviceSocket = MyService.accept();
				  	
		    	System.out.println("Usuario conectedo."+ serviceSocket.getLocalAddress().toString()); 
		    	
		    	new AttendClientThread(n++, serviceSocket, mySQL).start();		    	
		    }	
		}
        catch (IOException e) {
        	System.out.println("Error " + e);
        	return;
        }   
		
	}
	
	public static void main(String[] args) 
	{
		ServerSocket MyService = null;		
		MySQL mySQL = new MySQL();
		
	    try {
	    	System.out.println("Creando socket del servidor.");
	    	MyService = new ServerSocket(4009);
	    	
	    	System.out.println("Esperando conexion.");
		    
			
        }
        catch (IOException e) {
        	System.out.println(e);
        	return;
        }
	    
	    runServer(MyService, mySQL);
	 	
	}
	
}
