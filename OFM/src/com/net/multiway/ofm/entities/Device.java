/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.entities;

import com.net.multiway.ofm.utils.Utils;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
 * @author joshua
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

    private Integer deviceId;

    private String name;

    private StringProperty ip;

    private StringProperty mask;

    private StringProperty gateway;

    private Date createTime;

    private Date updateTime;

    private Data data;

    private Parameter parameter;

    private Limit limit;

    private List<Occurrence> occurrenceList;

    private User user;

    public Device() {
        this.ip = new SimpleStringProperty();
        this.mask = new SimpleStringProperty();
        this.gateway = new SimpleStringProperty();
    }

    public Device(String name, String ip, String mask, String gateway, Date createTime) {
        this.name = name;
        this.ip = new SimpleStringProperty(ip);
        this.mask = new SimpleStringProperty(mask);
        this.gateway = new SimpleStringProperty(gateway);
        this.createTime = createTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "device_id", nullable = false)
    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    @Basic(optional = false)
    @Column(name = "name",nullable = false, length = 63)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic(optional = false)
    @Column(name = "ip",nullable = false, length = 32)
    public String getIp() {
        return ip.get();
    }

    public StringProperty ipProperty() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip.set(Utils.fillAddress(ip));
    }

    @Basic(optional = false)
    @Column(name = "mask",nullable = false, length = 32)
    public String getMask() {
        return mask.get();
    }

    public StringProperty maskProperty() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask.set(Utils.fillAddress(mask));
    }

    @Basic(optional = false)
    @Column(name = "gateway",nullable = false, length = 32)
    public String getGateway() {
        return gateway.get();
    }

    public StringProperty gatewayProperty() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway.set(Utils.fillAddress(gateway));
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "device", fetch = FetchType.LAZY)
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "device", fetch = FetchType.LAZY)
    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "device", fetch = FetchType.LAZY)
    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device", fetch = FetchType.LAZY)
    public List<Occurrence> getOccurrenceList() {
        return occurrenceList;
    }

    public void setOccurrenceList(List<Occurrence> occurrenceList) {
        this.occurrenceList = occurrenceList;
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

    public String takeData() {
        return this.ip.get() + this.mask.get() + this.gateway.get();
    }
}
