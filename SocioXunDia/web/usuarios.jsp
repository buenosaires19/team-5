<%@page import="java.util.List"%>
<%@page import="socio.repositories.GenericR"%>
<%@page import="socio.entities.Usuario"%>
<%@page import="socio.connectors.Connector"%>
<%@page import="socio.html.Html"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% out.println(Html.getHead()); %>
    <body>
        <h1>Listado de Usuarios</h1>
		<%
			GenericR<Usuario> us = new GenericR(Connector.getConnection(), Usuario.class);
			out.println("	<table>");

			for (Usuario item:us.getByFiltro("usuario")) {
					out.println("asdasd");
					out.println("					<tr>");
					out.println("						<td>" + item.getApellidousuario()+ "</td>");
					out.println("						<td>" + item.getNombreusuario()+ "</td>");
					out.println("						<td>" + item.getMailusuario()+ "</td>");
			};
		%>
	</body>
</html>
