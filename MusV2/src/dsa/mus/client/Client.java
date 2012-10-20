package dsa.mus.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import dsa.mus.lib.CPlayer;

public class Client {
	
	private static void clientOperations(ObjectOutputStream sOut, ObjectInputStream sIn, BufferedReader bf) throws IOException, ClassNotFoundException
	{
		String name = "", pass = "";
        int age = 0; 	
    		
        /* Introduccion de datos  */
		System.out.print("Introduce el nombre: ");
    	name = bf.readLine();
    	System.out.print("Introduce la contraseña: ");
    	pass = bf.readLine();
    	
		/* Enviando datos */ 
    	sOut.writeObject(new CPlayer(name, pass,age));
		
		/* Recepcion de datos del servidor */
    	if((boolean)sIn.readObject())
    		System.out.println("Validacion satisfactoria");
    	else 
    	{
    		System.out.println("Error en la validacion");
    		return;
    	}
    	
    	if((boolean)sIn.readObject())
    		System.out.println("has ganado.");
    	else
    		System.out.println("has perdido");
	}
	
	private static void clientConection(ObjectInputStream sIn, ObjectOutputStream sOut) throws IOException  
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf = new BufferedReader (isr);
		   	  
    	try {
			clientOperations(sOut, sIn, bf);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());	        
	        return;
		}
       
		isr.close();
		bf.close();
	}
	
	public static void main(String[] args) throws IOException
	{
		Socket MyClient = null;
		ObjectInputStream sIn = null;
		ObjectOutputStream sOut = null;
				
		try
		{
			System.out.println("Conectando con el servidor.");
			MyClient = new Socket(InetAddress.getLocalHost(), 4009);			
			
			sOut = new ObjectOutputStream( MyClient.getOutputStream() );
	        sIn = new ObjectInputStream( MyClient.getInputStream() );   
		}
	    catch (IOException e) 
	    {
	        System.out.println(e.getMessage());	        
	        System.exit(-1);
		}   
		
		clientConection(sIn, sOut);
        
        MyClient.close();
        sIn.close();
        sOut.close();				
	}
}