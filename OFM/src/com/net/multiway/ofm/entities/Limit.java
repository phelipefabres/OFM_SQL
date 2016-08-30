/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.entities;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author joshua
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "limits", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"device_id"})})
@NamedQueries({
    @NamedQuery(name = "Limit.findAll", query = "SELECT l FROM Limit l"),
    @NamedQuery(name = "Limit.findByLimitId", query = "SELECT l FROM Limit l WHERE l.limitId = :limitId"),
    @NamedQuery(name = "Limit.findByInsertionGreen", query = "SELECT l FROM Limit l WHERE l.insertionGreen = :insertionGreen"),
    @NamedQuery(name = "Limit.findByReflectionGreen", query = "SELECT l FROM Limit l WHERE l.reflectionGreen = :reflectionGreen"),
    @NamedQuery(name = "Limit.findByDistanceGreen", query = "SELECT l FROM Limit l WHERE l.distanceGreen = :distanceGreen"),
    @NamedQuery(name = "Limit.findByAttenuationGreen", query = "SELECT l FROM Limit l WHERE l.attenuationGreen = :attenuationGreen"),
    @NamedQuery(name = "Limit.findByAcumulationGreen", query = "SELECT l FROM Limit l WHERE l.acumulationGreen = :acumulationGreen"),
    @NamedQuery(name = "Limit.findByInsertionYellow", query = "SELECT l FROM Limit l WHERE l.insertionYellow = :insertionYellow"),
    @NamedQuery(name = "Limit.findByReflectionYellow", query = "SELECT l FROM Limit l WHERE l.reflectionYellow = :reflectionYellow"),
    @NamedQuery(name = "Limit.findByDistanceYellow", query = "SELECT l FROM Limit l WHERE l.distanceYellow = :distanceYellow"),
    @NamedQuery(name = "Limit.findByAttenuationYellow", query = "SELECT l FROM Limit l WHERE l.attenuationYellow = :attenuationYellow"),
    @NamedQuery(name = "Limit.findByAcumulationYellow", query = "SELECT l FROM Limit l WHERE l.acumulationYellow = :acumulationYellow"),
    @NamedQuery(name = "Limit.findByCreateTime", query = "SELECT l FROM Limit l WHERE l.createTime = :createTime"),
    @NamedQuery(name = "Limit.findByUpdateTime", query = "SELECT l FROM Limit l WHERE l.updateTime = :updateTime")})
public class Limit implements Externalizable {

    private static final long serialVersionUID = 1L;

    public Limit() {
        this.insertionGreen = new SimpleObjectProperty<>();
        this.reflectionGreen = new SimpleObjectProperty<>();
        this.distanceGreen = new SimpleObjectProperty<>();
        this.distanceYellow = new SimpleObjectProperty<>();
        this.insertionYellow = new SimpleObjectProperty<>();
        this.reflectionYellow = new SimpleObjectProperty<>();
        this.attenuationGreen = new SimpleObjectProperty<>();
        this.attenuationYellow = new SimpleObjectProperty<>();
        this.acumulationYellow = new SimpleObjectProperty<>();
        this.acumulationGreen = new SimpleObjectProperty<>();
        this.createTime = new SimpleObjectProperty<>();

    }

