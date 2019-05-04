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
import socio.entities.Mentor;
import socio.entities.Region;
import socio.entities.Profesion;

/**
 *
 * @author seba
 */
public class MentorJpaController implements Serializable {

	public MentorJpaController(UserTransaction utx, EntityManagerFactory emf) {
		this.utx = utx;
		this.emf = emf;
	}
	private UserTransaction utx = null;
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Mentor mentor) throws RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Region regionid = mentor.getRegionid();
			if (regionid != null) {
				regionid = em.getReference(regionid.getClass(), regionid.getIdregion());
				mentor.setRegionid(regionid);
			}
			Profesion profesionid = mentor.getProfesionid();
			if (profesionid != null) {
				profesionid = em.getReference(profesionid.getClass(), profesionid.getIdprofesion());
				mentor.setProfesionid(profesionid);
			}
			em.persist(mentor);
			if (regionid != null) {
				regionid.getMentorCollection().add(mentor);
				regionid = em.merge(regionid);
			}
			if (profesionid != null) {
				profesionid.getMentorCollection().add(mentor);
				profesionid = em.merge(profesionid);
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

	public void edit(Mentor mentor) throws NonexistentEntityException, RollbackFailureException, Exception {
		EntityManager em = null;
		try {
			utx.begin();
			em = getEntityManager();
			Mentor persistentMentor = em.find(Mentor.class, mentor.getIdmentor());
			Region regionidOld = persistentMentor.getRegionid();
			Region regionidNew = mentor.getRegionid();
			Profesion profesionidOld = persistentMentor.getProfesionid();
			Profesion profesionidNew = mentor.getProfesionid();
			if (regionidNew != null) {
				regionidNew = em.getReference(regionidNew.getClass(), regionidNew.getIdregion());
				mentor.setRegionid(regionidNew);
			}
			if (profesionidNew != null) {
				profesionidNew = em.getReference(profesionidNew.getClass(), profesionidNew.getIdprofesion());
				mentor.setProfesionid(profesionidNew);
			}
			mentor = em.merge(mentor);
			if (regionidOld != null && !regionidOld.equals(regionidNew)) {
				regionidOld.getMentorCollection().remove(mentor);
				regionidOld = em.merge(regionidOld);
			}
			if (regionidNew != null && !regionidNew.equals(regionidOld)) {
				regionidNew.getMentorCollection().add(mentor);
				regionidNew = em.merge(regionidNew);
			}
			if (profesionidOld != null && !profesionidOld.equals(profesionidNew)) {
				profesionidOld.getMentorCollection().remove(mentor);
				profesionidOld = em.merge(profesionidOld);
			}
			if (profesionidNew != null && !profesionidNew.equals(profesionidOld)) {
				profesionidNew.getMentorCollection().add(mentor);
				profesionidNew = em.merge(profesionidNew);
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
				Integer id = mentor.getIdmentor();
				if (findMentor(id) == null) {
					throw new NonexistentEntityException("The mentor with id " + id + " no longer exists.");
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
			Mentor mentor;
			try {
				mentor = em.getReference(Mentor.class, id);
				mentor.getIdmentor();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The mentor with id " + id + " no longer exists.", enfe);
			}
			Region regionid = mentor.getRegionid();
			if (regionid != null) {
				regionid.getMentorCollection().remove(mentor);
				regionid = em.merge(regionid);
			}
			Profesion profesionid = mentor.getProfesionid();
			if (profesionid != null) {
				profesionid.getMentorCollection().remove(mentor);
				profesionid = em.merge(profesionid);
			}
			em.remove(mentor);
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

	public List<Mentor> findMentorEntities() {
		return findMentorEntities(true, -1, -1);
	}

	public List<Mentor> findMentorEntities(int maxResults, int firstResult) {
		return findMentorEntities(false, maxResults, firstResult);
	}

	private List<Mentor> findMentorEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Mentor.class));
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

	public Mentor findMentor(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Mentor.class, id);
		} finally {
			em.close();
		}
	}

	public int getMentorCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Mentor> rt = cq.from(Mentor.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
