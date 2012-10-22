package dsa.mus.lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class SocketPlayer extends Player {

	
	/**
	 * UID version del dia 22/10/2012
	 */
	private static final long serialVersionUID = 22102012L;
	
	int handLength;
	Socket s;
	ObjectInputStream sIn;
	ObjectOutputStream sOut;
	Queue<Object> pendingMessage = new LinkedList<Object>();
	boolean isWaiting;
	
	public SocketPlayer(String name, int position, Socket s)
	{
		super(name, position);
		handLength = 0;
		this.s = s;
		this.isWaiting = false;
		try {
			sIn = new ObjectInputStream(s.getInputStream());
			sOut = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public SocketPlayer(String name, int position, Socket s, ObjectInputStream sIn, 
			ObjectOutputStream sOut)
	{
		super(name, position);
		handLength = 0;
		this.s = s;
		this.sIn = sIn;
		this.sOut = sOut;
		this.isWaiting = false;
	}

	private Object getMessage()
	{
		if(this.pendingMessage.isEmpty())
			try {
				isWaiting = true;
				synchronized (this) {
					wait();
				}					
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		return pendingMessage.poll();
	}
	
	@Override
	public boolean isMus()
	{
		try {
			sOut.writeObject((Object)new CMessage(TypeMessage.IS_MUS));			
			return (Boolean)getMessage();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public Card[] discard() 
	{
		try {
			sOut.writeObject(new CMessage(TypeMessage.DISCARD));
			Card[] discards = (Card[])getMessage();
			Card[] newHand = new Card[4];
			
			handLength = 4 - discards.length;

			for(int i = 0; i < discards.length; i++)
				for(int j = 0; j < hand.length; j++)
					if(!discards[i].equals(hand[j])) {
						newHand[i] = hand[j];
						break;
					}
			
			System.arraycopy(newHand, 0, hand, 0, hand.length);
			return discards;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void receive(Card[] cards) 
	{
		try {
			sOut.writeObject(new CMessage(TypeMessage.RECEIVE, cards));
			System.arraycopy(cards, 0, hand, handLength, cards.length);
			handLength = handLength + cards.length;
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void clearHand() 
	{
		handLength = 0;
	}

	@Override
	public void showHand()
	{
		try {
			sOut.writeObject(new CMessage(TypeMessage.SHOW_HAND));			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void notifyWinner(boolean win) {
		try {
			sOut.writeObject(new CMessage(TypeMessage.WINNER, win));			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void addMessage(Object message)
	{
		this.pendingMessage.add(message);
	}	
	public boolean haveMessage()
	{
		return !pendingMessage.isEmpty();
	}
	public boolean isWaiting()
	{
		return isWaiting;
	}
	public void send(Object data) throws IOException
	{
		sOut.writeObject(data);
	}
}
