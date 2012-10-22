package dsa.mus.lib;

import java.io.Serializable;

public abstract class Card implements Serializable {
	
	/**
	 * UID version del dia 22/10/2012
	 */
	private static final long serialVersionUID = 22102012L;
	
	private int id;
	
	public Card(int id) {
		super();
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}
	public abstract String getSuit();	
	public abstract String getNumber();

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNumber() + getSuit();
	}
}