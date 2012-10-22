package dsa.mus.lib;

import java.io.Serializable;


public abstract class Player implements Serializable{
	
	/**
	 * UID version del dia 22/10/2012
	 */
	private static final long serialVersionUID = 22102012L;
	
	private String name;
	private int position;
	protected Card[] hand;
	
	public Player(String name, int position) {
		super();
		this.name = name;
		this.position = position;
		this.hand = new Card[4];		
	}
	
	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}

	public abstract boolean isMus();
	public abstract Card[] discard();
	public abstract void receive(Card[] cards);
	public abstract void clearHand();
	public abstract void showHand();
	public abstract void notifyWinner(boolean win);
	
}