    public Limit(Integer limitId, Float insertionGreen, Float reflectionGreen, Float distanceGreen, Float attenuationGreen, Float acumulationGreen, Float insertionYellow, Float reflectionYellow, Float distanceYellow, Float attenuationYellow, Float acumulationYellow, Date createTime) {
        this.limitId = new SimpleObjectProperty<>(limitId);
        this.insertionGreen = new SimpleObjectProperty<>(insertionGreen);
        this.reflectionGreen = new SimpleObjectProperty<>(reflectionGreen);
        this.distanceGreen = new SimpleObjectProperty<>(distanceGreen);
        this.distanceYellow = new SimpleObjectProperty<>(distanceYellow);
        this.insertionYellow = new SimpleObjectProperty<>(insertionYellow);
        this.reflectionYellow = new SimpleObjectProperty<>(reflectionYellow);
        this.attenuationGreen = new SimpleObjectProperty<>(attenuationGreen);
        this.attenuationYellow = new SimpleObjectProperty<>(attenuationYellow);
        this.acumulationYellow = new SimpleObjectProperty<>(acumulationYellow);
        this.acumulationGreen = new SimpleObjectProperty<>(acumulationGreen);
        this.createTime = new SimpleObjectProperty<>(createTime);
        this._limitId = limitId;
        this._insertionGreen = insertionGreen;
        this._reflectionGreen = reflectionGreen;
        this._distanceGreen = distanceGreen;
        this._attenuationGreen = attenuationGreen;
        this._acumulationGreen = acumulationGreen;
        this._insertionYellow = insertionYellow;
        this._reflectionYellow = reflectionYellow;
        this._distanceYellow = distanceYellow;
        this._attenuationYellow = attenuationYellow;
        this._acumulationYellow = acumulationYellow;
        this._createTime = createTime;
    }

    public Limit(float insertionGreen, float reflectionGreen, float distanceGreen,
            float distanceYellow, float insertionYellow, float reflectionYellow, float attenuationGreen,
            float attenuationYellow, float acumulationYellow, float acumulationGreen, Date createTime) {
        this.insertionGreen = new SimpleObjectProperty<>(insertionGreen);
        this.reflectionGreen = new SimpleObjectProperty<>(reflectionGreen);
        this.distanceGreen = new SimpleObjectProperty<>(distanceGreen);
        this.distanceYellow = new SimpleObjectProperty<>(distanceYellow);
        this.insertionYellow = new SimpleObjectProperty<>(insertionYellow);
        this.reflectionYellow = new SimpleObjectProperty<>(reflectionYellow);
        this.attenuationGreen = new SimpleObjectProperty<>(attenuationGreen);
        this.attenuationYellow = new SimpleObjectProperty<>(attenuationYellow);
        this.acumulationYellow = new SimpleObjectProperty<>(acumulationYellow);
        this.acumulationGreen = new SimpleObjectProperty<>(acumulationGreen);
        this.createTime = new SimpleObjectProperty<>(createTime);
        this._insertionGreen = insertionGreen;
        this._reflectionGreen = reflectionGreen;
        this._distanceGreen = distanceGreen;
        this._attenuationGreen = attenuationGreen;
        this._acumulationGreen = acumulationGreen;
        this._insertionYellow = insertionYellow;
        this._reflectionYellow = reflectionYellow;
        this._distanceYellow = distanceYellow;
        this._attenuationYellow = attenuationYellow;
        this._acumulationYellow = acumulationYellow;
        this._createTime = createTime;
    }

