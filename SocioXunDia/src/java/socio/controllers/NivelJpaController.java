/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socio.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import socio.entities.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import socio.controllers.exceptions.IllegalOrphanException;
import socio.controllers.exceptions.NonexistentEntityException;
import socio.controllers.exceptions.RollbackFailureException;
import socio.entities.Nivel;

/**
 *
 * @author seba
 */
public class NivelJpaController implements Serializable {

	public NivelJpaController(UserTransaction utx, EntityManagerFactory emf) {
		this.utx = utx;
		this.emf = emf;
	}
	private UserTransaction utx = null;
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Nivel nivel) throws RollbackFailureException, Exception {
		if (nivel.getUsuarioCollection() == null) {
			nivel.setUsuarioCollection(new ArrayList<Usuario>());
		}
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
			for (Usuario usuarioCollectionUsuarioToAttach : nivel.getUsuarioCollection()) {
				usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdusuario());
				attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
			}
			nivel.setUsuarioCollection(attachedUsuarioCollection);
			em.persist(nivel);
			for (Usuario usuarioCollectionUsuario : nivel.getUsuarioCollection()) {
				Nivel oldNivelidOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getNivelid();
				usuarioCollectionUsuario.setNivelid(nivel);
				usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
				if (oldNivelidOfUsuarioCollectionUsuario != null) {
					oldNivelidOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
					oldNivelidOfUsuarioCollectionUsuario = em.merge(oldNivelidOfUsuarioCollectionUsuario);
				}
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

	public void edit(Nivel nivel) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Nivel persistentNivel = em.find(Nivel.class, nivel.getIdnivel());
			Collection<Usuario> usuarioCollectionOld = persistentNivel.getUsuarioCollection();
			Collection<Usuario> usuarioCollectionNew = nivel.getUsuarioCollection();
			List<String> illegalOrphanMessages = null;
			for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
				if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its nivelid field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
			for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
				usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getIdusuario());
				attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
			}
			usuarioCollectionNew = attachedUsuarioCollectionNew;
			nivel.setUsuarioCollection(usuarioCollectionNew);
			nivel = em.merge(nivel);
			for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
				if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
					Nivel oldNivelidOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getNivelid();
					usuarioCollectionNewUsuario.setNivelid(nivel);
					usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
					if (oldNivelidOfUsuarioCollectionNewUsuario != null && !oldNivelidOfUsuarioCollectionNewUsuario.equals(nivel)) {
						oldNivelidOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
						oldNivelidOfUsuarioCollectionNewUsuario = em.merge(oldNivelidOfUsuarioCollectionNewUsuario);
					}
				}
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
				Integer id = nivel.getIdnivel();
				if (findNivel(id) == null) {
					throw new NonexistentEntityException("The nivel with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Nivel nivel;
			try {
				nivel = em.getReference(Nivel.class, id);
				nivel.getIdnivel();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The nivel with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			Collection<Usuario> usuarioCollectionOrphanCheck = nivel.getUsuarioCollection();
			for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Nivel (" + nivel + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable nivelid field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(nivel);
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

	public List<Nivel> findNivelEntities() {
		return findNivelEntities(true, -1, -1);
	}

	public List<Nivel> findNivelEntities(int maxResults, int firstResult) {
		return findNivelEntities(false, maxResults, firstResult);
	}

	private List<Nivel> findNivelEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Nivel.class));
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

	public Nivel findNivel(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Nivel.class, id);
		} finally {
			em.close();
		}
	}

	public int getNivelCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Nivel> rt = cq.from(Nivel.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
