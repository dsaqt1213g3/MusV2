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

import dsa.mus.lib.CMessage;
import dsa.mus.lib.Console;
import dsa.mus.lib.ConsolePlayer;
import dsa.mus.lib.JsonQueris;
import dsa.mus.lib.TypeMessage;
import dsa.mus.server.Server;

public class Client {
	
	int port;
	InetAddress addres;
	public Client(InetAddress addres, int port)
	{
		this.addres = addres;
		this.port = port;
	}
	
	private void clientOperations(ObjectInputStream sIn, ObjectOutputStream sOut,  BufferedReader bf, String name) throws ClassNotFoundException, IOException
	{
    	ConsolePlayer player = new ConsolePlayer(name, 0);
    	
    	System.out.println("Esperando partida.");
    	boolean fin = false;
    	while(!fin)
    	{
    		CMessage message = 	CMessage.Error();
			try {
				message = (CMessage)sIn.readObject();
			} catch (IOException e) {
				System.out.println("Error " + e.getMessage());
				return;
			}
    		switch (message.getCode()) {
    		case START_GAME:
    			System.out.println("Iniciando partida");
    			break;
    			
			case IS_MUS:				
				sOut.writeObject(new CMessage(TypeMessage.IS_MUS,player.isMus()));
				break;
				
			case DISCARD:
				sOut.writeObject(new CMessage(TypeMessage.DISCARD, player.discard()));
				break;
				
			case RECEIVE:
				player.receive(message.getCards());
				break;
				
			case WINNER:
				if(message.getBool())
					System.out.println("Has ganado.");
				else 
					System.out.println("Has perdido.");
				break;
				
			case SHOW_HAND:
				player.showHand();
				break;
				
			case  FINISH_GAME:
				sOut.writeObject(new CMessage(TypeMessage.FINISH_GAME, 
						Console.yesNoQuestion("Jugar otra partida? (s/n)", bf)));
				break;
				
			case DISCONNECT:
				System.out.println("Desconectado del servidor");
				
				fin = true;
				break;

			default:
				break;
			}
    	}
	}
	
	private boolean autenticacionHTTP(String name, String pass) throws IOException
	{
		HttpClient httpclient = new DefaultHttpClient();		
		HttpPost httppost = new HttpPost(Server.httpServer);
		int respuesta = 0;
		
		Gson g = new Gson(); 		 
		String json = g.toJson(new JsonQueris(name, pass));
		
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
	
	private void run()
	{
		Socket MyClient = null;
		ObjectInputStream sIn = null;
		ObjectOutputStream sOut = null;
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf = new BufferedReader (isr);
		
		String name = "", pass = "";
    		
        /* Introduccion de datos  */
		name = Console.inputString("Introduce el nombre: ", bf);
		pass = Console.inputString("Introduce la contraseña: ", bf);
		
		try {
			if(!autenticacionHTTP(name, pass)) return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try
		{	
			System.out.println("Conectando con el servidor.");
			MyClient = new Socket(addres, port);			
			
			sOut = new ObjectOutputStream( MyClient.getOutputStream() );
	        sIn = new ObjectInputStream( MyClient.getInputStream() );   
		}
	    catch (IOException e) 
	    {
	        System.out.println(e.getMessage());	        
	        System.exit(-1);
		}   
		
		try {
			sOut.writeObject(name);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			clientOperations(sIn, sOut, bf, name);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
        
		try {
			isr.close();
			bf.close();
	        MyClient.close();
	        sIn.close();
	        sOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException
	{
		Client cliente = new Client(InetAddress.getLocalHost(), 4009);
		cliente.run();
	}
}