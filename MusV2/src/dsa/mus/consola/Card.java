package dsa.mus.consola;

public abstract class Card {
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