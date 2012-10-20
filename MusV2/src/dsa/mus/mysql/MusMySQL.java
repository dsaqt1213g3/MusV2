package dsa.mus.mysql;

import dsa.mus.lib.MySQL;

public class MusMySQL {

	private final static String sqlFile = "mus_database.sql";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MySQL mysql = new MySQL();
		mysql.executeFile(sqlFile);
		System.out.println("Base de datos reiniciada.");
	}

}
