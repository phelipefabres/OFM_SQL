/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.entities;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

/**
 *
 * @author joshua
 */
@Entity
@Table(catalog = "ofm", schema = "", uniqueConstraints = {
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
public class Limit implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer limitId;

    private ObjectProperty<Float> insertionGreen;
    private ObjectProperty<Float> reflectionGreen;
    private ObjectProperty<Float> distanceGreen;
    private ObjectProperty<Float> distanceYellow;
    private ObjectProperty<Float> insertionYellow;
    private ObjectProperty<Float> reflectionYellow;
    private ObjectProperty<Float> attenuationGreen;
    private ObjectProperty<Float> attenuationYellow;
    private ObjectProperty<Float> acumulationYellow;
    private ObjectProperty<Float> acumulationGreen;
    private ObjectProperty<Integer> eventsNumber;

    private Date createTime;

    private Date updateTime;

    private Device device;

    private User user;

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
        this.createTime = createTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "limit_id", nullable = false)
    public Integer getLimitId() {
        return limitId;
    }

    public void setLimitId(Integer limitId) {
        this.limitId = limitId;
    }

    @Basic(optional = false)
    @Column(name = "insertion_green", nullable = false)
    public Float getInsertionGreen() {
        return insertionGreen.get();
    }

    public void setInsertionGreen(float insertionGreen) {
        this.insertionGreen.set(insertionGreen);
    }

    @Basic(optional = false)
    @Column(name = "events_number", nullable = false)
    public Integer getEventsNumber() {
        return eventsNumber.get();
    }

    public void setEventsNumber(int eventsNumber) {
        this.eventsNumber.set(eventsNumber);
    }

    @Basic(optional = false)
    @Column(name = "reflection_green", nullable = false)
    public Float getReflectionGreen() {
        return reflectionGreen.get();
    }

    public void setReflectionGreen(float reflectionGreen) {
        this.reflectionGreen.set(reflectionGreen);
    }

    @Basic(optional = false)
    @Column(name = "distance_green")
    public Float getDistanceGreen() {
        return distanceGreen.get();
    }

    public void setDistanceGreen(float distanceGreen) {
        this.distanceGreen.set(distanceGreen);
    }

    @Basic(optional = false)
    @Column(name = "distance_yellow")
    public Float getDistanceYellow() {
        return distanceYellow.get();
    }

    public void setDistanceYellow(float distanceYellow) {
        this.distanceYellow.set(distanceYellow);
    }

    @Basic(optional = false)
    @Column(name = "insertion_yellow")
    public Float getInsertionYellow() {
        return insertionYellow.get();
    }

    public void setInsertionYellow(float insertionYellow) {
        this.insertionYellow.set(insertionYellow);
    }

    @Basic(optional = false)
    @Column(name = "reflection_yellow")
    public Float getReflectionYellow() {
        return reflectionYellow.get();
    }

    public void setReflectionYellow(float reflectionYellow) {
        this.reflectionYellow.set(reflectionYellow);
    }

    @Basic(optional = false)
    @Column(name = "attenuation_green")
    public Float getAttenuationGreen() {
        return attenuationGreen.get();
    }

    public void setAttenuationGreen(float attenuationGreen) {
        this.attenuationGreen.set(attenuationGreen);
    }

    @Basic(optional = false)
    @Column(name = "attenuation_yellow")
    public Float getAttenuationYellow() {
        return attenuationYellow.get();
    }

    public void setAttenuationYellow(float attenuationYellow) {
        this.attenuationYellow.set(attenuationYellow);
    }

    @Basic(optional = false)
    @Column(name = "acumulation_yellow")
    public Float getAcumulationYellow() {
        return acumulationYellow.get();
    }

    public void setAcumulationYellow(float acumulationYellow) {
        this.acumulationYellow.set(acumulationYellow);
    }

    @Basic(optional = false)
    @Column(name = "acumulation_green")
    public Float getAcumulationGreen() {
        return acumulationGreen.get();
    }

    public void setAcumulationGreen(float acumulationGreen) {
        this.acumulationGreen.set(acumulationGreen);
    }

    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (limitId != null ? limitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Limit)) {
            return false;
        }
        Limit other = (Limit) object;
        if ((this.limitId == null && other.limitId != null) || (this.limitId != null && !this.limitId.equals(other.limitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.net.multiway.ofm.entities.Limit[ limitId=" + limitId + " ]";
    }

}
