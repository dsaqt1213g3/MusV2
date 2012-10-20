package dsa.mus.consola;

public class CopyOfCard {
	private int id;
	private final static String[] SUITS = {"0","C","E","B"};
	private final static String[] NUMBERS = {"1","2","3","4","5","6","7","10","11","12"};
	
	public CopyOfCard(int id) {
		super();
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public String getSuit(){
		return SUITS[id/4];
	}
	
	public String getNumber(){
		return NUMBERS[id%10];
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNumber() + getSuit();
	}
}