    private ObjectProperty<Integer> limitId;
    private Integer _limitId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "limit_id", nullable = false)
    public Integer getLimitId() {
        if (limitId == null) {
            return _limitId;
        } else {
            return limitId.get();
        }
    }

    public void setLimitId(Integer limitId) {
        if (this.limitId == null) {
            this._limitId = limitId;
        } else {
            this.limitId.set(limitId);
        }
    }

    public ObjectProperty<Integer> limitIdProperty() {
        if (limitId == null) {
            limitId = new SimpleObjectProperty<>(_limitId);
        }
        return limitId;
    }

    private ObjectProperty<Float> insertionGreen;
    private Float _insertionGreen;

    @Basic(optional = false)
    @Column(name = "insertion_green", nullable = false)
    public Float getInsertionGreen() {
        if (insertionGreen == null) {
            return _insertionGreen;
        } else {
            return insertionGreen.get();
        }
    }

    public void setInsertionGreen(Float insertionGreen) {
        if (this.insertionGreen == null) {
            this._insertionGreen = insertionGreen;
        } else {
            this.insertionGreen.set(insertionGreen);
        }
    }

    public ObjectProperty<Float> insertionGreenProperty() {
        if (insertionGreen == null) {
            insertionGreen = new SimpleObjectProperty<>(_insertionGreen);
        }
        return insertionGreen;
    }

    private ObjectProperty<Float> reflectionGreen;
    private Float _reflectionGreen;

    @Basic(optional = false)
    @Column(name = "reflection_green", nullable = false)
    public Float getReflectionGreen() {
        if (reflectionGreen == null) {
            return _reflectionGreen;
        } else {
            return reflectionGreen.get();
        }
    }

    public void setReflectionGreen(Float reflectionGreen) {
        if (this.reflectionGreen == null) {
            this._reflectionGreen = reflectionGreen;
        } else {
            this.reflectionGreen.set(reflectionGreen);
        }
    }

    public ObjectProperty<Float> reflectionGreenProperty() {
        if (reflectionGreen == null) {
            reflectionGreen = new SimpleObjectProperty<>(_reflectionGreen);
        }
        return reflectionGreen;
    }

    private ObjectProperty<Float> distanceGreen;
    private Float _distanceGreen;

    @Basic(optional = false)
    @Column(name = "distance_green", nullable = false)
    public Float getDistanceGreen() {
        if (distanceGreen == null) {
            return _distanceGreen;
        } else {
            return distanceGreen.get();
        }
    }

    public void setDistanceGreen(Float distanceGreen) {
        if (this.distanceGreen == null) {
            this._distanceGreen = distanceGreen;
        } else {
            this.distanceGreen.set(distanceGreen);
        }
    }

    public ObjectProperty<Float> distanceGreenProperty() {
        if (distanceGreen == null) {
            distanceGreen = new SimpleObjectProperty<>(_distanceGreen);
        }
        return distanceGreen;
    }

    private ObjectProperty<Float> attenuationGreen;
    private Float _attenuationGreen;

    @Basic(optional = false)
    @Column(name = "attenuation_green", nullable = false)
    public Float getAttenuationGreen() {
        if (attenuationGreen == null) {
            return _attenuationGreen;
        } else {
            return attenuationGreen.get();
        }
    }

    public void setAttenuationGreen(Float attenuationGreen) {
        if (this.attenuationGreen == null) {
            this._attenuationGreen = attenuationGreen;
        } else {
            this.attenuationGreen.set(attenuationGreen);
        }
    }

    public ObjectProperty<Float> attenuationGreenProperty() {
        if (attenuationGreen == null) {
            attenuationGreen = new SimpleObjectProperty<>(_attenuationGreen);
        }
        return attenuationGreen;
    }

    private ObjectProperty<Float> acumulationGreen;
    private Float _acumulationGreen;

    @Basic(optional = false)
    @Column(name = "acumulation_green", nullable = false)
    public Float getAcumulationGreen() {
        if (acumulationGreen == null) {
            return _acumulationGreen;
        } else {
            return acumulationGreen.get();
        }
    }

    public void setAcumulationGreen(Float acumulationGreen) {
        if (this.acumulationGreen == null) {
            this._acumulationGreen = acumulationGreen;
        } else {
            this.acumulationGreen.set(acumulationGreen);
        }
    }

    public ObjectProperty<Float> acumulationGreenProperty() {
        if (acumulationGreen == null) {
            acumulationGreen = new SimpleObjectProperty<>(_acumulationGreen);
        }
        return acumulationGreen;
    }

    private ObjectProperty<Float> insertionYellow;
    private Float _insertionYellow;

    @Basic(optional = false)
    @Column(name = "insertion_yellow", nullable = false)
    public Float getInsertionYellow() {
        if (insertionYellow == null) {
            return _insertionYellow;
        } else {
            return insertionYellow.get();
        }
    }

    public void setInsertionYellow(Float insertionYellow) {
        if (this.insertionYellow == null) {
            this._insertionYellow = insertionYellow;
        } else {
            this.insertionYellow.set(insertionYellow);
        }
    }

    public ObjectProperty<Float> insertionYellowProperty() {
        if (insertionYellow == null) {
            insertionYellow = new SimpleObjectProperty<>(_insertionYellow);
        }
        return insertionYellow;
    }

    private ObjectProperty<Float> reflectionYellow;
    private Float _reflectionYellow;

    @Basic(optional = false)
    @Column(name = "reflection_yellow", nullable = false)
    public Float getReflectionYellow() {
        if (reflectionYellow == null) {
            return _reflectionYellow;
        } else {
            return reflectionYellow.get();
        }
    }

    public void setReflectionYellow(Float reflectionYellow) {
        if (this.reflectionYellow == null) {
            this._reflectionYellow = reflectionYellow;
        } else {
            this.reflectionYellow.set(reflectionYellow);
        }
    }

    public ObjectProperty<Float> reflectionYellowProperty() {
        if (reflectionYellow == null) {
            reflectionYellow = new SimpleObjectProperty<>(_reflectionYellow);
        }
        return reflectionYellow;
    }

    private ObjectProperty<Float> distanceYellow;
    private Float _distanceYellow;

    @Basic(optional = false)
    @Column(name = "distance_yellow", nullable = false)
    public Float getDistanceYellow() {
        if (distanceYellow == null) {
            return _distanceYellow;
        } else {
            return distanceYellow.get();
        }
    }

    public void setDistanceYellow(Float distanceYellow) {
        if (this.distanceYellow == null) {
            this._distanceYellow = distanceYellow;
        } else {
            this.distanceYellow.set(distanceYellow);
        }
    }

    public ObjectProperty<Float> distanceYellowProperty() {
        if (distanceYellow == null) {
            distanceYellow = new SimpleObjectProperty<>(_distanceYellow);
        }
        return distanceYellow;
    }

    private ObjectProperty<Float> attenuationYellow;
    private Float _attenuationYellow;

    @Basic(optional = false)
    @Column(name = "attenuation_yellow", nullable = false)
    public Float getAttenuationYellow() {
        if (attenuationYellow == null) {
            return _attenuationYellow;
        } else {
            return attenuationYellow.get();
        }
    }

    public void setAttenuationYellow(Float attenuationYellow) {
        if (this.attenuationYellow == null) {
            this._attenuationYellow = attenuationYellow;
        } else {
            this.attenuationYellow.set(attenuationYellow);
        }
    }

    public ObjectProperty<Float> attenuationYellowProperty() {
        if (attenuationYellow == null) {
            attenuationYellow = new SimpleObjectProperty<>(_attenuationYellow);
        }
        return attenuationYellow;
    }

    private ObjectProperty<Float> acumulationYellow;
    private Float _acumulationYellow;

    @Basic(optional = false)
    @Column(name = "acumulation_yellow", nullable = false)
    public Float getAcumulationYellow() {
        if (acumulationYellow == null) {
            return _acumulationYellow;
        } else {
            return acumulationYellow.get();
        }
    }

    public void setAcumulationYellow(Float acumulationYellow) {
        if (this.acumulationYellow == null) {
            this._acumulationYellow = acumulationYellow;
        } else {
            this.acumulationYellow.set(acumulationYellow);
        }
    }

    public ObjectProperty<Float> acumulationYellowProperty() {
        if (acumulationYellow == null) {
            acumulationYellow = new SimpleObjectProperty<>(_acumulationYellow);
        }
        return acumulationYellow;
    }

    private ObjectProperty<Date> createTime;
    private Date _createTime;

    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        if (createTime == null) {
            return _createTime;
        } else {
            return createTime.get();
        }
    }

    public void setCreateTime(Date createTime) {
        if (this.createTime == null) {
            this._createTime = createTime;
        } else {
            this.createTime.set(createTime);
        }
    }

    public ObjectProperty<Date> createTimeProperty() {
        if (createTime == null) {
            createTime = new SimpleObjectProperty<>(this, "createTime", _createTime);
        }
        return createTime;
    }

    private ObjectProperty<Date> updateTime;
    private Date _updateTime;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        if (updateTime == null) {
            return _updateTime;
        } else {
            return updateTime.get();
        }
    }

    public void setUpdateTime(Date updateTime) {
        if (this.updateTime == null) {
            this._updateTime = updateTime;
        } else {
            this.updateTime.set(updateTime);
        }
    }

    public ObjectProperty<Date> updateTimeProperty() {
        if (updateTime == null) {
            updateTime = new SimpleObjectProperty<>(this, "updateTime", _updateTime);
        }
        return updateTime;
    }

    private ObjectProperty<Device> device;
    private Device _device;

    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    @OneToOne(optional = false)
    public Device getDevice() {
        if (device == null) {
            return _device;
        } else {
            return device.get();
        }
    }

    public void setDevice(Device device) {
        if (this.device == null) {
            this._device = device;
        } else {
            this.device.set(device);
        }
    }

    public ObjectProperty<Device> deviceProperty() {
        if (device == null) {
            device = new SimpleObjectProperty<>(this, "device", _device);
        }
        return device;
    }

    private ObjectProperty<User> user;
    private User _user;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public User getUser() {
        if (user == null) {
            return _user;
        } else {
            return user.get();
        }
    }

    public void setUser(User user) {
        if (this.user == null) {
            this._user = user;
        } else {
            this.user.set(user);
        }
    }

    public ObjectProperty<User> userProperty() {
        if (user == null) {
            user = new SimpleObjectProperty<>(this, "user", _user);
        }
        return user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (_limitId != null ? _limitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Limit)) {
            return false;
        }
        Limit other = (Limit) object;
        if ((this._limitId == null && other._limitId != null) || (this._limitId != null && !this._limitId.equals(other._limitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ofm.model.entities.Limit[ limitId=" + _limitId + " - device=" + _device.toString() + " ]";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getLimitId());
        out.writeObject(getInsertionGreen());
        out.writeObject(getReflectionGreen());
        out.writeObject(getDistanceGreen());
        out.writeObject(getAttenuationGreen());
        out.writeObject(getAcumulationGreen());
        out.writeObject(getInsertionYellow());
        out.writeObject(getReflectionYellow());
        out.writeObject(getDistanceYellow());
        out.writeObject(getAttenuationYellow());
        out.writeObject(getAcumulationYellow());
        out.writeObject(getCreateTime());
        out.writeObject(getUpdateTime());
        out.writeObject(getDevice());
        out.writeObject(getUser());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setLimitId((Integer) in.readObject());
        setInsertionGreen((Float) in.readObject());
        setReflectionGreen((Float) in.readObject());
        setDistanceGreen((Float) in.readObject());
        setAttenuationGreen((Float) in.readObject());
        setAcumulationGreen((Float) in.readObject());
        setInsertionYellow((Float) in.readObject());
        setReflectionYellow((Float) in.readObject());
        setDistanceYellow((Float) in.readObject());
        setAttenuationYellow((Float) in.readObject());
        setAcumulationYellow((Float) in.readObject());
        setCreateTime((Date) in.readObject());
        setUpdateTime((Date) in.readObject());
        setDevice((Device) in.readObject());
        setUser((User) in.readObject());
    }

    public void copy(Limit data) {
        //setLimitId((Integer) in.readObject());
        setInsertionGreen(data.getInsertionGreen());
        setReflectionGreen(data.getReflectionGreen());
        setDistanceGreen(data.getDistanceGreen());
        setAttenuationGreen(data.getAttenuationGreen());
        setAcumulationGreen(data.getAcumulationGreen());
        setInsertionYellow(data.getInsertionYellow());
        setReflectionYellow(data.getReflectionYellow());
        setDistanceYellow(data.getDistanceYellow());
        setAttenuationYellow(data.getAttenuationYellow());
        setAcumulationYellow(data.getAcumulationYellow());
        setCreateTime(data.getCreateTime());
        //setUpdateTime((Date) in.readObject());
//        setDevice(data.getDevice());
//        setUser(data.getUser());
    }

}
