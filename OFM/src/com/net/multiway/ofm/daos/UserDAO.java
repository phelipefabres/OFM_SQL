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
import com.net.multiway.ofm.entities.Data;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import com.net.multiway.ofm.daos.exceptions.IllegalOrphanException;
import com.net.multiway.ofm.daos.exceptions.NonexistentEntityException;
import com.net.multiway.ofm.database.Database;
import com.net.multiway.ofm.entities.Parameter;
import com.net.multiway.ofm.entities.Limit;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.User;

/**
 *
 * @author joshua
 */
public class UserDAO extends DAO implements Serializable {
  
    public void create(User user) {
        if (user.getDataList() == null) {
            user.setDataList(new ArrayList<Data>());
        }
        if (user.getParameterList() == null) {
            user.setParameterList(new ArrayList<Parameter>());
        }
        if (user.getLimitList() == null) {
            user.setLimitList(new ArrayList<Limit>());
        }
        if (user.getDeviceList() == null) {
            user.setDeviceList(new ArrayList<Device>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Data> attachedDataList = new ArrayList<Data>();
            for (Data dataListDataToAttach : user.getDataList()) {
                dataListDataToAttach = em.getReference(dataListDataToAttach.getClass(), dataListDataToAttach.getDataId());
                attachedDataList.add(dataListDataToAttach);
            }
            user.setDataList(attachedDataList);
            List<Parameter> attachedParameterList = new ArrayList<Parameter>();
            for (Parameter parameterListParameterToAttach : user.getParameterList()) {
                parameterListParameterToAttach = em.getReference(parameterListParameterToAttach.getClass(), parameterListParameterToAttach.getParameterId());
                attachedParameterList.add(parameterListParameterToAttach);
            }
            user.setParameterList(attachedParameterList);
            List<Limit> attachedLimitList = new ArrayList<Limit>();
            for (Limit limitListLimitToAttach : user.getLimitList()) {
                limitListLimitToAttach = em.getReference(limitListLimitToAttach.getClass(), limitListLimitToAttach.getLimitId());
                attachedLimitList.add(limitListLimitToAttach);
            }
            user.setLimitList(attachedLimitList);
            List<Device> attachedDeviceList = new ArrayList<Device>();
            for (Device deviceListDeviceToAttach : user.getDeviceList()) {
                deviceListDeviceToAttach = em.getReference(deviceListDeviceToAttach.getClass(), deviceListDeviceToAttach.getDeviceId());
                attachedDeviceList.add(deviceListDeviceToAttach);
            }
            user.setDeviceList(attachedDeviceList);
            em.persist(user);
            for (Data dataListData : user.getDataList()) {
                User oldUserOfDataListData = dataListData.getUser();
                dataListData.setUser(user);
                dataListData = em.merge(dataListData);
                if (oldUserOfDataListData != null) {
                    oldUserOfDataListData.getDataList().remove(dataListData);
                    oldUserOfDataListData = em.merge(oldUserOfDataListData);
                }
            }
            for (Parameter parameterListParameter : user.getParameterList()) {
                User oldUserOfParameterListParameter = parameterListParameter.getUser();
                parameterListParameter.setUser(user);
                parameterListParameter = em.merge(parameterListParameter);
                if (oldUserOfParameterListParameter != null) {
                    oldUserOfParameterListParameter.getParameterList().remove(parameterListParameter);
                    oldUserOfParameterListParameter = em.merge(oldUserOfParameterListParameter);
                }
            }
            for (Limit limitListLimit : user.getLimitList()) {
                User oldUserOfLimitListLimit = limitListLimit.getUser();
                limitListLimit.setUser(user);
                limitListLimit = em.merge(limitListLimit);
                if (oldUserOfLimitListLimit != null) {
                    oldUserOfLimitListLimit.getLimitList().remove(limitListLimit);
                    oldUserOfLimitListLimit = em.merge(oldUserOfLimitListLimit);
                }
            }
            for (Device deviceListDevice : user.getDeviceList()) {
                User oldUserOfDeviceListDevice = deviceListDevice.getUser();
                deviceListDevice.setUser(user);
                deviceListDevice = em.merge(deviceListDevice);
                if (oldUserOfDeviceListDevice != null) {
                    oldUserOfDeviceListDevice.getDeviceList().remove(deviceListDevice);
                    oldUserOfDeviceListDevice = em.merge(oldUserOfDeviceListDevice);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getUserId());
            List<Data> dataListOld = persistentUser.getDataList();
            List<Data> dataListNew = user.getDataList();
            List<Parameter> parameterListOld = persistentUser.getParameterList();
            List<Parameter> parameterListNew = user.getParameterList();
            List<Limit> limitListOld = persistentUser.getLimitList();
            List<Limit> limitListNew = user.getLimitList();
            List<Device> deviceListOld = persistentUser.getDeviceList();
            List<Device> deviceListNew = user.getDeviceList();
            List<String> illegalOrphanMessages = null;
            for (Data dataListOldData : dataListOld) {
                if (!dataListNew.contains(dataListOldData)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Data " + dataListOldData + " since its user field is not nullable.");
                }
            }
            for (Parameter parameterListOldParameter : parameterListOld) {
                if (!parameterListNew.contains(parameterListOldParameter)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Parameter " + parameterListOldParameter + " since its user field is not nullable.");
                }
            }
            for (Limit limitListOldLimit : limitListOld) {
                if (!limitListNew.contains(limitListOldLimit)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Limit " + limitListOldLimit + " since its user field is not nullable.");
                }
            }
            for (Device deviceListOldDevice : deviceListOld) {
                if (!deviceListNew.contains(deviceListOldDevice)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Device " + deviceListOldDevice + " since its user field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Data> attachedDataListNew = new ArrayList<Data>();
            for (Data dataListNewDataToAttach : dataListNew) {
                dataListNewDataToAttach = em.getReference(dataListNewDataToAttach.getClass(), dataListNewDataToAttach.getDataId());
                attachedDataListNew.add(dataListNewDataToAttach);
            }
            dataListNew = attachedDataListNew;
            user.setDataList(dataListNew);
            List<Parameter> attachedParameterListNew = new ArrayList<Parameter>();
            for (Parameter parameterListNewParameterToAttach : parameterListNew) {
                parameterListNewParameterToAttach = em.getReference(parameterListNewParameterToAttach.getClass(), parameterListNewParameterToAttach.getParameterId());
                attachedParameterListNew.add(parameterListNewParameterToAttach);
            }
            parameterListNew = attachedParameterListNew;
            user.setParameterList(parameterListNew);
            List<Limit> attachedLimitListNew = new ArrayList<Limit>();
            for (Limit limitListNewLimitToAttach : limitListNew) {
                limitListNewLimitToAttach = em.getReference(limitListNewLimitToAttach.getClass(), limitListNewLimitToAttach.getLimitId());
                attachedLimitListNew.add(limitListNewLimitToAttach);
            }
            limitListNew = attachedLimitListNew;
            user.setLimitList(limitListNew);
            List<Device> attachedDeviceListNew = new ArrayList<Device>();
            for (Device deviceListNewDeviceToAttach : deviceListNew) {
                deviceListNewDeviceToAttach = em.getReference(deviceListNewDeviceToAttach.getClass(), deviceListNewDeviceToAttach.getDeviceId());
                attachedDeviceListNew.add(deviceListNewDeviceToAttach);
            }
            deviceListNew = attachedDeviceListNew;
            user.setDeviceList(deviceListNew);
            user = em.merge(user);
            for (Data dataListNewData : dataListNew) {
                if (!dataListOld.contains(dataListNewData)) {
                    User oldUserOfDataListNewData = dataListNewData.getUser();
                    dataListNewData.setUser(user);
                    dataListNewData = em.merge(dataListNewData);
                    if (oldUserOfDataListNewData != null && !oldUserOfDataListNewData.equals(user)) {
                        oldUserOfDataListNewData.getDataList().remove(dataListNewData);
                        oldUserOfDataListNewData = em.merge(oldUserOfDataListNewData);
                    }
                }
            }
            for (Parameter parameterListNewParameter : parameterListNew) {
                if (!parameterListOld.contains(parameterListNewParameter)) {
                    User oldUserOfParameterListNewParameter = parameterListNewParameter.getUser();
                    parameterListNewParameter.setUser(user);
                    parameterListNewParameter = em.merge(parameterListNewParameter);
                    if (oldUserOfParameterListNewParameter != null && !oldUserOfParameterListNewParameter.equals(user)) {
                        oldUserOfParameterListNewParameter.getParameterList().remove(parameterListNewParameter);
                        oldUserOfParameterListNewParameter = em.merge(oldUserOfParameterListNewParameter);
                    }
                }
            }
            for (Limit limitListNewLimit : limitListNew) {
                if (!limitListOld.contains(limitListNewLimit)) {
                    User oldUserOfLimitListNewLimit = limitListNewLimit.getUser();
                    limitListNewLimit.setUser(user);
                    limitListNewLimit = em.merge(limitListNewLimit);
                    if (oldUserOfLimitListNewLimit != null && !oldUserOfLimitListNewLimit.equals(user)) {
                        oldUserOfLimitListNewLimit.getLimitList().remove(limitListNewLimit);
                        oldUserOfLimitListNewLimit = em.merge(oldUserOfLimitListNewLimit);
                    }
                }
            }
            for (Device deviceListNewDevice : deviceListNew) {
                if (!deviceListOld.contains(deviceListNewDevice)) {
                    User oldUserOfDeviceListNewDevice = deviceListNewDevice.getUser();
                    deviceListNewDevice.setUser(user);
                    deviceListNewDevice = em.merge(deviceListNewDevice);
                    if (oldUserOfDeviceListNewDevice != null && !oldUserOfDeviceListNewDevice.equals(user)) {
                        oldUserOfDeviceListNewDevice.getDeviceList().remove(deviceListNewDevice);
                        oldUserOfDeviceListNewDevice = em.merge(oldUserOfDeviceListNewDevice);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getUserId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Data> dataListOrphanCheck = user.getDataList();
            for (Data dataListOrphanCheckData : dataListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Data " + dataListOrphanCheckData + " in its dataList field has a non-nullable user field.");
            }
            List<Parameter> parameterListOrphanCheck = user.getParameterList();
            for (Parameter parameterListOrphanCheckParameter : parameterListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Parameter " + parameterListOrphanCheckParameter + " in its parameterList field has a non-nullable user field.");
            }
            List<Limit> limitListOrphanCheck = user.getLimitList();
            for (Limit limitListOrphanCheckLimit : limitListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Limit " + limitListOrphanCheckLimit + " in its limitList field has a non-nullable user field.");
            }
            List<Device> deviceListOrphanCheck = user.getDeviceList();
            for (Device deviceListOrphanCheckDevice : deviceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Device " + deviceListOrphanCheckDevice + " in its deviceList field has a non-nullable user field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    /**
     * Method get users ordered by number
     *
     * @param username
     * @return all user in database with this username
     */
    public List<User> findByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("User.findByUsername");
            query.setParameter("username", username);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
