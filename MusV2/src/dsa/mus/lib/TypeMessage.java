package dsa.mus.lib;

import java.io.Serializable;

public enum TypeMessage implements Serializable{
	IS_MUS (0), 
	WINNER (1),
	DISCARD (2),
	START_GAME (3),
	SHOW_HAND (4),
	RECEIVE (5),
	FINISH_GAME (6),

	DISCONNECT (254),
	ERROR (255);
	
	private final int code;
	
	/**
	 * @param code
	 */
	private TypeMessage(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static TypeMessage withCode(int code)
	{
		for(TypeMessage m : TypeMessage.values())
		{
			if (code == m.getCode()) return m;
		}
		return null;
	}
}
