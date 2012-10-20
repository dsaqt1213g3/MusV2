<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
body
{ 
background: url('/MusV2/mus1.jpg') no-repeat fixed center; 
background-size:800px 600px;
background-position: 0px 0px;
}
</style>	

<%  String mensBienvenida;
	Boolean autenticacionOK = (Boolean) request.getAttribute("autenticacionOK");
	if(autenticacionOK)
		mensBienvenida = "Bienvenido " + (String) request.getAttribute("nom");
	else
	{
		mensBienvenida = "Autenticacion incorrecta";
	}
%>

<title><%= mensBienvenida %> </title>
</head>
<body>

<%
	if(autenticacionOK)
		out.println ("<h1> hola de nuevo "+ (String) request.getAttribute("nom") + "</h1>");
	else
		out.println ("<h1> No has sido autenticado correctamente.</h1>");
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