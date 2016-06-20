/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.daos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.net.multiway.ofm.daos.exceptions.NonexistentEntityException;
import com.net.multiway.ofm.entities.Data;
import com.net.multiway.ofm.entities.DataGraphic;
import java.util.ArrayList;

/**
 *
 * @author joshua
 */
public class DataGraphicDAO extends DAO implements Serializable {

    public void createAll(ArrayList<DataGraphic> dataGraphics) {
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            for(DataGraphic dataGraphic : dataGraphics) {
                Data data = dataGraphic.getData();
                if (data != null) {
                    data = em.getReference(data.getClass(), data.getDataId());
                    dataGraphic.setData(data);
                }
                em.persist(dataGraphic);
                if (data != null) {
                    data.getDataGraphicList().add(dataGraphic);
                    data = em.merge(data);
                }
            }
            
            em.getTransaction().commit();
            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void create(DataGraphic dataGraphic) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Data data = dataGraphic.getData();
            if (data != null) {
                data = em.getReference(data.getClass(), data.getDataId());
                dataGraphic.setData(data);
            }
            em.persist(dataGraphic);
            if (data != null) {
                data.getDataGraphicList().add(dataGraphic);
                data = em.merge(data);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DataGraphic dataGraphic) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DataGraphic persistentDataGraphic = em.find(DataGraphic.class, dataGraphic.getDataGraphicId());
            Data dataOld = persistentDataGraphic.getData();
            Data dataNew = dataGraphic.getData();
            if (dataNew != null) {
                dataNew = em.getReference(dataNew.getClass(), dataNew.getDataId());
                dataGraphic.setData(dataNew);
            }
            dataGraphic = em.merge(dataGraphic);
            if (dataOld != null && !dataOld.equals(dataNew)) {
                dataOld.getDataGraphicList().remove(dataGraphic);
                dataOld = em.merge(dataOld);
            }
            if (dataNew != null && !dataNew.equals(dataOld)) {
                dataNew.getDataGraphicList().add(dataGraphic);
                dataNew = em.merge(dataNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = dataGraphic.getDataGraphicId();
                if (findDataGraphic(id) == null) {
                    throw new NonexistentEntityException("The dataGraphic with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DataGraphic dataGraphic;
            try {
                dataGraphic = em.getReference(DataGraphic.class, id);
                dataGraphic.getDataGraphicId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dataGraphic with id " + id + " no longer exists.", enfe);
            }
            Data data = dataGraphic.getData();
            if (data != null) {
                data.getDataGraphicList().remove(dataGraphic);
                data = em.merge(data);
            }
            em.remove(dataGraphic);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DataGraphic> findDataGraphicEntities() {
        return findDataGraphicEntities(true, -1, -1);
    }

    public List<DataGraphic> findDataGraphicEntities(int maxResults, int firstResult) {
        return findDataGraphicEntities(false, maxResults, firstResult);
    }

    private List<DataGraphic> findDataGraphicEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DataGraphic.class));
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

    public DataGraphic findDataGraphic(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DataGraphic.class, id);
        } finally {
            em.close();
        }
    }

    public int getDataGraphicCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DataGraphic> rt = cq.from(DataGraphic.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
