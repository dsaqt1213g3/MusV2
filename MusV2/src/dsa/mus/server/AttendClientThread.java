package dsa.mus.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import dsa.mus.lib.CPlayer;
import dsa.mus.lib.MySQL;

public class AttendClientThread extends Thread {

	int id;
	Socket s;
	ObjectInputStream sIn;
	ObjectOutputStream sOut;
	MySQL mySQL;
	CPlayer player;

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
    		
    		this.player = new CPlayer(name, "");
   	
			Server.getServerThreads().add(this);
    		    		
    		if(Server.getServerThreads().size() >= 4)
    		{	
    			int ganador = (int)(new Random().nextInt())%2 + 1;
    			AttendClientThread[] aCT = Server.getServerThreads().toArray(new AttendClientThread[Server.getServerThreads().size()]);
    			for( int i = 0; i < aCT.length; i++)
					try {
						if((i == ganador)||(i == ganador+2))
							
						aCT[i].sOut.writeObject((i == ganador)||(i == ganador+2));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			Server.getServerThreads().remove(this);
    		
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
