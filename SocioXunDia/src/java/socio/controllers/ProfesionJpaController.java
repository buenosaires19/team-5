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
import socio.entities.Videos;
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
import socio.entities.Profesion;

/**
 *
 * @author seba
 */
public class ProfesionJpaController implements Serializable {

	public ProfesionJpaController(UserTransaction utx, EntityManagerFactory emf) {
		this.utx = utx;
		this.emf = emf;
	}
	private UserTransaction utx = null;
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Profesion profesion) throws RollbackFailureException, Exception {
		if (profesion.getMentorCollection() == null) {
			profesion.setMentorCollection(new ArrayList<Mentor>());
		}
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Videos videoid = profesion.getVideoid();
			if (videoid != null) {
				videoid = em.getReference(videoid.getClass(), videoid.getIdvideos());
				profesion.setVideoid(videoid);
			}
			Collection<Mentor> attachedMentorCollection = new ArrayList<Mentor>();
			for (Mentor mentorCollectionMentorToAttach : profesion.getMentorCollection()) {
				mentorCollectionMentorToAttach = em.getReference(mentorCollectionMentorToAttach.getClass(), mentorCollectionMentorToAttach.getIdmentor());
				attachedMentorCollection.add(mentorCollectionMentorToAttach);
			}
			profesion.setMentorCollection(attachedMentorCollection);
			em.persist(profesion);
			if (videoid != null) {
				videoid.getProfesionCollection().add(profesion);
				videoid = em.merge(videoid);
			}
			for (Mentor mentorCollectionMentor : profesion.getMentorCollection()) {
				Profesion oldProfesionidOfMentorCollectionMentor = mentorCollectionMentor.getProfesionid();
				mentorCollectionMentor.setProfesionid(profesion);
				mentorCollectionMentor = em.merge(mentorCollectionMentor);
				if (oldProfesionidOfMentorCollectionMentor != null) {
					oldProfesionidOfMentorCollectionMentor.getMentorCollection().remove(mentorCollectionMentor);
					oldProfesionidOfMentorCollectionMentor = em.merge(oldProfesionidOfMentorCollectionMentor);
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

	public void edit(Profesion profesion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Profesion persistentProfesion = em.find(Profesion.class, profesion.getIdprofesion());
			Videos videoidOld = persistentProfesion.getVideoid();
			Videos videoidNew = profesion.getVideoid();
			Collection<Mentor> mentorCollectionOld = persistentProfesion.getMentorCollection();
			Collection<Mentor> mentorCollectionNew = profesion.getMentorCollection();
			List<String> illegalOrphanMessages = null;
			for (Mentor mentorCollectionOldMentor : mentorCollectionOld) {
				if (!mentorCollectionNew.contains(mentorCollectionOldMentor)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Mentor " + mentorCollectionOldMentor + " since its profesionid field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			if (videoidNew != null) {
				videoidNew = em.getReference(videoidNew.getClass(), videoidNew.getIdvideos());
				profesion.setVideoid(videoidNew);
			}
			Collection<Mentor> attachedMentorCollectionNew = new ArrayList<Mentor>();
			for (Mentor mentorCollectionNewMentorToAttach : mentorCollectionNew) {
				mentorCollectionNewMentorToAttach = em.getReference(mentorCollectionNewMentorToAttach.getClass(), mentorCollectionNewMentorToAttach.getIdmentor());
				attachedMentorCollectionNew.add(mentorCollectionNewMentorToAttach);
			}
			mentorCollectionNew = attachedMentorCollectionNew;
			profesion.setMentorCollection(mentorCollectionNew);
			profesion = em.merge(profesion);
			if (videoidOld != null && !videoidOld.equals(videoidNew)) {
				videoidOld.getProfesionCollection().remove(profesion);
				videoidOld = em.merge(videoidOld);
			}
			if (videoidNew != null && !videoidNew.equals(videoidOld)) {
				videoidNew.getProfesionCollection().add(profesion);
				videoidNew = em.merge(videoidNew);
			}
			for (Mentor mentorCollectionNewMentor : mentorCollectionNew) {
				if (!mentorCollectionOld.contains(mentorCollectionNewMentor)) {
					Profesion oldProfesionidOfMentorCollectionNewMentor = mentorCollectionNewMentor.getProfesionid();
					mentorCollectionNewMentor.setProfesionid(profesion);
					mentorCollectionNewMentor = em.merge(mentorCollectionNewMentor);
					if (oldProfesionidOfMentorCollectionNewMentor != null && !oldProfesionidOfMentorCollectionNewMentor.equals(profesion)) {
						oldProfesionidOfMentorCollectionNewMentor.getMentorCollection().remove(mentorCollectionNewMentor);
						oldProfesionidOfMentorCollectionNewMentor = em.merge(oldProfesionidOfMentorCollectionNewMentor);
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
				Integer id = profesion.getIdprofesion();
				if (findProfesion(id) == null) {
					throw new NonexistentEntityException("The profesion with id " + id + " no longer exists.");
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
			Profesion profesion;
			try {
				profesion = em.getReference(Profesion.class, id);
				profesion.getIdprofesion();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The profesion with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			Collection<Mentor> mentorCollectionOrphanCheck = profesion.getMentorCollection();
			for (Mentor mentorCollectionOrphanCheckMentor : mentorCollectionOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Profesion (" + profesion + ") cannot be destroyed since the Mentor " + mentorCollectionOrphanCheckMentor + " in its mentorCollection field has a non-nullable profesionid field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			Videos videoid = profesion.getVideoid();
			if (videoid != null) {
				videoid.getProfesionCollection().remove(profesion);
				videoid = em.merge(videoid);
			}
			em.remove(profesion);
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

	public List<Profesion> findProfesionEntities() {
		return findProfesionEntities(true, -1, -1);
	}

	public List<Profesion> findProfesionEntities(int maxResults, int firstResult) {
		return findProfesionEntities(false, maxResults, firstResult);
	}

	private List<Profesion> findProfesionEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Profesion.class));
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

	public Profesion findProfesion(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Profesion.class, id);
		} finally {
			em.close();
		}
	}

	public int getProfesionCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Profesion> rt = cq.from(Profesion.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
