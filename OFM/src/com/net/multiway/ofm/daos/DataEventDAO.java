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
import com.net.multiway.ofm.entities.Data;
import com.net.multiway.ofm.entities.DataEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author phelipe
 */
public class DataEventDAO implements Serializable {

    public DataEventDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DataEvent dataEvent) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Data data = dataEvent.getData();
            if (data != null) {
                data = em.getReference(data.getClass(), data.getDataId());
                dataEvent.setData(data);
            }
            em.persist(dataEvent);
            if (data != null) {
                data.getDataEventList().add(dataEvent);
                data = em.merge(data);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DataEvent dataEvent) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DataEvent persistentDataEvent = em.find(DataEvent.class, dataEvent.getDataEventId());
            Data dataOld = persistentDataEvent.getData();
            Data dataNew = dataEvent.getData();
            if (dataNew != null) {
                dataNew = em.getReference(dataNew.getClass(), dataNew.getDataId());
                dataEvent.setData(dataNew);
            }
            dataEvent = em.merge(dataEvent);
            if (dataOld != null && !dataOld.equals(dataNew)) {
                dataOld.getDataEventList().remove(dataEvent);
                dataOld = em.merge(dataOld);
            }
            if (dataNew != null && !dataNew.equals(dataOld)) {
                dataNew.getDataEventList().add(dataEvent);
                dataNew = em.merge(dataNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dataEvent.getDataEventId();
                if (findDataEvent(id) == null) {
                    throw new NonexistentEntityException("The dataEvent with id " + id + " no longer exists.");
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
            DataEvent dataEvent;
            try {
                dataEvent = em.getReference(DataEvent.class, id);
                dataEvent.getDataEventId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dataEvent with id " + id + " no longer exists.", enfe);
            }
            Data data = dataEvent.getData();
            if (data != null) {
                data.getDataEventList().remove(dataEvent);
                data = em.merge(data);
            }
            em.remove(dataEvent);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DataEvent> findDataEventEntities() {
        return findDataEventEntities(true, -1, -1);
    }

    public List<DataEvent> findDataEventEntities(int maxResults, int firstResult) {
        return findDataEventEntities(false, maxResults, firstResult);
    }

    private List<DataEvent> findDataEventEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DataEvent.class));
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

    public DataEvent findDataEvent(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DataEvent.class, id);
        } finally {
            em.close();
        }
    }

    public int getDataEventCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DataEvent> rt = cq.from(DataEvent.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
