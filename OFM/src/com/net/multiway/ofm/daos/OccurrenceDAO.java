/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.daos;

import com.net.multiway.ofm.daos.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.Occurrence;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author joshua
 */
public class OccurrenceDAO implements Serializable {

    public OccurrenceDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Occurrence occurrence) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Device device = occurrence.getDevice();
            if (device != null) {
                device = em.getReference(device.getClass(), device.getDeviceId());
                occurrence.setDevice(device);
            }
            em.persist(occurrence);
            if (device != null) {
                device.getOccurrenceList().add(occurrence);
                device = em.merge(device);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Occurrence occurrence) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Occurrence persistentOccurrence = em.find(Occurrence.class, occurrence.getOccurrenceId());
            Device deviceOld = persistentOccurrence.getDevice();
            Device deviceNew = occurrence.getDevice();
            if (deviceNew != null) {
                deviceNew = em.getReference(deviceNew.getClass(), deviceNew.getDeviceId());
                occurrence.setDevice(deviceNew);
            }
            occurrence = em.merge(occurrence);
            if (deviceOld != null && !deviceOld.equals(deviceNew)) {
                deviceOld.getOccurrenceList().remove(occurrence);
                deviceOld = em.merge(deviceOld);
            }
            if (deviceNew != null && !deviceNew.equals(deviceOld)) {
                deviceNew.getOccurrenceList().add(occurrence);
                deviceNew = em.merge(deviceNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = occurrence.getOccurrenceId();
                if (findOccurrence(id) == null) {
                    throw new NonexistentEntityException("The occurrence with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Occurrence occurrence;
            try {
                occurrence = em.getReference(Occurrence.class, id);
                occurrence.getOccurrenceId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The occurrence with id " + id + " no longer exists.", enfe);
            }
            Device device = occurrence.getDevice();
            if (device != null) {
                device.getOccurrenceList().remove(occurrence);
                device = em.merge(device);
            }
            em.remove(occurrence);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Occurrence> findOccurrenceEntities() {
        return findOccurrenceEntities(true, -1, -1);
    }

    public List<Occurrence> findOccurrenceEntities(int maxResults, int firstResult) {
        return findOccurrenceEntities(false, maxResults, firstResult);
    }

    private List<Occurrence> findOccurrenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Occurrence.class));
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

    public Occurrence findOccurrence(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Occurrence.class, id);
        } finally {
            em.close();
        }
    }

    public int getOccurrenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Occurrence> rt = cq.from(Occurrence.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
