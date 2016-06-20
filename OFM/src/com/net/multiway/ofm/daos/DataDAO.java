/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.daos;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.net.multiway.ofm.entities.Device;
import com.net.multiway.ofm.entities.User;
import com.net.multiway.ofm.entities.DataEvent;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import com.net.multiway.ofm.daos.exceptions.IllegalOrphanException;
import com.net.multiway.ofm.daos.exceptions.NonexistentEntityException;
import com.net.multiway.ofm.entities.Data;
import com.net.multiway.ofm.entities.DataGraphic;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author joshua
 */
public class DataDAO extends DAO implements Serializable {

    public void create(Data data) throws IllegalOrphanException {
        if (data.getDataEventList() == null) {
            data.setDataEventList(new ArrayList<DataEvent>());
        }
        if (data.getDataGraphicList() == null) {
            data.setDataGraphicList(new ArrayList<DataGraphic>());
        }
        List<String> illegalOrphanMessages = null;
        Device deviceOrphanCheck = data.getDevice();
        if (deviceOrphanCheck != null) {
            Data oldDataOfDevice = deviceOrphanCheck.getData();
            if (oldDataOfDevice != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Device " + deviceOrphanCheck + " already has an item of type Data whose device column cannot be null. Please make another selection for the device field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Device device = data.getDevice();
            if (device != null) {
                device = em.getReference(device.getClass(), device.getDeviceId());
                data.setDevice(device);
            }
            User user = data.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                data.setUser(user);
            }
            List<DataEvent> attachedDataEventList = new ArrayList<DataEvent>();
            for (DataEvent dataEventListDataEventToAttach : data.getDataEventList()) {
                dataEventListDataEventToAttach = em.getReference(dataEventListDataEventToAttach.getClass(), dataEventListDataEventToAttach.getDataEventId());
                attachedDataEventList.add(dataEventListDataEventToAttach);
            }
            data.setDataEventList(attachedDataEventList);
            List<DataGraphic> attachedDataGraphicList = new ArrayList<DataGraphic>();
            for (DataGraphic dataGraphicListDataGraphicToAttach : data.getDataGraphicList()) {
                dataGraphicListDataGraphicToAttach = em.getReference(dataGraphicListDataGraphicToAttach.getClass(), dataGraphicListDataGraphicToAttach.getDataGraphicId());
                attachedDataGraphicList.add(dataGraphicListDataGraphicToAttach);
            }
            data.setDataGraphicList(attachedDataGraphicList);
            em.persist(data);
            if (device != null) {
                device.setData(data);
                device = em.merge(device);
            }
            if (user != null) {
                user.getDataList().add(data);
                user = em.merge(user);
            }
            for (DataEvent dataEventListDataEvent : data.getDataEventList()) {
                Data oldDataOfDataEventListDataEvent = dataEventListDataEvent.getData();
                dataEventListDataEvent.setData(data);
                dataEventListDataEvent = em.merge(dataEventListDataEvent);
                if (oldDataOfDataEventListDataEvent != null) {
                    oldDataOfDataEventListDataEvent.getDataEventList().remove(dataEventListDataEvent);
                    oldDataOfDataEventListDataEvent = em.merge(oldDataOfDataEventListDataEvent);
                }
            }
            for (DataGraphic dataGraphicListDataGraphic : data.getDataGraphicList()) {
                Data oldDataOfDataGraphicListDataGraphic = dataGraphicListDataGraphic.getData();
                dataGraphicListDataGraphic.setData(data);
                dataGraphicListDataGraphic = em.merge(dataGraphicListDataGraphic);
                if (oldDataOfDataGraphicListDataGraphic != null) {
                    oldDataOfDataGraphicListDataGraphic.getDataGraphicList().remove(dataGraphicListDataGraphic);
                    oldDataOfDataGraphicListDataGraphic = em.merge(oldDataOfDataGraphicListDataGraphic);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Data data) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Data persistentData = em.find(Data.class, data.getDataId());
            Device deviceOld = persistentData.getDevice();
            Device deviceNew = data.getDevice();
            User userOld = persistentData.getUser();
            User userNew = data.getUser();
            List<DataEvent> dataEventListOld = persistentData.getDataEventList();
            List<DataEvent> dataEventListNew = data.getDataEventList();
            List<DataGraphic> dataGraphicListOld = persistentData.getDataGraphicList();
            List<DataGraphic> dataGraphicListNew = data.getDataGraphicList();
            List<String> illegalOrphanMessages = null;
            if (deviceNew != null && !deviceNew.equals(deviceOld)) {
                Data oldDataOfDevice = deviceNew.getData();
                if (oldDataOfDevice != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Device " + deviceNew + " already has an item of type Data whose device column cannot be null. Please make another selection for the device field.");
                }
            }
            for (DataEvent dataEventListOldDataEvent : dataEventListOld) {
                if (!dataEventListNew.contains(dataEventListOldDataEvent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DataEvent " + dataEventListOldDataEvent + " since its data field is not nullable.");
                }
            }
            for (DataGraphic dataGraphicListOldDataGraphic : dataGraphicListOld) {
                if (!dataGraphicListNew.contains(dataGraphicListOldDataGraphic)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DataGraphic " + dataGraphicListOldDataGraphic + " since its data field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (deviceNew != null) {
                deviceNew = em.getReference(deviceNew.getClass(), deviceNew.getDeviceId());
                data.setDevice(deviceNew);
            }
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                data.setUser(userNew);
            }
            List<DataEvent> attachedDataEventListNew = new ArrayList<DataEvent>();
            for (DataEvent dataEventListNewDataEventToAttach : dataEventListNew) {
                dataEventListNewDataEventToAttach = em.getReference(dataEventListNewDataEventToAttach.getClass(), dataEventListNewDataEventToAttach.getDataEventId());
                attachedDataEventListNew.add(dataEventListNewDataEventToAttach);
            }
            dataEventListNew = attachedDataEventListNew;
            data.setDataEventList(dataEventListNew);
            List<DataGraphic> attachedDataGraphicListNew = new ArrayList<DataGraphic>();
            for (DataGraphic dataGraphicListNewDataGraphicToAttach : dataGraphicListNew) {
                dataGraphicListNewDataGraphicToAttach = em.getReference(dataGraphicListNewDataGraphicToAttach.getClass(), dataGraphicListNewDataGraphicToAttach.getDataGraphicId());
                attachedDataGraphicListNew.add(dataGraphicListNewDataGraphicToAttach);
            }
            dataGraphicListNew = attachedDataGraphicListNew;
            data.setDataGraphicList(dataGraphicListNew);
            data = em.merge(data);
            if (deviceOld != null && !deviceOld.equals(deviceNew)) {
                deviceOld.setData(null);
                deviceOld = em.merge(deviceOld);
            }
            if (deviceNew != null && !deviceNew.equals(deviceOld)) {
                deviceNew.setData(data);
                deviceNew = em.merge(deviceNew);
            }
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getDataList().remove(data);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getDataList().add(data);
                userNew = em.merge(userNew);
            }
            for (DataEvent dataEventListNewDataEvent : dataEventListNew) {
                if (!dataEventListOld.contains(dataEventListNewDataEvent)) {
                    Data oldDataOfDataEventListNewDataEvent = dataEventListNewDataEvent.getData();
                    dataEventListNewDataEvent.setData(data);
                    dataEventListNewDataEvent = em.merge(dataEventListNewDataEvent);
                    if (oldDataOfDataEventListNewDataEvent != null && !oldDataOfDataEventListNewDataEvent.equals(data)) {
                        oldDataOfDataEventListNewDataEvent.getDataEventList().remove(dataEventListNewDataEvent);
                        oldDataOfDataEventListNewDataEvent = em.merge(oldDataOfDataEventListNewDataEvent);
                    }
                }
            }
            for (DataGraphic dataGraphicListNewDataGraphic : dataGraphicListNew) {
                if (!dataGraphicListOld.contains(dataGraphicListNewDataGraphic)) {
                    Data oldDataOfDataGraphicListNewDataGraphic = dataGraphicListNewDataGraphic.getData();
                    dataGraphicListNewDataGraphic.setData(data);
                    dataGraphicListNewDataGraphic = em.merge(dataGraphicListNewDataGraphic);
                    if (oldDataOfDataGraphicListNewDataGraphic != null && !oldDataOfDataGraphicListNewDataGraphic.equals(data)) {
                        oldDataOfDataGraphicListNewDataGraphic.getDataGraphicList().remove(dataGraphicListNewDataGraphic);
                        oldDataOfDataGraphicListNewDataGraphic = em.merge(oldDataOfDataGraphicListNewDataGraphic);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = data.getDataId();
                if (findData(id) == null) {
                    throw new NonexistentEntityException("The data with id " + id + " no longer exists.");
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
            Data data;
            try {
                data = em.getReference(Data.class, id);
                data.getDataId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The data with id " + id + " no longer exists.", enfe);
            }
            data.getDataEventList().clear();
            data.getDataGraphicList().clear();
//            List<String> illegalOrphanMessages = null;
//            List<DataEvent> dataEventListOrphanCheck = data.getDataEventList();
//            for (DataEvent dataEventListOrphanCheckDataEvent : dataEventListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Data (" + data + ") cannot be destroyed since the DataEvent " + dataEventListOrphanCheckDataEvent + " in its dataEventList field has a non-nullable data field.");
//            }
//            List<DataGraphic> dataGraphicListOrphanCheck = data.getDataGraphicList();
//            for (DataGraphic dataGraphicListOrphanCheckDataGraphic : dataGraphicListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Data (" + data + ") cannot be destroyed since the DataGraphic " + dataGraphicListOrphanCheckDataGraphic + " in its dataGraphicList field has a non-nullable data field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            Device device = data.getDevice();
            if (device != null) {
                device.setData(null);
                device = em.merge(device);
            }
            User user = data.getUser();
            if (user != null) {
                user.getDataList().remove(data);
                user = em.merge(user);
            }
            em.remove(data);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Data> findDataEntities() {
        return findDataEntities(true, -1, -1);
    }

    public List<Data> findDataEntities(int maxResults, int firstResult) {
        return findDataEntities(false, maxResults, firstResult);
    }

    private List<Data> findDataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Data.class));
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

    public Data findData(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Data.class, id);
        } finally {
            em.close();
        }
    }

    public int getDataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Data> rt = cq.from(Data.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
