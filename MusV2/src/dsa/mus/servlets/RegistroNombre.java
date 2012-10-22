package dsa.mus.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class RegistroNombre
 */
@WebServlet("/RegistroNombre")
public class RegistroNombre extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connect = null;
	Statement statement = null;
	ResultSet resultSet = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistroNombre() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
    	// TODO Auto-generated method stub
    	super.init(config);
    	
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		connect = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "bulkan33");
    		statement = connect.createStatement();
    		statement.execute("use client");
    		//statement.execute("create table clientes ( nombre VARCHAR(20), password VARCHAR (10));");
    	}catch (Exception e) 
    	{
    		System.out.println ("Error base de datos " + e);
    	} 		
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String nombre1=null;
		String password1=null;

		String nombre = (String) request.getParameter("nombre");
		System.out.println ("he recibido este nombre: "+ nombre);
		String password = (String) request.getParameter("password");
		System.out.println ("he recibido este password: "+ password);
		
		
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();

		try {
			statement.close();
			connect.close();
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

