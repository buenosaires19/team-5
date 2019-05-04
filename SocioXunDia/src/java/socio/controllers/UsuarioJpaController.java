/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socio.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import socio.controllers.exceptions.NonexistentEntityException;
import socio.controllers.exceptions.RollbackFailureException;
import socio.entities.Region;
import socio.entities.Nivel;
import socio.entities.Usuario;
import socio.entities.Videos;

/**
 *
 * @author seba
 */
public class UsuarioJpaController implements Serializable {

	public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
		this.utx = utx;
		this.emf = emf;
	}
	private UserTransaction utx = null;
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Usuario usuario) throws RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Region regionid = usuario.getRegionid();
			if (regionid != null) {
				regionid = em.getReference(regionid.getClass(), regionid.getIdregion());
				usuario.setRegionid(regionid);
			}
			Nivel nivelid = usuario.getNivelid();
			if (nivelid != null) {
				nivelid = em.getReference(nivelid.getClass(), nivelid.getIdnivel());
				usuario.setNivelid(nivelid);
			}
			Videos videoid = usuario.getVideoid();
			if (videoid != null) {
				videoid = em.getReference(videoid.getClass(), videoid.getIdvideos());
				usuario.setVideoid(videoid);
			}
			em.persist(usuario);
			if (regionid != null) {
				regionid.getUsuarioCollection().add(usuario);
				regionid = em.merge(regionid);
			}
			if (nivelid != null) {
				nivelid.getUsuarioCollection().add(usuario);
				nivelid = em.merge(nivelid);
			}
			if (videoid != null) {
				videoid.getUsuarioCollection().add(usuario);
				videoid = em.merge(videoid);
			}
			utx.commit();
		} catch (Exception ex) {
			try {
				utx.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Usuario usuario) throws NonexistentEntityException, RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdusuario());
			Region regionidOld = persistentUsuario.getRegionid();
			Region regionidNew = usuario.getRegionid();
			Nivel nivelidOld = persistentUsuario.getNivelid();
			Nivel nivelidNew = usuario.getNivelid();
			Videos videoidOld = persistentUsuario.getVideoid();
			Videos videoidNew = usuario.getVideoid();
			if (regionidNew != null) {
				regionidNew = em.getReference(regionidNew.getClass(), regionidNew.getIdregion());
				usuario.setRegionid(regionidNew);
			}
			if (nivelidNew != null) {
				nivelidNew = em.getReference(nivelidNew.getClass(), nivelidNew.getIdnivel());
				usuario.setNivelid(nivelidNew);
			}
			if (videoidNew != null) {
				videoidNew = em.getReference(videoidNew.getClass(), videoidNew.getIdvideos());
				usuario.setVideoid(videoidNew);
			}
			usuario = em.merge(usuario);
			if (regionidOld != null && !regionidOld.equals(regionidNew)) {
				regionidOld.getUsuarioCollection().remove(usuario);
				regionidOld = em.merge(regionidOld);
			}
			if (regionidNew != null && !regionidNew.equals(regionidOld)) {
				regionidNew.getUsuarioCollection().add(usuario);
				regionidNew = em.merge(regionidNew);
			}
			if (nivelidOld != null && !nivelidOld.equals(nivelidNew)) {
				nivelidOld.getUsuarioCollection().remove(usuario);
				nivelidOld = em.merge(nivelidOld);
			}
			if (nivelidNew != null && !nivelidNew.equals(nivelidOld)) {
				nivelidNew.getUsuarioCollection().add(usuario);
				nivelidNew = em.merge(nivelidNew);
			}
			if (videoidOld != null && !videoidOld.equals(videoidNew)) {
				videoidOld.getUsuarioCollection().remove(usuario);
				videoidOld = em.merge(videoidOld);
			}
			if (videoidNew != null && !videoidNew.equals(videoidOld)) {
				videoidNew.getUsuarioCollection().add(usuario);
				videoidNew = em.merge(videoidNew);
			}
			utx.commit();
		} catch (Exception ex) {
			try {
				utx.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
			}
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = usuario.getIdusuario();
				if (findUsuario(id) == null) {
					throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Usuario usuario;
			try {
				usuario = em.getReference(Usuario.class, id);
				usuario.getIdusuario();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
			}
			Region regionid = usuario.getRegionid();
			if (regionid != null) {
				regionid.getUsuarioCollection().remove(usuario);
				regionid = em.merge(regionid);
			}
			Nivel nivelid = usuario.getNivelid();
			if (nivelid != null) {
				nivelid.getUsuarioCollection().remove(usuario);
				nivelid = em.merge(nivelid);
			}
			Videos videoid = usuario.getVideoid();
			if (videoid != null) {
				videoid.getUsuarioCollection().remove(usuario);
				videoid = em.merge(videoid);
			}
			em.remove(usuario);
			utx.commit();
		} catch (Exception ex) {
			try {
				utx.rollback();
			} catch (Exception re) {
				throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Usuario> findUsuarioEntities() {
		return findUsuarioEntities(true, -1, -1);
	}

	public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
		return findUsuarioEntities(false, maxResults, firstResult);
	}

	private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Usuario.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Usuario findUsuario(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Usuario.class, id);
		} finally {
			em.close();
		}
	}

	public int getUsuarioCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Usuario> rt = cq.from(Usuario.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
