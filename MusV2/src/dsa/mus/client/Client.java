package dsa.mus.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import dsa.mus.lib.CPlayer;

public class Client {
	
	public final static String httpServer = "http://localhost:8080/MusV2/ObtenerNombre";
	
	private static void clientOperations(ObjectOutputStream sOut, ObjectInputStream sIn, BufferedReader bf) throws IOException, ClassNotFoundException
	{

    	
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
	
	private static void clientConection(ObjectInputStream sIn, ObjectOutputStream sOut, BufferedReader bf) throws IOException  
	{	
    	try {
			clientOperations(sOut, sIn, bf);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());	        
	        return;
		}	
	}
	
	private static boolean autenticacionHTTP(String name, String pass) throws IOException
	{
		HttpClient httpclient = new DefaultHttpClient();		
		HttpPost httppost = new HttpPost(httpServer);
		
		Gson g = new Gson(); 		 
		String json = g.toJson(new CPlayer(name, pass));
		System.out.println(json);
		
		StringEntity stringEntity = new StringEntity(json);
		stringEntity.setContentType("application/json");
		httppost.setEntity(stringEntity);
		
		System.out.println("executing request " + httppost.getRequestLine());
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();
					
		return false;
	}

	public static void main(String[] args) throws IOException
	{
		Socket MyClient = null;
		ObjectInputStream sIn = null;
		ObjectOutputStream sOut = null;
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf = new BufferedReader (isr);
		
		String name = "", pass = "";
        int age = 0; 	
    		
        /* Introduccion de datos  */
		System.out.print("Introduce el nombre: ");
    	name = bf.readLine();
    	System.out.print("Introduce la contraseña: ");
    	pass = bf.readLine();
		
		if(!autenticacionHTTP(name, pass)) return;
		
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
		
		clientConection(sIn, sOut, bf);
        
		isr.close();
		bf.close();
        MyClient.close();
        sIn.close();
        sOut.close();				
	}
}