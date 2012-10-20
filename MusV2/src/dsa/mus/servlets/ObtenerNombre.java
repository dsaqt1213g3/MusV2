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
 * Servlet implementation class ObtenerNombre
 */
@WebServlet("/ObtenerNombre")
public class ObtenerNombre extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connect = null;
	Statement statement = null;
	ResultSet resultSet = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerNombre() {
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
		
		try {
			
			resultSet = statement.executeQuery("select nombre,password from clientes where nombre='"+nombre+"';");
			
//  and password= '"+password+"' 
	
	
			System.out.println("Contenido de la base de datos");
		
				while (resultSet.next()) 
			{
				
				nombre1 = resultSet.getString("nombre");
				password1 = resultSet.getString("password");
				
				System.out.println("Nombre: " + nombre1 + " Password: " + password1);

			}
			
			 }catch (Exception e) 
			 {
				 System.out.println ("Error base de datos " + e);
			 } 
		

		if (password1 == null){
		
			PrintWriter out = response.getWriter();
			out.println ("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd>");

			out.println ("<html>");
			out.println ("<head>");
			out.println ("<meta http-equiv=\"Content-Type\" content=\"text/html;  charset-ISO-8859-1\">");
			out.println ("<title>Insert title here</title>");
			out.println ("</head>");
			out.println ("<body>");
			out.println ("<h1> este usuario no existe </h1>");
			Calendar calendario = new GregorianCalendar ();
			int hora = calendario.get(Calendar.HOUR_OF_DAY);
			int minute = calendario.get(Calendar.MINUTE);
			out.println ("<h2> son las " + hora+":"+minute+ " </h2>");
			out.println ("</body>");
			out.println ("</html>");
			System.out.println ("Fin");
		
		
		}
		else if (password1.equals(password)) {
		
				PrintWriter out = response.getWriter();
				out.println ("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd>");
			
				out.println ("<html>");
				out.println ("<head>");
				out.println ("<meta http-equiv=\"Content-Type\" content=\"text/html;  charset-ISO-8859-1\">");
				out.println ("<title>Insert title here</title>");
				out.println ("</head>");
				out.println ("<body>");
				out.println ("<h1> hola de nuevo "+ nombre  + "</h1>");
				out.println ("<h1> tu password es "+ password  + "</h1>");
				Calendar calendario = new GregorianCalendar ();
				int hora = calendario.get(Calendar.HOUR_OF_DAY);
				int minute = calendario.get(Calendar.MINUTE);
				out.println ("<h2> son las " + hora+":"+minute+ " </h2>");
				out.println ("</body>");
				out.println ("</html>");
				System.out.println ("Fin");
			
			}
			
			else{
				PrintWriter out = response.getWriter();
				out.println ("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd>");
	
				out.println ("<html>");
				out.println ("<head>");
				out.println ("<meta http-equiv=\"Content-Type\" content=\"text/html;  charset-ISO-8859-1\">");
				out.println ("<title>Insert title here</title>");
				out.println ("</head>");
				out.println ("<body>");
				out.println ("<h1> hola  "+ nombre  + ", su password no es correcte </h1>");
				Calendar calendario = new GregorianCalendar ();
				int hora = calendario.get(Calendar.HOUR_OF_DAY);
				int minute = calendario.get(Calendar.MINUTE);
				out.println ("<h2> son las " + hora+":"+minute+ " </h2>");
				out.println ("</body>");
				out.println ("</html>");
				System.out.println ("Fin");
		
			}
		
		
		
	

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
			connect.close();
			statement.close();
			resultSet.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

