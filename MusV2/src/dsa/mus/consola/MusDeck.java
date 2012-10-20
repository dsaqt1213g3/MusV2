package dsa.mus.consola;

public class MusDeck {
	private Card[] cards;
	private Card[] discardPile;
	private int nextDeal;
	private int nextDiscard;
	
	public MusDeck() {
		super();
		cards = new Card[40];
		discardPile = new Card[24];
		nextDeal = 0;
		nextDiscard = 0;

		for(int i = 0; i < cards.length; i++)
		{
			cards[i] = new SpanishCard(i);
		}
		
	}
	
	private void exch(Card[] a, int i, int j) {
		Card swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	public void shuffle() {
		int N = cards.length;
		for(int i = 0; i < N; i++)
		{
			int r = i + (int)(Math.random()*(N-i));
			exch(cards,i,r);
		}
	}
	public Card deal() {
		if(nextDeal==cards.length){
			cards = new Card[discardPile.length];
			System.arraycopy(discardPile,0,cards,0,cards.length);
			shuffle();
			nextDeal=0;
			nextDiscard=0;
		}		
		Card c = cards[nextDeal];
		nextDeal++;	
		
		return	c;
	}
	public Card[] deal(int n){
		Card[] cards = new Card[n];
		for(int i = 0; i < n; i++)
			cards[i] = deal();
		return cards;
	}	
	public Card[] discard(Card[] cards) {
		// Descartamos las cartas
		System.arraycopy(cards, 0, discardPile, nextDiscard, cards.length);
		nextDiscard = nextDiscard + cards.length;
		
		//Repartir las nuevas cartas
		return deal(cards.length);		
	}
	
}
