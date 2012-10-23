package dsa.mus.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	
	public final static String httpServer = "http://localhost:8080/MusV2/ObtenerNombre";
	
	private List<AttendClientThread> clientWithoutMach = new ArrayList<AttendClientThread>();
	int port;
	
	public Server(int port)
	{
		this.port = port;
	}
	
	public void run() throws IOException
	{
		ServerSocket myService = null;	
		Socket serviceSocket = null;
		
	    try {
	    	System.out.println("Creando socket del servidor.");
	    	myService = new ServerSocket(port);
	    	
	    	System.out.println("Esperando conexion.");			
        }
        catch (IOException e) {
        	System.out.println(e);
        	return;
        }
					
		try {
			int n = 0;
			while(true)
		    {	    	
				serviceSocket = myService.accept();
				  	
		    	System.out.println("Usuario conectedo con direccion ip: " + serviceSocket.getLocalAddress().toString()); 
		    	
		    	new AttendClientThread(n++, serviceSocket, this).start();
		    }	
		}
        catch (IOException e) {
        	System.out.println("Error " + e);
        	myService.close();
        	return;
        }   		
	}
	
	public List<AttendClientThread> getClientWithoutMach()
	{
		return clientWithoutMach;
	}
	
	public static void main(String[] args) 
	{
	    try {
			new Server(4009).run();
		} catch (IOException e) {
			e.printStackTrace();
		}	 	
	}
	
}
