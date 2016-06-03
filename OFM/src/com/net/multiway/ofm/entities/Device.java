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
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Access(AccessType.PROPERTY)
@Table(schema = "ofm", name = "device", uniqueConstraints = {
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
public class Device implements Externalizable {

    private static final long serialVersionUID = 1L;

    public Device() {
        this.ip = new SimpleStringProperty();
        this.mask = new SimpleStringProperty();
        this.gateway = new SimpleStringProperty();
        this.createTime = new SimpleObjectProperty<>();
        this.status = new SimpleStringProperty();
    }

    public Device(Integer deviceId, String name, String ip, String mask, String gateway, Date createTime, String status) {
        this.deviceId = new SimpleObjectProperty<>(deviceId);
        this.name = new SimpleStringProperty(name);
        this.ip = new SimpleStringProperty(ip);
        this.mask = new SimpleStringProperty(mask);
        this.gateway = new SimpleStringProperty(gateway);
        this.createTime = new SimpleObjectProperty<>(createTime);
        this.status = new SimpleStringProperty(status);
        this._deviceId = deviceId;
        this._name = name;
        this._ip = ip;
        this._mask = mask;
        this._gateway = gateway;
        this._createTime = createTime;
        this._status = status;

    }

    public Device(String name, String ip, String mask, String gateway, Date createTime, String status) {
        this.name = new SimpleStringProperty(name);
        this.ip = new SimpleStringProperty(ip);
        this.mask = new SimpleStringProperty(mask);
        this.gateway = new SimpleStringProperty(gateway);
        this.createTime = new SimpleObjectProperty<>(createTime);
        this.status = new SimpleStringProperty(status);
        this._name = name;
        this._ip = ip;
        this._mask = mask;
        this._gateway = gateway;
        this._createTime = createTime;
        this._status = status;

    }

    private ObjectProperty<Integer> deviceId;
    private Integer _deviceId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "device_id", nullable = false)
    public Integer getDeviceId() {
        if (deviceId == null) {
            return _deviceId;
        } else {
            return deviceId.get();
        }
    }

    public void setDeviceId(Integer deviceId) {
        if (this.deviceId == null) {
            this._deviceId = deviceId;
        } else {
            this.deviceId.set(deviceId);
        }
    }

    public ObjectProperty<Integer> deviceIdProperty() {
        if (deviceId == null) {
            deviceId = new SimpleObjectProperty<>(_deviceId);
        }
        return deviceId;
    }

    private StringProperty name;
    private String _name;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 63)
    public String getName() {
        if (name == null) {
            return _name;
        } else {
            return name.get();
        }
    }

    public void setName(String name) {
        if (this.name == null) {
            this._name = name;
        } else {
            this.name.set(name);
        }
    }

    public StringProperty nameProperty() {
        if (name == null) {
            name = new SimpleStringProperty(this, "name", _name);
        }
        return name;
    }

    private StringProperty ip;
    private String _ip;

    @Basic(optional = false)
    @Column(name = "ip", nullable = false, length = 32)
    public String getIp() {
        if (ip == null) {
            return _ip;
        } else {
            return ip.get();
        }
    }

    public void setIp(String ip) {
        if (this.ip == null) {
            this._ip = ip;
        } else {
            this.ip.set(ip);
        }
    }

    public StringProperty ipProperty() {
        if (ip == null) {
            ip = new SimpleStringProperty(this, "ip", _ip);
        }
        return ip;
    }

    private StringProperty mask;
    private String _mask;

    @Basic(optional = false)
    @Column(name = "mask", nullable = false, length = 32)
    public String getMask() {
        if (mask == null) {
            return _mask;
        } else {
            return mask.get();
        }
    }

    public void setMask(String mask) {
        if (this.mask == null) {
            this._mask = mask;
        } else {
            this.mask.set(mask);
        }
    }

    public StringProperty maskProperty() {
        if (mask == null) {
            mask = new SimpleStringProperty(this, "mask", _mask);
        }
        return mask;
    }

    private StringProperty gateway;
    private String _gateway;

    @Basic(optional = false)
    @Column(name = "gateway", nullable = false, length = 32)
    public String getGateway() {
        if (gateway == null) {
            return _gateway;
        } else {
            return gateway.get();
        }
    }

    public void setGateway(String gateway) {
        if (this.gateway == null) {
            this._gateway = gateway;
        } else {
            this.gateway.set(gateway);
        }
    }

    public StringProperty gatewayProperty() {
        if (gateway == null) {
            gateway = new SimpleStringProperty(this, "gateway", _gateway);
        }
        return gateway;
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

    private StringProperty status;
    private String _status;

    @Basic(optional = false)
    @Column(name = "status", nullable = false) // ('Active', 'Inactive')
    public String getStatus() {
        if (status == null) {
            return _status;
        } else {
            return status.get();
        }
    }

    public void setStatus(String status) {
        if (this.status == null) {
            this._status = status;
        } else {
            this.status.set(status);
        }
    }

    public StringProperty statusProperty() {
        if (status == null) {
            status = new SimpleStringProperty(this, "status", _status);
        }
        return status;
    }

    private ObjectProperty<Data> data;
    private Data _data;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "device")
    public Data getData() {
        if (data == null) {
            return _data;
        } else {
            return data.get();
        }
    }

    public void setData(Data data) {
        if (this.data == null) {
            this._data = data;
        } else {
            this.data.set(data);
        }
    }

    public ObjectProperty<Data> dataProperty() {
        if (data == null) {
            data = new SimpleObjectProperty<>(this, "data", _data);
        }
        return data;
    }

    private ObjectProperty<Parameter> parameter;
    private Parameter _parameter;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "device")
    public Parameter getParameter() {
        if (parameter == null) {
            return _parameter;
        } else {
            return parameter.get();
        }
    }

    public void setParameter(Parameter parameter) {
        if (this.parameter == null) {
            this._parameter = parameter;
        } else {
            this.parameter.set(parameter);
        }
    }

    public ObjectProperty<Parameter> parameterProperty() {
        if (parameter == null) {
            parameter = new SimpleObjectProperty<>(this, "parameter", _parameter);
        }
        return parameter;
    }

    private ObjectProperty<Limit> limit;
    private Limit _limit;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "device")
    public Limit getLimit() {
        if (limit == null) {
            return _limit;
        } else {
            return limit.get();
        }
    }

    public void setLimit(Limit limit) {
        if (this.limit == null) {
            this._limit = limit;
        } else {
            this.limit.set(limit);
        }
    }

    public ObjectProperty<Limit> limitProperty() {
        if (limit == null) {
            limit = new SimpleObjectProperty<>(this, "limit", _limit);
        }
        return limit;
    }

    private List<Occurrence> occurrenceList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device")
    public List<Occurrence> getOccurrenceList() {

        return occurrenceList;

    }

    public void setOccurrenceList(List<Occurrence> occurrenceList) {

        this.occurrenceList = occurrenceList;

    }

    public void addOccurrence(Occurrence occur) {
        this.occurrenceList.add(occur);
    }

    private ObjectProperty<User> user;
    private User _user;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
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
        return "ofm.model.entities.Device[ deviceId=" + deviceId + "; name=" + name + " ]";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getDeviceId());
        out.writeObject(getName());
        out.writeObject(getIp());
        out.writeObject(getMask());
        out.writeObject(getGateway());
        out.writeObject(getCreateTime());
        out.writeObject(getUpdateTime());
        out.writeObject(getStatus());
        out.writeObject(getData());
        out.writeObject(getParameter());
        out.writeObject(getLimit());
        out.writeObject(getOccurrenceList());
        out.writeObject(getUser());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setDeviceId((Integer) in.readObject());
        setName((String) in.readObject());
        setIp((String) in.readObject());
        setMask((String) in.readObject());
        setGateway((String) in.readObject());
        setCreateTime((Date) in.readObject());
        setUpdateTime((Date) in.readObject());
        setStatus((String) in.readObject());
        setData((Data) in.readObject());
        setParameter((Parameter) in.readObject());
        setLimit((Limit) in.readObject());
        setOccurrenceList((List<Occurrence>) in.readObject());
        setUser((User) in.readObject());
    }

    public String takeData() {
        return this.ip.get() + this.mask.get() + this.gateway.get();
    }

}
