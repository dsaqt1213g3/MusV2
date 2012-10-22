package dsa.mus.server;


import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import dsa.mus.lib.CMessage;
import dsa.mus.lib.JsonQueris;
import dsa.mus.lib.MusEngine;
import dsa.mus.lib.SocketPlayer;
import dsa.mus.lib.TypeMessage;

public class AttendMachThread extends Thread {

	MusEngine musEngine;
	SocketPlayer[] players;
	/**
	 * @param players
	 */
	public AttendMachThread(SocketPlayer[] players) {
		super();
		this.players = players;
		this.musEngine = new MusEngine(players);
	}
	
	private static void guardarPartida(String ganador1, String ganador2, String jugador3, String jugador4) throws IOException
	{
		HttpClient httpclient = new DefaultHttpClient();		
		HttpPost httppost = new HttpPost(Server.httpServer);
		
		Gson g = new Gson(); 		 
		String json = g.toJson(new JsonQueris(ganador1, ganador2, jugador3, jugador4));
		
		StringEntity stringEntity = new StringEntity(json);
		stringEntity.setContentType("application/json");
		httppost.setEntity(stringEntity);
		
		httpclient.execute(httppost);
	}

	private void finishMach(int winner)
	{
		/* Send player finish game */
		for(SocketPlayer player: players)
			try {
				player.send(new CMessage(TypeMessage.FINISH_GAME));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		/* Save mach in database */
		try {
			guardarPartida(
					players[winner%4].getName(), players[(winner + 2)%4].getName(), 
					players[(winner + 1)%4].getName(), players[(winner + 3)%4].getName()
			);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run()
	{ 
		finishMach(musEngine.musStart());
	}
	
}