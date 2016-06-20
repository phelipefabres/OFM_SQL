/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.daos;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import com.net.multiway.ofm.daos.exceptions.IllegalOrphanException;
import com.net.multiway.ofm.daos.exceptions.NonexistentEntityException;
import com.net.multiway.ofm.entities.Limit;

/**
 *
 * @author joshua
 */
public class LimitDAO extends DAO implements Serializable {

    public void create(Limit limit) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Device deviceOrphanCheck = limit.getDevice();
        if (deviceOrphanCheck != null) {
            Limit oldLimitOfDevice = deviceOrphanCheck.getLimit();
            if (oldLimitOfDevice != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Device " + deviceOrphanCheck + " already has an item of type Limit whose device column cannot be null. Please make another selection for the device field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Device device = limit.getDevice();
            if (device != null) {
                device = em.getReference(device.getClass(), device.getDeviceId());
                limit.setDevice(device);
            }
            User user = limit.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                limit.setUser(user);
            }
            em.persist(limit);
            if (device != null) {
                device.setLimit(limit);
                device = em.merge(device);
            }
            if (user != null) {
                user.getLimitList().add(limit);
                user = em.merge(user);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Limit limit) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Limit persistentLimit = em.find(Limit.class, limit.getLimitId());
            Device deviceOld = persistentLimit.getDevice();
            Device deviceNew = limit.getDevice();
            User userOld = persistentLimit.getUser();
            User userNew = limit.getUser();
            List<String> illegalOrphanMessages = null;
            if (deviceNew != null && !deviceNew.equals(deviceOld)) {
                Limit oldLimitOfDevice = deviceNew.getLimit();
                if (oldLimitOfDevice != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Device " + deviceNew + " already has an item of type Limit whose device column cannot be null. Please make another selection for the device field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (deviceNew != null) {
                deviceNew = em.getReference(deviceNew.getClass(), deviceNew.getDeviceId());
                limit.setDevice(deviceNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                limit.setUser(userNew);
            }
            limit = em.merge(limit);
            if (deviceOld != null && !deviceOld.equals(deviceNew)) {
                deviceOld.setLimit(null);
                deviceOld = em.merge(deviceOld);
            }
            if (deviceNew != null && !deviceNew.equals(deviceOld)) {
                deviceNew.setLimit(limit);
                deviceNew = em.merge(deviceNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getLimitList().remove(limit);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getLimitList().add(limit);
                userNew = em.merge(userNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = limit.getLimitId();
                if (findLimit(id) == null) {
                    throw new NonexistentEntityException("The limit with id " + id + " no longer exists.");
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
            Limit limit;
            try {
                limit = em.getReference(Limit.class, id);
                limit.getLimitId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The limit with id " + id + " no longer exists.", enfe);
            }
            Device device = limit.getDevice();
            if (device != null) {
                device.setLimit(null);
                device = em.merge(device);
            }
            User user = limit.getUser();
            if (user != null) {
                user.getLimitList().remove(limit);
                user = em.merge(user);
            }
            em.remove(limit);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Limit> findLimitEntities() {
        return findLimitEntities(true, -1, -1);
    }

    public List<Limit> findLimitEntities(int maxResults, int firstResult) {
        return findLimitEntities(false, maxResults, firstResult);
    }

    private List<Limit> findLimitEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Limit.class));
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

    public Limit findLimit(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Limit.class, id);
        } finally {
            em.close();
        }
    }

    public int getLimitCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Limit> rt = cq.from(Limit.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
