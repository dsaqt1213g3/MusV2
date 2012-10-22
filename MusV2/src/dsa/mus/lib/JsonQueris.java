package dsa.mus.lib;
import java.io.Serializable;

public class JsonQueris implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 20121007L;

	String name;
	String password;
	int age;
	
	String ganador1, ganador2, jugador3, jugador4;
	
	EJsonQueris type;
	
	public JsonQueris(String ganador1, String ganador2, String jugador3, String jugador4) {
		this.ganador1 = ganador1;
		this.ganador2 = ganador2;
		this.jugador3 = jugador3;
		this.jugador4 = jugador4;
		this.type = EJsonQueris.GUARDAR_PARTIDA;
	}
	
	public JsonQueris(String name, String password, int age) {
		super();
		this.name = name;
		this.password = password;
		this.age = age;
	}
	public JsonQueris(String name, String password) {
		super();
		this.name = name;
		this.password = password;
		this.type = EJsonQueris.ACCES;
	}

	
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public int getAge() {
		return age;
	}
	public String getGanador1() {
		return ganador1;
	}

	public String getGanador2() {
		return ganador2;
	}

	public String getJugador3() {
		return jugador3;
	}

	public String getJugador4() {
		return jugador4;
	}
	public EJsonQueris getType() {
		return type;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "player.name: " + this.name + " player.password: " + this.password + " player.age: " + this.age;
	}

	

	
	
}
