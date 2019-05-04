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
import socio.entities.Mentor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import socio.controllers.exceptions.IllegalOrphanException;
import socio.controllers.exceptions.NonexistentEntityException;
import socio.controllers.exceptions.RollbackFailureException;
import socio.entities.Region;
import socio.entities.Usuario;

/**
 *
 * @author seba
 */
public class RegionJpaController implements Serializable {

	public RegionJpaController(UserTransaction utx, EntityManagerFactory emf) {
		this.utx = utx;
		this.emf = emf;
	}
	private UserTransaction utx = null;
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Region region) throws RollbackFailureException, Exception {
		if (region.getMentorCollection() == null) {
			region.setMentorCollection(new ArrayList<Mentor>());
		}
		if (region.getUsuarioCollection() == null) {
			region.setUsuarioCollection(new ArrayList<Usuario>());
		}
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Collection<Mentor> attachedMentorCollection = new ArrayList<Mentor>();
			for (Mentor mentorCollectionMentorToAttach : region.getMentorCollection()) {
				mentorCollectionMentorToAttach = em.getReference(mentorCollectionMentorToAttach.getClass(), mentorCollectionMentorToAttach.getIdmentor());
				attachedMentorCollection.add(mentorCollectionMentorToAttach);
			}
			region.setMentorCollection(attachedMentorCollection);
			Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
			for (Usuario usuarioCollectionUsuarioToAttach : region.getUsuarioCollection()) {
				usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdusuario());
				attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
			}
			region.setUsuarioCollection(attachedUsuarioCollection);
			em.persist(region);
			for (Mentor mentorCollectionMentor : region.getMentorCollection()) {
				Region oldRegionidOfMentorCollectionMentor = mentorCollectionMentor.getRegionid();
				mentorCollectionMentor.setRegionid(region);
				mentorCollectionMentor = em.merge(mentorCollectionMentor);
				if (oldRegionidOfMentorCollectionMentor != null) {
					oldRegionidOfMentorCollectionMentor.getMentorCollection().remove(mentorCollectionMentor);
					oldRegionidOfMentorCollectionMentor = em.merge(oldRegionidOfMentorCollectionMentor);
				}
			}
			for (Usuario usuarioCollectionUsuario : region.getUsuarioCollection()) {
				Region oldRegionidOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getRegionid();
				usuarioCollectionUsuario.setRegionid(region);
				usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
				if (oldRegionidOfUsuarioCollectionUsuario != null) {
					oldRegionidOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
					oldRegionidOfUsuarioCollectionUsuario = em.merge(oldRegionidOfUsuarioCollectionUsuario);
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

	public void edit(Region region) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Region persistentRegion = em.find(Region.class, region.getIdregion());
			Collection<Mentor> mentorCollectionOld = persistentRegion.getMentorCollection();
			Collection<Mentor> mentorCollectionNew = region.getMentorCollection();
			Collection<Usuario> usuarioCollectionOld = persistentRegion.getUsuarioCollection();
			Collection<Usuario> usuarioCollectionNew = region.getUsuarioCollection();
			List<String> illegalOrphanMessages = null;
			for (Mentor mentorCollectionOldMentor : mentorCollectionOld) {
				if (!mentorCollectionNew.contains(mentorCollectionOldMentor)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Mentor " + mentorCollectionOldMentor + " since its regionid field is not nullable.");
				}
			}
			for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
				if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its regionid field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			Collection<Mentor> attachedMentorCollectionNew = new ArrayList<Mentor>();
			for (Mentor mentorCollectionNewMentorToAttach : mentorCollectionNew) {
				mentorCollectionNewMentorToAttach = em.getReference(mentorCollectionNewMentorToAttach.getClass(), mentorCollectionNewMentorToAttach.getIdmentor());
				attachedMentorCollectionNew.add(mentorCollectionNewMentorToAttach);
			}
			mentorCollectionNew = attachedMentorCollectionNew;
			region.setMentorCollection(mentorCollectionNew);
			Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
			for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
				usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getIdusuario());
				attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
			}
			usuarioCollectionNew = attachedUsuarioCollectionNew;
			region.setUsuarioCollection(usuarioCollectionNew);
			region = em.merge(region);
			for (Mentor mentorCollectionNewMentor : mentorCollectionNew) {
				if (!mentorCollectionOld.contains(mentorCollectionNewMentor)) {
					Region oldRegionidOfMentorCollectionNewMentor = mentorCollectionNewMentor.getRegionid();
					mentorCollectionNewMentor.setRegionid(region);
					mentorCollectionNewMentor = em.merge(mentorCollectionNewMentor);
					if (oldRegionidOfMentorCollectionNewMentor != null && !oldRegionidOfMentorCollectionNewMentor.equals(region)) {
						oldRegionidOfMentorCollectionNewMentor.getMentorCollection().remove(mentorCollectionNewMentor);
						oldRegionidOfMentorCollectionNewMentor = em.merge(oldRegionidOfMentorCollectionNewMentor);
					}
				}
			}
			for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
				if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
					Region oldRegionidOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getRegionid();
					usuarioCollectionNewUsuario.setRegionid(region);
					usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
					if (oldRegionidOfUsuarioCollectionNewUsuario != null && !oldRegionidOfUsuarioCollectionNewUsuario.equals(region)) {
						oldRegionidOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
						oldRegionidOfUsuarioCollectionNewUsuario = em.merge(oldRegionidOfUsuarioCollectionNewUsuario);
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
				Integer id = region.getIdregion();
				if (findRegion(id) == null) {
					throw new NonexistentEntityException("The region with id " + id + " no longer exists.");
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
			Region region;
			try {
				region = em.getReference(Region.class, id);
				region.getIdregion();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The region with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			Collection<Mentor> mentorCollectionOrphanCheck = region.getMentorCollection();
			for (Mentor mentorCollectionOrphanCheckMentor : mentorCollectionOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Region (" + region + ") cannot be destroyed since the Mentor " + mentorCollectionOrphanCheckMentor + " in its mentorCollection field has a non-nullable regionid field.");
			}
			Collection<Usuario> usuarioCollectionOrphanCheck = region.getUsuarioCollection();
			for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Region (" + region + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable regionid field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(region);
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

	public List<Region> findRegionEntities() {
		return findRegionEntities(true, -1, -1);
	}

	public List<Region> findRegionEntities(int maxResults, int firstResult) {
		return findRegionEntities(false, maxResults, firstResult);
	}

	private List<Region> findRegionEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Region.class));
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

	public Region findRegion(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Region.class, id);
		} finally {
			em.close();
		}
	}

	public int getRegionCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Region> rt = cq.from(Region.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
