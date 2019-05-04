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
import socio.entities.Profesion;
import socio.entities.Videos;

/**
 *
 * @author seba
 */
public class VideosJpaController implements Serializable {

	public VideosJpaController(UserTransaction utx, EntityManagerFactory emf) {
		this.utx = utx;
		this.emf = emf;
	}
	private UserTransaction utx = null;
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Videos videos) throws RollbackFailureException, Exception {
		if (videos.getUsuarioCollection() == null) {
			videos.setUsuarioCollection(new ArrayList<Usuario>());
		}
		if (videos.getProfesionCollection() == null) {
			videos.setProfesionCollection(new ArrayList<Profesion>());
		}
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
			for (Usuario usuarioCollectionUsuarioToAttach : videos.getUsuarioCollection()) {
				usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdusuario());
				attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
			}
			videos.setUsuarioCollection(attachedUsuarioCollection);
			Collection<Profesion> attachedProfesionCollection = new ArrayList<Profesion>();
			for (Profesion profesionCollectionProfesionToAttach : videos.getProfesionCollection()) {
				profesionCollectionProfesionToAttach = em.getReference(profesionCollectionProfesionToAttach.getClass(), profesionCollectionProfesionToAttach.getIdprofesion());
				attachedProfesionCollection.add(profesionCollectionProfesionToAttach);
			}
			videos.setProfesionCollection(attachedProfesionCollection);
			em.persist(videos);
			for (Usuario usuarioCollectionUsuario : videos.getUsuarioCollection()) {
				Videos oldVideoidOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getVideoid();
				usuarioCollectionUsuario.setVideoid(videos);
				usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
				if (oldVideoidOfUsuarioCollectionUsuario != null) {
					oldVideoidOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
					oldVideoidOfUsuarioCollectionUsuario = em.merge(oldVideoidOfUsuarioCollectionUsuario);
				}
			}
			for (Profesion profesionCollectionProfesion : videos.getProfesionCollection()) {
				Videos oldVideoidOfProfesionCollectionProfesion = profesionCollectionProfesion.getVideoid();
				profesionCollectionProfesion.setVideoid(videos);
				profesionCollectionProfesion = em.merge(profesionCollectionProfesion);
				if (oldVideoidOfProfesionCollectionProfesion != null) {
					oldVideoidOfProfesionCollectionProfesion.getProfesionCollection().remove(profesionCollectionProfesion);
					oldVideoidOfProfesionCollectionProfesion = em.merge(oldVideoidOfProfesionCollectionProfesion);
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

	public void edit(Videos videos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Videos persistentVideos = em.find(Videos.class, videos.getIdvideos());
			Collection<Usuario> usuarioCollectionOld = persistentVideos.getUsuarioCollection();
			Collection<Usuario> usuarioCollectionNew = videos.getUsuarioCollection();
			Collection<Profesion> profesionCollectionOld = persistentVideos.getProfesionCollection();
			Collection<Profesion> profesionCollectionNew = videos.getProfesionCollection();
			List<String> illegalOrphanMessages = null;
			for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
				if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its videoid field is not nullable.");
				}
			}
			for (Profesion profesionCollectionOldProfesion : profesionCollectionOld) {
				if (!profesionCollectionNew.contains(profesionCollectionOldProfesion)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Profesion " + profesionCollectionOldProfesion + " since its videoid field is not nullable.");
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
			videos.setUsuarioCollection(usuarioCollectionNew);
			Collection<Profesion> attachedProfesionCollectionNew = new ArrayList<Profesion>();
			for (Profesion profesionCollectionNewProfesionToAttach : profesionCollectionNew) {
				profesionCollectionNewProfesionToAttach = em.getReference(profesionCollectionNewProfesionToAttach.getClass(), profesionCollectionNewProfesionToAttach.getIdprofesion());
				attachedProfesionCollectionNew.add(profesionCollectionNewProfesionToAttach);
			}
			profesionCollectionNew = attachedProfesionCollectionNew;
			videos.setProfesionCollection(profesionCollectionNew);
			videos = em.merge(videos);
			for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
				if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
					Videos oldVideoidOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getVideoid();
					usuarioCollectionNewUsuario.setVideoid(videos);
					usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
					if (oldVideoidOfUsuarioCollectionNewUsuario != null && !oldVideoidOfUsuarioCollectionNewUsuario.equals(videos)) {
						oldVideoidOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
						oldVideoidOfUsuarioCollectionNewUsuario = em.merge(oldVideoidOfUsuarioCollectionNewUsuario);
					}
				}
			}
			for (Profesion profesionCollectionNewProfesion : profesionCollectionNew) {
				if (!profesionCollectionOld.contains(profesionCollectionNewProfesion)) {
					Videos oldVideoidOfProfesionCollectionNewProfesion = profesionCollectionNewProfesion.getVideoid();
					profesionCollectionNewProfesion.setVideoid(videos);
					profesionCollectionNewProfesion = em.merge(profesionCollectionNewProfesion);
					if (oldVideoidOfProfesionCollectionNewProfesion != null && !oldVideoidOfProfesionCollectionNewProfesion.equals(videos)) {
						oldVideoidOfProfesionCollectionNewProfesion.getProfesionCollection().remove(profesionCollectionNewProfesion);
						oldVideoidOfProfesionCollectionNewProfesion = em.merge(oldVideoidOfProfesionCollectionNewProfesion);
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
				Integer id = videos.getIdvideos();
				if (findVideos(id) == null) {
					throw new NonexistentEntityException("The videos with id " + id + " no longer exists.");
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
			Videos videos;
			try {
				videos = em.getReference(Videos.class, id);
				videos.getIdvideos();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The videos with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			Collection<Usuario> usuarioCollectionOrphanCheck = videos.getUsuarioCollection();
			for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Videos (" + videos + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable videoid field.");
			}
			Collection<Profesion> profesionCollectionOrphanCheck = videos.getProfesionCollection();
			for (Profesion profesionCollectionOrphanCheckProfesion : profesionCollectionOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Videos (" + videos + ") cannot be destroyed since the Profesion " + profesionCollectionOrphanCheckProfesion + " in its profesionCollection field has a non-nullable videoid field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(videos);
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

	public List<Videos> findVideosEntities() {
		return findVideosEntities(true, -1, -1);
	}

	public List<Videos> findVideosEntities(int maxResults, int firstResult) {
		return findVideosEntities(false, maxResults, firstResult);
	}

	private List<Videos> findVideosEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Videos.class));
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

	public Videos findVideos(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Videos.class, id);
		} finally {
			em.close();
		}
	}

	public int getVideosCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Videos> rt = cq.from(Videos.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
