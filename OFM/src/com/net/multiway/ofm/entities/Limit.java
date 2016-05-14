/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.entities;

import java.io.Serializable;
import java.util.Date;
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
 * @author phelipe
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "limit_id", nullable = false)
    private Integer limitId;
    @Basic(optional = false)
    @Column(name = "insertion_green", nullable = false)
    private float insertionGreen;
    @Basic(optional = false)
    @Column(name = "reflection_green", nullable = false)
    private float reflectionGreen;
    @Basic(optional = false)
    @Column(name = "distance_green", nullable = false)
    private float distanceGreen;
    @Basic(optional = false)
    @Column(name = "attenuation_green", nullable = false)
    private float attenuationGreen;
    @Basic(optional = false)
    @Column(name = "acumulation_green", nullable = false)
    private float acumulationGreen;
    @Basic(optional = false)
    @Column(name = "insertion_yellow", nullable = false)
    private float insertionYellow;
    @Basic(optional = false)
    @Column(name = "reflection_yellow", nullable = false)
    private float reflectionYellow;
    @Basic(optional = false)
    @Column(name = "distance_yellow", nullable = false)
    private float distanceYellow;
    @Basic(optional = false)
    @Column(name = "attenuation_yellow", nullable = false)
    private float attenuationYellow;
    @Basic(optional = false)
    @Column(name = "acumulation_yellow", nullable = false)
    private float acumulationYellow;
    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Device device;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public Limit() {
    }

    public Limit(float insertionGreen, float reflectionGreen, float distanceGreen, float attenuationGreen, float acumulationGreen, float insertionYellow, float reflectionYellow, float distanceYellow, float attenuationYellow, float acumulationYellow, Date createTime) {

        this.insertionGreen = insertionGreen;
        this.reflectionGreen = reflectionGreen;
        this.distanceGreen = distanceGreen;
        this.attenuationGreen = attenuationGreen;
        this.acumulationGreen = acumulationGreen;
        this.insertionYellow = insertionYellow;
        this.reflectionYellow = reflectionYellow;
        this.distanceYellow = distanceYellow;
        this.attenuationYellow = attenuationYellow;
        this.acumulationYellow = acumulationYellow;
        this.createTime = createTime;
    }

    public Integer getLimitId() {
        return limitId;
    }

    public void setLimitId(Integer limitId) {
        this.limitId = limitId;
    }

    public float getInsertionGreen() {
        return insertionGreen;
    }

    public void setInsertionGreen(float insertionGreen) {
        this.insertionGreen = insertionGreen;
    }

    public float getReflectionGreen() {
        return reflectionGreen;
    }

    public void setReflectionGreen(float reflectionGreen) {
        this.reflectionGreen = reflectionGreen;
    }

    public float getDistanceGreen() {
        return distanceGreen;
    }

    public void setDistanceGreen(float distanceGreen) {
        this.distanceGreen = distanceGreen;
    }

    public float getAttenuationGreen() {
        return attenuationGreen;
    }

    public void setAttenuationGreen(float attenuationGreen) {
        this.attenuationGreen = attenuationGreen;
    }

    public float getAcumulationGreen() {
        return acumulationGreen;
    }

    public void setAcumulationGreen(float acumulationGreen) {
        this.acumulationGreen = acumulationGreen;
    }

    public float getInsertionYellow() {
        return insertionYellow;
    }

    public void setInsertionYellow(float insertionYellow) {
        this.insertionYellow = insertionYellow;
    }

    public float getReflectionYellow() {
        return reflectionYellow;
    }

    public void setReflectionYellow(float reflectionYellow) {
        this.reflectionYellow = reflectionYellow;
    }

    public float getDistanceYellow() {
        return distanceYellow;
    }

    public void setDistanceYellow(float distanceYellow) {
        this.distanceYellow = distanceYellow;
    }

    public float getAttenuationYellow() {
        return attenuationYellow;
    }

    public void setAttenuationYellow(float attenuationYellow) {
        this.attenuationYellow = attenuationYellow;
    }

    public float getAcumulationYellow() {
        return acumulationYellow;
    }

    public void setAcumulationYellow(float acumulationYellow) {
        this.acumulationYellow = acumulationYellow;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

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
