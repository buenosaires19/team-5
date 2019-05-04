package socio.repositories;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import socio.entities.Usuario;

public class TestGenericR {
    
	
	public static void testGeneric(String nombre) {
		
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("socioPU");
			EntityManager em = emf.createEntityManager();
			TypedQuery<Usuario> tqu = em.createQuery("SELECT u FROM usuario u",Usuario.class);
			
			List<Usuario> lista = new ArrayList();
			lista = tqu.getResultList();
			lista.stream().forEach(item -> System.out.println(item.getNombreusuario()));
		
	}
	
	public static void main(String[] args) {
		testGeneric("");
	}
    
}