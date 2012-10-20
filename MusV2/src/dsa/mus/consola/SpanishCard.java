package dsa.mus.consola;

public class SpanishCard extends Card {

	private final static String[] SUITS = {"0","C","E","B"};
	private final static String[] NUMBERS = {"1","2","3","4","5","6","7","10","11","12"};
	
	public SpanishCard(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSuit() {
		// TODO Auto-generated method stub
		return SUITS[getId()/10];
	}

	@Override
	public String getNumber() {
		// TODO Auto-generated method stub
		return NUMBERS[getId()%10];
	}
}