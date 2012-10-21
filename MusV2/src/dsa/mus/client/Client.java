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
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import dsa.mus.lib.CPlayer;

public class Client {
	
	public final static String httpServer = "http://localhost:8080/MusV2/ObtenerNombre";
	
	private static void clientOperations( ObjectInputStream sIn, ObjectOutputStream sOut, BufferedReader bf) throws IOException, ClassNotFoundException
	{
    	
    	if((boolean)sIn.readObject())
    		System.out.println("has ganado.");
    	else
    		System.out.println("has perdido");
	}
	
	private static boolean autenticacionHTTP(String name, String pass) throws IOException
	{
		HttpClient httpclient = new DefaultHttpClient();		
		HttpPost httppost = new HttpPost(httpServer);
		int respuesta = 0;
		
		Gson g = new Gson(); 		 
		String json = g.toJson(new CPlayer(name, pass));
		
		StringEntity stringEntity = new StringEntity(json);
		stringEntity.setContentType("application/json");
		httppost.setEntity(stringEntity);
		
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();
		
		if (resEntity != null) {
			respuesta = resEntity.getContent().read();
		}
		EntityUtils.consume(resEntity);
		
		if(respuesta == 1)
		{
			System.out.println("Validacion satisfactoria");
			return true;
		}
		else 
		{
			System.out.println("Validacion erronea");
			return false;
		}
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
		
		sOut.writeObject(name);
		
		try {
			clientOperations(sIn, sOut, bf);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		isr.close();
		bf.close();
        MyClient.close();
        sIn.close();
        sOut.close();				
	}
}