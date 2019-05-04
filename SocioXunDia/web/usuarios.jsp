
<%@page import="java.util.ArrayList"%>
<%@page import="socio.html.TablaHTML"%>
<%@page import="java.util.List"%>
<%@page import="socio.connectors.ConnectorMySQL"%>
<%@page import="socio.html.Html"%>
<%@page import="socio.entities.Usuario"%>
<%@page import="socio.repositories.GenericR"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mantenimiento de Usuarios</title>
    </head>
    <body>
        <h1>Mantenimiento de Usuarios</h1>
		<%
			String id		= "";
			String name		= "";
			String mail	= "";
			String region = "";
			String nivel = "";
			String video = "";
		%>
		
		<form action="respuesta.jsp">
			<fieldset><legend>Datos de Usuario</legend>
			ID:		<input type=text value="<% out.print(id); %>" name="id"><br>
			Nombre:	<input type=text value="<% out.print(name); %>" name="nombre"><br>
			Email:	<input type=text value="<% out.print(mail); %>" name="mail"><br>
			Region:	<input type=text value="<% out.print(region); %>" name="region"><br>
			Nivel:	<input type=text value="<% out.print(nivel); %>" name="nivel"><br>
			Video:	<input type=text value="<% out.print(video); %>" name="video"><br>
			
			<input type=submit value="Agregar">
			</fieldset>
		</form >
		
		<%
			try {
				GenericR<Usuario> us = new GenericR(ConnectorMySQL.getConnection(), Usuario.class);
				us.save(new Usuario(
						request.getParameter("nombre"),
						request.getParameter("apellido"),
						request.getParameter("mail"),
						Integer.parseInt(request.getParameter("region")),
						Integer.parseInt(request.getParameter("nivel")),
						Integer.parseInt(request.getParameter("video")))
				);
				
				id		= "";
				name	= "";
				mail	= "";
				region = "";
				nivel = "";
				video = "";
			} catch (Exception e) { out.println("Error de parametros!");}
		%>
		
    </body>
</html>
