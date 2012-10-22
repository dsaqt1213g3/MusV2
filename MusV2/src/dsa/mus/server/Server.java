package dsa.mus.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import dsa.mus.lib.MySQL;


public class Server {
	
	public final static String httpServer = "http://localhost:8080/MusV2/ObtenerNombre";
	
	private List<AttendClientThread> clientWithoutMach = new ArrayList<AttendClientThread>();
	
	public List<AttendClientThread> getClientWithoutMach()
	{
		return clientWithoutMach;
	}
	
	public void run(ServerSocket MyService, MySQL mySQL)
	{
		Socket serviceSocket = null;	
		
		try {
			int n = 0;
			while(true)
		    {	    	
				serviceSocket = MyService.accept();
				  	
		    	System.out.println("Usuario conectedo." + serviceSocket.getLocalAddress().toString()); 
		    	
		    	new AttendClientThread(n++, serviceSocket, mySQL, this).start();
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
	    
	    new Server().run(MyService, mySQL);
	 	
	}
	
}
