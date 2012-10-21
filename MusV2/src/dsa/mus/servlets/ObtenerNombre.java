package dsa.mus.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dsa.mus.lib.CPlayer;
import dsa.mus.lib.MySQL;


/**
 * Servlet implementation class ObtenerNombre
 */
@WebServlet("/ObtenerNombre")
public class ObtenerNombre extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MySQL mysql;
		
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
		mysql = new MySQL();	
	}
	
	private void autenticacion(HttpServletRequest request, HttpServletResponse response, String nombre, String password)
	{
		String nombre1=null;
		String password1=null;
		
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		
	
		System.out.println ("he recibido este nombre: "+ nombre);
		System.out.println ("he recibido este password: "+ password);
		
		try {
			
			mysql.executeQuery("select nombre,contrasena from jugador where nombre='"+nombre+"';");
			
			System.out.println("Contenido de la base de datos");
		
			while (mysql.getResultSet().next()) 
			{
				
				nombre1 = mysql.getResultSet().getString("nombre");
				password1 = mysql.getResultSet().getString("contrasena");
				
				System.out.println("Nombre: " + nombre1 + " Password: " + password1);

			}
			
		 }catch (Exception e) 
		 {
			 System.out.println ("Error base de datos " + e);
		 } 
		
		try {		
			
			if (password1 == null)
			{
				if(request.getContentType()==null)
				{		
					request.setAttribute("autenticacionOK", false);
					RequestDispatcher view = request.getRequestDispatcher("/acceso.jsp");
					try {
						view.forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				else
					out.write(0); // Autenticacion incorrecta
			}
			else if (password1.equals(password)) 
			{	
				if(request.getContentType()==null)
				{
					request.setAttribute("nom", nombre);
					request.setAttribute("autenticacionOK", true);
					RequestDispatcher view = request.getRequestDispatcher("/acceso.jsp");
					try {
						view.forward(request, response);
					} catch (ServletException e) {
						e.printStackTrace();
					}
				}	
				else
					out.write(1); // Autenticacion correcta
			}
			else
			{			
				if(request.getContentType()==null)
				{
					request.setAttribute("autenticacionOK", false);
					RequestDispatcher view = request.getRequestDispatcher("/acceso.jsp");
					try {
						view.forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
					out.write(0); // Autenticacion incorrecta
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void registro(HttpServletRequest request, HttpServletResponse response)
	{
		String nombre1=null;
		String password1=null;
		
		String nombre = (String) request.getParameter("nombre");
		System.out.println ("he recibido este nombre: "+ nombre);

		String password = (String) request.getParameter("password");
		System.out.println ("he recibido este password: "+ password);
		
		try {
		
			if (nombre.length()<6){
				PrintWriter out = response.getWriter();
				out.println ("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd>");
				out.println ("<html>");
				out.println ("<head>");
				out.println ("<meta http-equiv=\"Content-Type\" content=\"text/html;  charset-ISO-8859-1\">");
				out.println ("<title>Insert title here</title>");
				out.println ("</head>");
				out.println ("<body>");
				out.println ("<h1> el nombre debe ser de 6 digitos como minimo </h1>");
				Calendar calendario = new GregorianCalendar ();
				int hora = calendario.get(Calendar.HOUR_OF_DAY);
				int minute = calendario.get(Calendar.MINUTE);
				out.println ("<h2> son las " + hora+":"+minute+ " </h2>");
				out.println ("</body>");
				out.println ("</html>");
				System.out.println ("Fin");
			}
			else if (password.length()<6){
				PrintWriter out = response.getWriter();
				out.println ("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd>");
				out.println ("<html>");
				out.println ("<head>");
				out.println ("<meta http-equiv=\"Content-Type\" content=\"text/html;  charset-ISO-8859-1\">");
				out.println ("<title>Insert title here</title>");
				out.println ("</head>");
				out.println ("<body>");
				out.println ("<h1> el password debe ser de 6 digitos como minimo</h1>");
				Calendar calendario = new GregorianCalendar ();
				int hora = calendario.get(Calendar.HOUR_OF_DAY);
				int minute = calendario.get(Calendar.MINUTE);
				out.println ("<h2> son las " + hora+":"+minute+ " </h2>");
				out.println ("</body>");
				out.println ("</html>");
				System.out.println ("Fin");
			}
			else{
				try{
					mysql.executeQuery("select nombre from jugador where nombre='"+nombre+"'");
					while (mysql.getResultSet().next()) 
					{
						mysql.getResultSet().getString("nombre");				
					//	System.out.println("Nombre: " + nombre1 + " Password: " + password1);
					}			
					}catch (Exception e) 
					{
						System.out.println ("Error base de datos " + e);
					} 
					if (nombre1==null){
						try {
							mysql.execute("insert into jugador values ('"+nombre+"', '"+password+"');");
							mysql.executeQuery("select * from jugador");
							System.out.println("Contenido de la base de datos");
							while (mysql.getResultSet().next()) 
							{
								nombre1 = mysql.getResultSet().getString("nombre");
								password1 = mysql.getResultSet().getString("contrasena");
								
								System.out.println("Nombre: " + nombre1 + " Password: " + password1);
							}			
						}catch (Exception e) 
						{
							System.out.println ("Error base de datos " + e);
						} 
						
						
						PrintWriter out = response.getWriter();
						out.println ("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd>");
						out.println ("<html>");
						out.println ("<head>");
						out.println ("<meta http-equiv=\"Content-Type\" content=\"text/html;  charset-ISO-8859-1\">");
						out.println ("<title>Insert title here</title>");
						out.println ("</head>");
						out.println ("<body>");
						out.println ("<h1> hola  "+ nombre  + "</h1>");
						out.println ("<h1> tu password es "+ password  + "</h1>");
						Calendar calendario = new GregorianCalendar ();
						int hora = calendario.get(Calendar.HOUR_OF_DAY);
						int minute = calendario.get(Calendar.MINUTE);
						out.println ("<h2> son las " + hora+":"+minute+ " </h2>");
						out.println ("</body>");
						out.println ("</html>");
						System.out.println ("Fin");
						
					}
					else {
						PrintWriter out = response.getWriter();
						out.println ("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd>");
						out.println ("<html>");
						out.println ("<head>");
						out.println ("<meta http-equiv=\"Content-Type\" content=\"text/html;  charset-ISO-8859-1\">");
						out.println ("<title>Insert title here</title>");
						out.println ("</head>");
						out.println ("<body>");
						out.println ("<h1> hola, el nombre  "+ nombre  + " ya esta registrado</h1>");
						out.println ("<h1> regístrese con otro nombre de usuario </h1>");
						Calendar calendario = new GregorianCalendar ();
						int hora = calendario.get(Calendar.HOUR_OF_DAY);
						int minute = calendario.get(Calendar.MINUTE);
						out.println ("<h2> son las " + hora+":"+minute+ " </h2>");
						out.println ("</body>");
						out.println ("</html>");
						System.out.println ("Fin");
					
					}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String navegador = (String) request.getParameter("navegador");
		System.out.println(navegador);
		
		switch (navegador) {
		case "autenticacion":
				autenticacion(request, response, (String) request.getParameter("nombre"), (String) request.getParameter("password"));
			break;
		
		case "registro":
				registro(request, response);
			break;

		default:
			break;
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String navegador = (String) request.getParameter("name");
		System.out.println(navegador);
		String n = "";
		int num = request.getReader().read() ;
		while(num != -1)
		{
			n += (char)num;
			num = request.getReader().read();
		} 
		
		Gson g = new Gson(); 
		CPlayer player = g.fromJson(n, CPlayer.class);
		
		System.out.println(player.toString());
		
		autenticacion(request, response, player.getName(), player.getPassword());
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		mysql.close();
	}

}


