package dsa.mus.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import dsa.mus.lib.CMessage;
import dsa.mus.lib.TypeMessage;
import dsa.mus.lib.SocketPlayer;

public class AttendClientThread extends Thread {

	int id;
	Socket s;
	ObjectInputStream sIn;
	ObjectOutputStream sOut;
	SocketPlayer player;
	Server server;

	/**
	 * @param s
	 * @param sIn
	 * @param sOut
	 * @param mySQL
	 */
	public AttendClientThread(int id, Socket s, Server server) {
		super();
		this.s = s;
    	try {
			this.sIn = new ObjectInputStream( s.getInputStream() );
			this.sOut = new ObjectOutputStream( s.getOutputStream() ); 
		} catch (IOException e) {
			e.printStackTrace();
		}    	 
		this.server = server;
	}
	
	private void createMach()
	{
		AttendClientThread[] aCT = server.getClientWithoutMach().toArray(
				new AttendClientThread[server.getClientWithoutMach().size()]);
				
		for( int i = 0; i < 4; i++)
			try {									
				server.getClientWithoutMach().remove(aCT[i]);

				aCT[i].sOut.writeObject((Object)new CMessage(TypeMessage.START_GAME));
			
			} catch (IOException e) {
				
				System.out.println("Error enviando datos en el servidor: " + e.getMessage());
			}
		
		AttendMachThread musMach = new AttendMachThread(new SocketPlayer[]{
				aCT[0].player, aCT[1].player, aCT[2].player, aCT[3].player
			});
						
		musMach.start(); 
	}
	
	private void attendClient() throws ClassNotFoundException, IOException
	{
		boolean clientConnected = true;
		while(clientConnected)
		{
			CMessage message;
			try {
				message = (CMessage)sIn.readObject();
			} catch( IOException e){
				System.out.println("Conexion perdida con " + player.getName());
				clientConnected = false; 
				continue;
			}
			switch (message.getCode()) {
			case IS_MUS:
				this.player.addMessage(message.getBool());
				synchronized (player) {	player.notify(); }				
				break;
			
			case DISCARD:
				this.player.addMessage(message.getCards());
				synchronized (player) { player.notify(); }
				break;

			case FINISH_GAME:
				if(message.getBool())
				{	
					server.getClientWithoutMach().add(this);
					synchronized (this) {
						if(server.getClientWithoutMach().size() >= 4)
							createMach();
					}
				}
				else
					clientConnected = false;
				break;
			
			default:
				clientConnected = false;
				break;
			}
		}	
		System.out.println("Desconectado a " + this.player.getName() + " con el thread " + this.getName());
	}

	public void run()
	{   
		String name;
		try {
			name = (String) sIn.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		this.player = new SocketPlayer(name, 0, s, sIn, sOut);   	
		server.getClientWithoutMach().add(this);
		
		System.out.println("Se ha conectado " + this.player.getName());
		synchronized (this) {
			if(server.getClientWithoutMach().size() >= 4)
				createMach();
		}
		
		try {
			attendClient();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Cerrando thread " + player.getName());
	}
}