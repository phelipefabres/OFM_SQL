/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
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
    @UniqueConstraint(columnNames = {"name"})})
@NamedQueries({
    @NamedQuery(name = "Device.findAll", query = "SELECT d FROM Device d"),
    @NamedQuery(name = "Device.findByDeviceId", query = "SELECT d FROM Device d WHERE d.deviceId = :deviceId"),
    @NamedQuery(name = "Device.findByName", query = "SELECT d FROM Device d WHERE d.name = :name"),
    @NamedQuery(name = "Device.findByIp", query = "SELECT d FROM Device d WHERE d.ip = :ip"),
    @NamedQuery(name = "Device.findByMask", query = "SELECT d FROM Device d WHERE d.mask = :mask"),
    @NamedQuery(name = "Device.findByGateway", query = "SELECT d FROM Device d WHERE d.gateway = :gateway"),
    @NamedQuery(name = "Device.findByCreateTime", query = "SELECT d FROM Device d WHERE d.createTime = :createTime"),
    @NamedQuery(name = "Device.findByUpdateTime", query = "SELECT d FROM Device d WHERE d.updateTime = :updateTime")})
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "device_id", nullable = false)
    private Integer deviceId;
    @Basic(optional = false)
    @Column(nullable = false, length = 63)
    private String name;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String ip;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String mask;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String gateway;
    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "device", fetch = FetchType.LAZY)
    private Data data;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "device", fetch = FetchType.LAZY)
    private Parameter parameter;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "device", fetch = FetchType.LAZY)
    private Limit limit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device", fetch = FetchType.LAZY)
    private List<Occurrence> occurrenceList;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public Device() {
    }

    public Device(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Device(Integer deviceId, String name, String ip, String mask, String gateway, Date createTime) {
        this.deviceId = deviceId;
        this.name = name;
        this.ip = ip;
        this.mask = mask;
        this.gateway = gateway;
        this.createTime = createTime;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public List<Occurrence> getOccurrenceList() {
        return occurrenceList;
    }

    public void setOccurrenceList(List<Occurrence> occurrenceList) {
        this.occurrenceList = occurrenceList;
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
        hash += (deviceId != null ? deviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.deviceId == null && other.deviceId != null) || (this.deviceId != null && !this.deviceId.equals(other.deviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.net.multiway.ofm.entities.Device[ deviceId=" + deviceId + " ]";
    }
    
}
