package dsa.mus.lib;

import java.io.BufferedReader;
import java.io.IOException;

public class Console {
	
	public static String inputString(String message, BufferedReader bf)
	{
		System.out.print(message);
    	try {
			return bf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean yesNoQuestion(String message, BufferedReader bf)
	{
		System.out.println(message);
		String result = "s";
		try {
			 result = bf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if((result.equals("s") || result.equals("S")))
			return true;
		else if((result.equals("n") || result.equals("N")))
			return false;
		else 
		{
			System.out.println("Respuesta incorrecta.");
			return yesNoQuestion(message, bf);
		}
	}
}
