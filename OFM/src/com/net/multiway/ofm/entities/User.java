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
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(schema = "ofm", name = "user", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"}),
    @UniqueConstraint(columnNames = {"username"})})
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByCreateTime", query = "SELECT u FROM User u WHERE u.createTime = :createTime"),
    @NamedQuery(name = "User.findByUpdateTime", query = "SELECT u FROM User u WHERE u.updateTime = :updateTime")})
public class User implements Externalizable {

    private static final long serialVersionUID = 1L;


    public User() {
        this.username = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.createTime = new SimpleObjectProperty<>();
    }

    public User(Integer userId) {
        this._userId = userId;
    }

    public User(Integer userId, String username, String email, String password, Date createTime) {
        this._userId = userId;
        this._username = username;
        this._email = email;
        this._password = password;
        this._createTime = createTime;
        //this.userId = new SimpleObjectProperty<>(userId);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.createTime = new SimpleObjectProperty<>(createTime);

    }

    public User(String username, String email, String password, Date createTime) {
        this._username = username;
        this._email = email;
        this._password = password;
        this._createTime = createTime;
    }

    private IntegerProperty userId;
    private Integer _userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
        if (userId == null) {
            return _userId;
        } else {
            return userId.get();
        }
    }

    public void setUserId(Integer userId) {
        if (this.userId == null) {
            this._userId = userId;
        } else {
            this.userId.set(userId);
        }
    }

    public IntegerProperty userIdProperty() {
        if (userId == null) {
            userId = new SimpleIntegerProperty(this, "userId", _userId);
        }
        return userId;
    }
    
    private IntegerProperty isAdmin;
    private Integer _isAdmin;
    
    @Basic(optional = false)
    @Column(name = "is_admin", nullable = false)
    public Integer getisAdmin() {
        if (isAdmin == null) {
            return _isAdmin;
        } else {
            return isAdmin.get();
        }
    }

    public void setisAdmin(Integer isAdmin) {
        if (this.userId == null) {
            this._isAdmin = isAdmin;
        } else {
            this.isAdmin.set(isAdmin);
        }
    }

    public IntegerProperty isAdmindProperty() {
        if (isAdmin == null) {
            isAdmin = new SimpleIntegerProperty(this, "userId", _isAdmin);
        }
        return isAdmin;
    }

    private StringProperty username;
    private String _username;

    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 16)
    public String getUsername() {
        if (username == null) {
            return _username;
        } else {
            return username.get();
        }
    }

    public void setUsername(String username) {
        if (this.username == null) {
            this._username = username;
        } else {
            this.username.set(username);
        }
    }

    public StringProperty username() {
        if (username == null) {
            username = new SimpleStringProperty(this, "username", _username);
        }
        return username;
    }

    private StringProperty email;
    private String _email;

    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        if (email == null) {
            return _email;
        } else {
            return email.get();
        }
    }

    public void setEmail(String email) {
        if (this.email == null) {
            this._email = email;
        } else {
            this.email.set(email);
        }
    }

    public StringProperty emailProperty() {
        if (email == null) {
            email = new SimpleStringProperty(this, "email", _email);
        }
        return email;
    }

    private StringProperty password;
    private String _password;

    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 32)
    public String getPassword() {
        if (password == null) {
            return _password;
        } else {
            return password.get();
        }
    }

    public void setPassword(String password) {
        if (this.password == null) {
            this._password = password;
        } else {
            this.password.set(password);
        }
    }

    public StringProperty passwordProperty() {
        if (password == null) {
            password = new SimpleStringProperty(this, "password", _password);
        }
        return password;
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

    private ListProperty<Data> dataList;
    private List<Data> _dataList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public List<Data> getDataList() {
        if (dataList == null) {
            return _dataList;
        } else {
            return dataList.get();
        }
    }

    public void setDataList(List<Data> dataList) {
        if (this.dataList == null) {
            this._dataList = dataList;
        } else {
            this.dataList.set(FXCollections.observableArrayList(dataList));
        }
    }

    public ListProperty<Data> dataListProperty() {
        if (dataList == null) {
            dataList = new SimpleListProperty<>(this, "dataList", FXCollections.observableArrayList(_dataList));
        }
        return dataList;
    }

    private ListProperty<Parameter> parameterList;
    private List<Parameter> _parameterList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public List<Parameter> getParameterList() {
        if (parameterList == null) {
            return _parameterList;
        } else {
            return parameterList.get();
        }
    }

    public void setParameterList(List<Parameter> parameterList) {
        if (this.parameterList == null) {
            this._parameterList = parameterList;
        } else {
            this.parameterList.set(FXCollections.observableArrayList(parameterList));
        }
    }

    public ListProperty<Parameter> parameterListProperty() {
        if (parameterList == null) {
            parameterList = new SimpleListProperty<>(this, "parameterList", FXCollections.observableArrayList(_parameterList));
        }
        return parameterList;
    }

    private ListProperty<Limit> limitList;
    private List<Limit> _limitList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public List<Limit> getLimitList() {
        if (limitList == null) {
            return _limitList;
        } else {
            return limitList.get();
        }
    }

    public void setLimitList(List<Limit> limitList) {
        if (this.limitList == null) {
            this._limitList = limitList;
        } else {
            this.limitList.set(FXCollections.observableArrayList(limitList));
        }
    }

    public ListProperty<Limit> limitListProperty() {
        if (limitList == null) {
            limitList = new SimpleListProperty<>(this, "limitList", FXCollections.observableArrayList(_limitList));
        }
        return limitList;
    }

    private ListProperty<Device> deviceList;
    private List<Device> _deviceList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    public List<Device> getDeviceList() {
        if (deviceList == null) {
            return _deviceList;
        } else {
            return deviceList.get();
        }
    }

    public void setDeviceList(List<Device> deviceList) {
        if (this.deviceList == null) {
            this._deviceList = deviceList;
        } else {
            this.deviceList.set(FXCollections.observableArrayList(deviceList));
        }
    }

    public ListProperty<Device> deviceListProperty() {
        if (deviceList == null) {
            deviceList = new SimpleListProperty<>(this, "deviceList", FXCollections.observableArrayList(_deviceList));
        }
        return deviceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (_userId != null ? _userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this._userId == null && other._userId != null) || (this._userId != null && !this._userId.equals(other._userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ofm.model.entities.User[ userId=" + _userId + "; username=" + _username + " ]";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getUserId());
        out.writeObject(getUsername());
        out.writeObject(getEmail());
        out.writeObject(getPassword());
        out.writeObject(getCreateTime());
        out.writeObject(getUpdateTime());
        out.writeObject(getDataList());
        out.writeObject(getParameterList());
        out.writeObject(getLimitList());
        out.writeObject(getDeviceList());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setUserId((Integer) in.readObject());
        setUsername((String) in.readObject());
        setEmail((String) in.readObject());
        setPassword((String) in.readObject());
        setCreateTime((Date) in.readObject());
        setUpdateTime((Date) in.readObject());
        setDataList((List<Data>) in.readObject());
        setParameterList((List<Parameter>) in.readObject());
        setLimitList((List<Limit>) in.readObject());
        setDeviceList((List<Device>) in.readObject());
    }

}
