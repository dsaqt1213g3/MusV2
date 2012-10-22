
package dsa.mus.lib;

import java.io.Serializable;

public class CMessage implements Serializable
{
	
	/**
	 * UID version del dia 22/10/2012
	 */
	private static final long serialVersionUID = 22102012L;
	
	TypeMessage code;
	Player player;
	Card[] cards;
	boolean bool;
	
	public CMessage(int code, Player player) {
		super();
		this.code = TypeMessage.withCode(code);
		this.player = player;
		this.cards = null;
		this.bool = false;
	}
	public CMessage(TypeMessage code, Card[] cards) {
		super();
		this.code = code;
		this.cards = cards;
		this.player = null;
		this.bool = false;
	}
	public CMessage(TypeMessage code) {
		super();
		this.code = code;
		this.player = null;
		this.cards = null;
		this.bool = false;
	}
	public CMessage(TypeMessage code, boolean bool) {
		super();
		this.code = code;
		this.player = null;
		this.cards = null;
		this.bool = bool;
	}
	public static CMessage Error()
	{
		return new CMessage(TypeMessage.ERROR);
	}
	
	public TypeMessage getCode() {
		return code;
	}
	public Player getPlayer() {
		return player;
	}	
	public Card[] getCards() {
		return cards;
	}
	public boolean getBool() {
		return bool;
	}
	@Override
	public String toString() {
		
		return "message.code: " + code + " " + player.toString();
	}
	
}