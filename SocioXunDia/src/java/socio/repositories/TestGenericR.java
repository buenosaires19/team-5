package socio.repositories;

import socio.connectors.ConnectorMySQL;
import socio.entities.Nivel;
import socio.servlets.usuario;
//import socio.entities.Usuario;

public class TestGenericR {
    
	public static void main(String[] args) {
			GenericR<Nivel> nv = new GenericR(ConnectorMySQL.getConnection(), Nivel.class);
	
	Nivel nivel = new Nivel("descripcion");
	
	nv.save(nivel);
	
	System.out.println(nivel);
	
	}
}