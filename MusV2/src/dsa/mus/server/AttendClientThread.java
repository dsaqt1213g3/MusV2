package dsa.mus.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import dsa.mus.lib.JsonQueris;
import dsa.mus.lib.MySQL;

public class AttendClientThread extends Thread {

	public final static String httpServer = "http://localhost:8080/MusV2/ObtenerNombre";
	
	int id;
	Socket s;
	ObjectInputStream sIn;
	ObjectOutputStream sOut;
	MySQL mySQL;
	JsonQueris player;

	/**
	 * @param s
	 * @param sIn
	 * @param sOut
	 * @param mySQL
	 */
	public AttendClientThread(int id, Socket s, MySQL mySQL) {
		super();
		this.s = s;
    	try {
			this.sIn = new ObjectInputStream( s.getInputStream() );
			this.sOut = new ObjectOutputStream( s.getOutputStream() ); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	 
		this.mySQL = mySQL;
	}

	private static void guardarPartida(String ganador1, String ganador2, String jugador3, String jugador4) throws IOException
	{
		HttpClient httpclient = new DefaultHttpClient();		
		HttpPost httppost = new HttpPost(httpServer);
		
		Gson g = new Gson(); 		 
		String json = g.toJson(new JsonQueris(ganador1, ganador2, jugador3, jugador4));
		
		StringEntity stringEntity = new StringEntity(json);
		stringEntity.setContentType("application/json");
		httppost.setEntity(stringEntity);
		
		httpclient.execute(httppost);
	}
	
	public void run()
	{
		while(true)
    	{    
			String name;
			try {
				name = (String) sIn.readObject();
			} catch (ClassNotFoundException | IOException e) {
				System.out.println(e.getMessage());
				return;
			}
    		
    		this.player = new JsonQueris(name, "");
   	
			Server.getServerThreads().add(this);
    		    		
    		if(Server.getServerThreads().size() >= 4)
    		{	
    			int ganador = (int)(new Random().nextInt())%2 + 1;
    			AttendClientThread[] aCT = Server.getServerThreads().toArray(new AttendClientThread[Server.getServerThreads().size()]);
    			for( int i = 0; i < aCT.length; i++)
					try {							
						aCT[i].sOut.writeObject((i == ganador)||(i == ganador+2));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			Server.getServerThreads().remove(this);
    			try {
					guardarPartida(
							aCT[ganador%4].player.getName(), aCT[(ganador + 2)%4].player.getName(), 
							aCT[(ganador + 1)%4].player.getName(), aCT[(ganador + 3)%4].player.getName()
					);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		else
    		{
    			try {
					sIn.readObject();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					Server.getServerThreads().remove(this);
					System.out.println("Cliente " + player.getName() + " desconectado");
				}
    		}
    	}   	
	}
}
