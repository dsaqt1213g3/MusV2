package dsa.mus.consola;

public class MusEngine {
	private ConsolePlayer[] player;
	private int turn, hand;
	private MusDeck deck;

	public MusEngine(ConsolePlayer[] player) {
		super();
		turn = hand = -1;
		this.player = player;
	}
	
	public void musStart(){
		
		while(true)
		{
			hand = (hand + 1) % 4;
			boolean discardFase = true;
			deck = new MusDeck();
			deck.shuffle();
			
			for(int i = 0; i < player.length; i++) {
				player[i].clearHand();
				player[i].receive(deck.deal(4));
			}
			
			while(discardFase){

				turn = hand;
				player[turn % player.length].showHand();
				while(player[turn++ % player.length].isMus() && turn < player.length + hand)	{
					player[turn % player.length].showHand();
				}
		
				if(!(turn < player.length + hand)) 
					for(int i = hand; i < player.length + hand; i++) {
						System.out.println("Descarte de " + player[i%player.length].getName());
						player[i%player.length].receive(deck.discard(player[i%player.length].discard()));	
					}
				else
					discardFase = false;
			}
		
			System.out.println("Fin de la ronda");
		}
	}
	
}
