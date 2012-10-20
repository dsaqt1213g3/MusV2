package dsa.mus.consola;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsolePlayer extends Player {
	
	int handLength;
	
	public ConsolePlayer(String name, int position) {
		super(name, position);
		handLength = 0;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMus() {
		System.out.println(getName() + " tienes mus? (s/n)");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf = new BufferedReader (isr);
		String mus = "s";
		try {
			 mus = bf.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if((mus.equals("s") || mus.equals("S")))
			return true;
		else if((mus.equals("n") || mus.equals("N")))
			return false;
		else 
		{
			System.out.println("Respuesta incorrecta.");
			return isMus();
		}
	}
	@Override
	public Card[] discard() {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader bf = new BufferedReader (isr);
		Card[] discards = new SpanishCard[4];
		Card[] newHand = new SpanishCard[4];
		
		String line = "";
		int nextDiscard = 0;
		
		for(int i = 0; i < hand.length; i++)
		{
			System.out.println("Deseas descartar el " + hand[i].toString() + "? (s/n)");
			try {
				 line = bf.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if((line.equals("s") || line.equals("S")))
				discards[nextDiscard++] = hand[i];
			else if((line.equals("n") || line.equals("N"))) 
				newHand[i - nextDiscard] = hand[i];
			else {
				System.out.println("Respuesta incorrecta.");
				i--;
			}
		}
		
		Card[] cards = new SpanishCard[nextDiscard];
		System.arraycopy(discards, 0, cards, 0, cards.length);
		
		handLength = 4 - nextDiscard;
		System.arraycopy(newHand, 0, hand, 0, hand.length);
		
		return cards;
	}
	public void clearHand() {
		handLength = 0;
	}
	@Override
	public void receive(Card[] cards) {
		System.arraycopy(cards, 0, hand, handLength, cards.length);
		handLength = handLength + cards.length;
	}

	public void showHand() {
		System.out.print(getName() + ", tu mano es: ");
		for(int i = 0; i < handLength; i++)
			System.out.print(hand[i].toString() + " ");
		System.out.println();
	}
	
}
