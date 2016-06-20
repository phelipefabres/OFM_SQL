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
import com.net.multiway.ofm.entities.Parameter;

/**
 *
 * @author joshua
 */
public class ParameterDAO extends DAO implements Serializable {
    
    public void create(Parameter parameter) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Device deviceOrphanCheck = parameter.getDevice();
        if (deviceOrphanCheck != null) {
            Parameter oldParameterOfDevice = deviceOrphanCheck.getParameter();
            if (oldParameterOfDevice != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Device " + deviceOrphanCheck + " already has an item of type Parameter whose device column cannot be null. Please make another selection for the device field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Device device = parameter.getDevice();
            if (device != null) {
                device = em.getReference(device.getClass(), device.getDeviceId());
                parameter.setDevice(device);
            }
            User user = parameter.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                parameter.setUser(user);
            }
            em.persist(parameter);
            if (device != null) {
                device.setParameter(parameter);
                device = em.merge(device);
            }
            if (user != null) {
                user.getParameterList().add(parameter);
                user = em.merge(user);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Parameter parameter) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parameter persistentParameter = em.find(Parameter.class, parameter.getParameterId());
            Device deviceOld = persistentParameter.getDevice();
            Device deviceNew = parameter.getDevice();
            User userOld = persistentParameter.getUser();
            User userNew = parameter.getUser();
            List<String> illegalOrphanMessages = null;
            if (deviceNew != null && !deviceNew.equals(deviceOld)) {
                Parameter oldParameterOfDevice = deviceNew.getParameter();
                if (oldParameterOfDevice != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Device " + deviceNew + " already has an item of type Parameter whose device column cannot be null. Please make another selection for the device field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (deviceNew != null) {
                deviceNew = em.getReference(deviceNew.getClass(), deviceNew.getDeviceId());
                parameter.setDevice(deviceNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                parameter.setUser(userNew);
            }
            parameter = em.merge(parameter);
            if (deviceOld != null && !deviceOld.equals(deviceNew)) {
                deviceOld.setParameter(null);
                deviceOld = em.merge(deviceOld);
            }
            if (deviceNew != null && !deviceNew.equals(deviceOld)) {
                deviceNew.setParameter(parameter);
                deviceNew = em.merge(deviceNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getParameterList().remove(parameter);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getParameterList().add(parameter);
                userNew = em.merge(userNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parameter.getParameterId();
                if (findParameter(id) == null) {
                    throw new NonexistentEntityException("The parameter with id " + id + " no longer exists.");
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
            Parameter parameter;
            try {
                parameter = em.getReference(Parameter.class, id);
                parameter.getParameterId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parameter with id " + id + " no longer exists.", enfe);
            }
            Device device = parameter.getDevice();
            if (device != null) {
                device.setParameter(null);
                device = em.merge(device);
            }
            User user = parameter.getUser();
            if (user != null) {
                user.getParameterList().remove(parameter);
                user = em.merge(user);
            }
            em.remove(parameter);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Parameter> findParameterEntities() {
        return findParameterEntities(true, -1, -1);
    }

    public List<Parameter> findParameterEntities(int maxResults, int firstResult) {
        return findParameterEntities(false, maxResults, firstResult);
    }

    private List<Parameter> findParameterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parameter.class));
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

    public Parameter findParameter(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parameter.class, id);
        } finally {
            em.close();
        }
    }

    public int getParameterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parameter> rt = cq.from(Parameter.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
