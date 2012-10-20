package dsa.mus.consola;

public abstract class Player {
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
	
}
