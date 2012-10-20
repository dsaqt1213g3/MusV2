package dsa.mus.lib;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MySQL {

	Connection connect = null;
	Statement statement = null;
	ResultSet resultSet = null;
	
	private final static String passFile = "mysqlPass.txt";
	
	public MySQL() {
		super();		
		try {			
			// This will load the MySQL driver			
			Class.forName("com.mysql.jdbc.Driver").newInstance();			
			
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", getPass());	
			
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();			
			statement.execute("use mus");
		
		} catch (Exception e) {
			System.out.println ("Error base de datos: " + e.getMessage());
		} 
	}
	
	private String getPass()
	{
		BufferedReader fr;
		try {
			fr = new BufferedReader(new FileReader(passFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			return null;
		}
		String pass;
		try {
			pass = fr.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try { fr.close(); } 
			catch (IOException e1) { e1.printStackTrace(); }
			return null;
		}
		try {
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pass;
	}

	public Connection getConnect() {
		return connect;
	}
	public Statement getStatement() {
		return statement;
	}
	public ResultSet getResultSet() {
		return resultSet;
	}

	public int executeQuery(String command) {
		try {
			resultSet = statement.executeQuery(command);
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return -1;
		}
	}
	public int executeFile(String file)
	{
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(file), Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		String fullText = "";
		for(String line: lines)
		{
			fullText += line + "";
		}
		String[] commands = fullText.split(";");
		for( String command : commands)
			this.execute(command);
		return 0;
	}
	
	public int execute(String command) {
		try {
			statement.execute(command);
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return -1;
		}
	}
	public int getResultLength() {
		try {
			resultSet.last();
			int length = resultSet.getRow();
			resultSet.first();
			return length;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
	public int close()
	{
		try {
			connect.close();
			statement.close();
			resultSet.close();
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}	
}
