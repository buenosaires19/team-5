<%@page import="ar.org.centro8.curso.java.html.Html"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% out.println(Html.getHead()); %>
    <body>
        <h1>Menu de Alumnos</h1>
		<ul>
			<li><a href="alumnosAlta.jsp">Alta de alumnos</a></li>
			<li><a href="alumnosBaja.jsp">Baja de alumnos</a></li>
			<li><a href="alumnosModificaciones.jsp">Modificaciones de alumnos</a></li>
			<li><a href="alumnosListado.jsp">Listado de cursos</a></li>
			<li><a href="index.jsp">Menu Principal</a></li>
		</ul>
		<% out.println(Html.volver("index.jsp")); %>
    </body>
</html>
