/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.daos;

import com.net.multiway.ofm.daos.exceptions.IllegalOrphanException;
import com.net.multiway.ofm.daos.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.net.multiway.ofm.entities.Data;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.Parameter;
import com.net.multiway.ofm.entities.Limit;
import com.net.multiway.ofm.entities.User;
import com.net.multiway.ofm.entities.Occurrence;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author joshua
 */
public class DeviceDAO implements Serializable {

    public DeviceDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Device device) {
        if (device.getOccurrenceList() == null) {
            device.setOccurrenceList(new ArrayList<Occurrence>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Data data = device.getData();
            if (data != null) {
                data = em.getReference(data.getClass(), data.getDataId());
                device.setData(data);
            }
            Parameter parameter = device.getParameter();
            if (parameter != null) {
                parameter = em.getReference(parameter.getClass(), parameter.getParameterId());
                device.setParameter(parameter);
            }
            Limit limit = device.getLimit();
            if (limit != null) {
                limit = em.getReference(limit.getClass(), limit.getLimitId());
                device.setLimit(limit);
            }
            User user = device.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                device.setUser(user);
            }
            List<Occurrence> attachedOccurrenceList = new ArrayList<Occurrence>();
            for (Occurrence occurrenceListOccurrenceToAttach : device.getOccurrenceList()) {
                occurrenceListOccurrenceToAttach = em.getReference(occurrenceListOccurrenceToAttach.getClass(), occurrenceListOccurrenceToAttach.getOccurrenceId());
                attachedOccurrenceList.add(occurrenceListOccurrenceToAttach);
            }
            device.setOccurrenceList(attachedOccurrenceList);
            em.persist(device);
            if (data != null) {
                Device oldDeviceOfData = data.getDevice();
                if (oldDeviceOfData != null) {
                    oldDeviceOfData.setData(null);
                    oldDeviceOfData = em.merge(oldDeviceOfData);
                }
                data.setDevice(device);
                data = em.merge(data);
            }
            if (parameter != null) {
                Device oldDeviceOfParameter = parameter.getDevice();
                if (oldDeviceOfParameter != null) {
                    oldDeviceOfParameter.setParameter(null);
                    oldDeviceOfParameter = em.merge(oldDeviceOfParameter);
                }
                parameter.setDevice(device);
                parameter = em.merge(parameter);
            }
            if (limit != null) {
                Device oldDeviceOfLimit = limit.getDevice();
                if (oldDeviceOfLimit != null) {
                    oldDeviceOfLimit.setLimit(null);
                    oldDeviceOfLimit = em.merge(oldDeviceOfLimit);
                }
                limit.setDevice(device);
                limit = em.merge(limit);
            }
            if (user != null) {
                user.getDeviceList().add(device);
                user = em.merge(user);
            }
            for (Occurrence occurrenceListOccurrence : device.getOccurrenceList()) {
                Device oldDeviceOfOccurrenceListOccurrence = occurrenceListOccurrence.getDevice();
                occurrenceListOccurrence.setDevice(device);
                occurrenceListOccurrence = em.merge(occurrenceListOccurrence);
                if (oldDeviceOfOccurrenceListOccurrence != null) {
                    oldDeviceOfOccurrenceListOccurrence.getOccurrenceList().remove(occurrenceListOccurrence);
                    oldDeviceOfOccurrenceListOccurrence = em.merge(oldDeviceOfOccurrenceListOccurrence);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Device device) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Device persistentDevice = em.find(Device.class, device.getDeviceId());
            Data dataOld = persistentDevice.getData();
            Data dataNew = device.getData();
            Parameter parameterOld = persistentDevice.getParameter();
            Parameter parameterNew = device.getParameter();
            Limit limitOld = persistentDevice.getLimit();
            Limit limitNew = device.getLimit();
            User userOld = persistentDevice.getUser();
            User userNew = device.getUser();
            List<Occurrence> occurrenceListOld = persistentDevice.getOccurrenceList();
            List<Occurrence> occurrenceListNew = device.getOccurrenceList();
            List<String> illegalOrphanMessages = null;
            if (dataOld != null && !dataOld.equals(dataNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Data " + dataOld + " since its device field is not nullable.");
            }
            if (parameterOld != null && !parameterOld.equals(parameterNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Parameter " + parameterOld + " since its device field is not nullable.");
            }
            if (limitOld != null && !limitOld.equals(limitNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Limit " + limitOld + " since its device field is not nullable.");
            }
            for (Occurrence occurrenceListOldOccurrence : occurrenceListOld) {
                if (!occurrenceListNew.contains(occurrenceListOldOccurrence)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Occurrence " + occurrenceListOldOccurrence + " since its device field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (dataNew != null) {
                dataNew = em.getReference(dataNew.getClass(), dataNew.getDataId());
                device.setData(dataNew);
            }
            if (parameterNew != null) {
                parameterNew = em.getReference(parameterNew.getClass(), parameterNew.getParameterId());
                device.setParameter(parameterNew);
            }
            if (limitNew != null) {
                limitNew = em.getReference(limitNew.getClass(), limitNew.getLimitId());
                device.setLimit(limitNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                device.setUser(userNew);
            }
            List<Occurrence> attachedOccurrenceListNew = new ArrayList<Occurrence>();
            for (Occurrence occurrenceListNewOccurrenceToAttach : occurrenceListNew) {
                occurrenceListNewOccurrenceToAttach = em.getReference(occurrenceListNewOccurrenceToAttach.getClass(), occurrenceListNewOccurrenceToAttach.getOccurrenceId());
                attachedOccurrenceListNew.add(occurrenceListNewOccurrenceToAttach);
            }
            occurrenceListNew = attachedOccurrenceListNew;
            device.setOccurrenceList(occurrenceListNew);
            device = em.merge(device);
            if (dataNew != null && !dataNew.equals(dataOld)) {
                Device oldDeviceOfData = dataNew.getDevice();
                if (oldDeviceOfData != null) {
                    oldDeviceOfData.setData(null);
                    oldDeviceOfData = em.merge(oldDeviceOfData);
                }
                dataNew.setDevice(device);
                dataNew = em.merge(dataNew);
            }
            if (parameterNew != null && !parameterNew.equals(parameterOld)) {
                Device oldDeviceOfParameter = parameterNew.getDevice();
                if (oldDeviceOfParameter != null) {
                    oldDeviceOfParameter.setParameter(null);
                    oldDeviceOfParameter = em.merge(oldDeviceOfParameter);
                }
                parameterNew.setDevice(device);
                parameterNew = em.merge(parameterNew);
            }
            if (limitNew != null && !limitNew.equals(limitOld)) {
                Device oldDeviceOfLimit = limitNew.getDevice();
                if (oldDeviceOfLimit != null) {
                    oldDeviceOfLimit.setLimit(null);
                    oldDeviceOfLimit = em.merge(oldDeviceOfLimit);
                }
                limitNew.setDevice(device);
                limitNew = em.merge(limitNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getDeviceList().remove(device);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getDeviceList().add(device);
                userNew = em.merge(userNew);
            }
            for (Occurrence occurrenceListNewOccurrence : occurrenceListNew) {
                if (!occurrenceListOld.contains(occurrenceListNewOccurrence)) {
                    Device oldDeviceOfOccurrenceListNewOccurrence = occurrenceListNewOccurrence.getDevice();
                    occurrenceListNewOccurrence.setDevice(device);
                    occurrenceListNewOccurrence = em.merge(occurrenceListNewOccurrence);
                    if (oldDeviceOfOccurrenceListNewOccurrence != null && !oldDeviceOfOccurrenceListNewOccurrence.equals(device)) {
                        oldDeviceOfOccurrenceListNewOccurrence.getOccurrenceList().remove(occurrenceListNewOccurrence);
                        oldDeviceOfOccurrenceListNewOccurrence = em.merge(oldDeviceOfOccurrenceListNewOccurrence);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = device.getDeviceId();
                if (findDevice(id) == null) {
                    throw new NonexistentEntityException("The device with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Device device;
            try {
                device = em.getReference(Device.class, id);
                device.getDeviceId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The device with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Data dataOrphanCheck = device.getData();
            if (dataOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Device (" + device + ") cannot be destroyed since the Data " + dataOrphanCheck + " in its data field has a non-nullable device field.");
            }
            Parameter parameterOrphanCheck = device.getParameter();
            if (parameterOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Device (" + device + ") cannot be destroyed since the Parameter " + parameterOrphanCheck + " in its parameter field has a non-nullable device field.");
            }
            Limit limitOrphanCheck = device.getLimit();
            if (limitOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Device (" + device + ") cannot be destroyed since the Limit " + limitOrphanCheck + " in its limit field has a non-nullable device field.");
            }
            List<Occurrence> occurrenceListOrphanCheck = device.getOccurrenceList();
            for (Occurrence occurrenceListOrphanCheckOccurrence : occurrenceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Device (" + device + ") cannot be destroyed since the Occurrence " + occurrenceListOrphanCheckOccurrence + " in its occurrenceList field has a non-nullable device field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User user = device.getUser();
            if (user != null) {
                user.getDeviceList().remove(device);
                user = em.merge(user);
            }
            em.remove(device);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Device> findDeviceEntities() {
        return findDeviceEntities(true, -1, -1);
    }

    public List<Device> findDeviceEntities(int maxResults, int firstResult) {
        return findDeviceEntities(false, maxResults, firstResult);
    }

    private List<Device> findDeviceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Device.class));
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

    public Device findDevice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Device.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeviceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Device> rt = cq.from(Device.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
