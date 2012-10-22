<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
body
{ 
background: url('/MusV2/mus1.jpg') no-repeat fixed center; 
background-size:800px 600px;
background-position: 0px 0px;
}
</style>	

<%  String mensBienvenida;
	Boolean nombreok = (Boolean) request.getAttribute("nombreok");
	Boolean contrasenaok = (Boolean) request.getAttribute("contrasenaok");
	Boolean usuariolibre = (Boolean) request.getAttribute("usuariolibre");
	if((usuariolibre) && (nombreok) && (contrasenaok))
		mensBienvenida = "Enhorabuena, se ha registrado correctamente" + (String) request.getAttribute("nom");
	else
	{
		mensBienvenida = "Error en el registro";
	}
%>


<title><%= mensBienvenida %> </title>
</head>
<body>

<%
	if ((nombreok) && (contrasenaok) && (usuariolibre))
		out.println ("<h1> Bienvenido a nuestra comunidad "+ (String) request.getAttribute("nom") + "</h1>");
	else
		{
		if(!nombreok)
			out.println ("<h1> El nombre debe ser de 6 dígitos como mínimo </h1>");


		else
			{
			if(!contrasenaok)
				out.println ("<h1> El password debe ser de 6 dígitos como mínimo </h1>");
						
			
			else
				{
				if(!usuariolibre)
				out.println ("<h1> El usuario que intenta dar de alta, ya está registrado. </h1>");
					
				}
			}
		}
		
		
	%>

<script type="text/javascript">
	
	//Auto ajust background image size
	document.body.style.backgroundSize= innerWidth + "px " + innerHeight + "px";
	window.onresize=resize;
	function resize()
	{
		document.body.style.backgroundSize= innerWidth + "px " + innerHeight + "px";
	}
</script>

</body>
</html>