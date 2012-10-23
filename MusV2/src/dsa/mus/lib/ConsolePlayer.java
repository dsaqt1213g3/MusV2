package dsa.mus.lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsolePlayer extends Player {

	/**
	 * UID version del dia 22/10/2012
	 */
	private static final long serialVersionUID = 22102012L;
	
	int handLength;
	
	public ConsolePlayer(String name, int position) {
		super(name, position);
		handLength = 0;
	}

	@Override
	public boolean isMus() {
		boolean isMus = Console.yesNoQuestion(getName() + " tienes mus? (s/n)", 
				new BufferedReader (new InputStreamReader(System.in)));
		return isMus;
	}
	
	@Override
	public Card[] discard() {
		Card[] discards = new SpanishCard[4];
		Card[] newHand = new SpanishCard[4];
		
		int nextDiscard = 0;
		
		for(int i = 0; i < hand.length; i++)
		{
			if(Console.yesNoQuestion("Deseas descartar el " + hand[i].toString() + "? (s/n)", 
					new BufferedReader (new InputStreamReader(System.in))))
			{
				discards[nextDiscard++] = hand[i];
			}
			else
			{
				newHand[i - nextDiscard] = hand[i];
			}
		}
		
		Card[] cards = new SpanishCard[nextDiscard];
		System.arraycopy(discards, 0, cards, 0, cards.length);
		
		handLength = 4 - nextDiscard;
		System.arraycopy(newHand, 0, hand, 0, hand.length);
		
		return cards;
	}
	@Override
	public void clearHand() {
		handLength = 0;
	}
	@Override
	public void receive(Card[] cards) {
		System.arraycopy(cards, 0, hand, handLength, cards.length);
		handLength = handLength + cards.length;
	}

	@Override
	public void showHand() {
		System.out.print(getName() + ", tu mano es: ");
		for(int i = 0; i < handLength; i++)
			System.out.print(hand[i].toString() + " ");
		System.out.println();
	}

	@Override
	public void notifyWinner(boolean win) {
		if(win)
			System.out.println("Ha ganado " + getName());
		else
			System.out.println("No ha ganado " + getName());
	}
	
}
