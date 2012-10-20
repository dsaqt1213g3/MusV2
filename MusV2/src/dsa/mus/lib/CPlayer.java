package dsa.mus.lib;
import java.io.Serializable;

public class CPlayer implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 20121007L;

	String name;
	String password;
	int age;
	public CPlayer(String name, String password, int age) {
		super();
		this.name = name;
		this.password = password;
		this.age = age;
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "player.name: " + this.name + " player.password: " + this.password + " player.age: " + this.age;
	}
	
	
}
