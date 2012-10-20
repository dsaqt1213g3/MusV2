package dsa.mus.consola;

public class App 
{	
	
    public static void main( String[] args )
    {
    	MusEngine musGame = new MusEngine( new ConsolePlayer[]{
    		new ConsolePlayer("Fernando", 0),
    		new ConsolePlayer("Irene", 1),
    		new ConsolePlayer("Fede", 2),
    		new ConsolePlayer("Toni", 3)
    	});
    	
    	musGame.musStart();
     	
   }
}
