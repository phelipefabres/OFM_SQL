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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author joshua
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "occurrence")
@NamedQueries({
    @NamedQuery(name = "Occurrence.findAll", query = "SELECT o FROM Occurrence o"),
    @NamedQuery(name = "Occurrence.findByOccurrenceId", query = "SELECT o FROM Occurrence o WHERE o.occurrenceId = :occurrenceId"),
    @NamedQuery(name = "Occurrence.findByType", query = "SELECT o FROM Occurrence o WHERE o.type = :type"),
    @NamedQuery(name = "Occurrence.findByDescription", query = "SELECT o FROM Occurrence o WHERE o.description = :description"),
    @NamedQuery(name = "Occurrence.findByCreateTime", query = "SELECT o FROM Occurrence o WHERE o.createTime = :createTime")})
public class Occurrence implements Externalizable {

    private static final long serialVersionUID = 1L;

    public Occurrence() {
        this.type = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.createTime = new SimpleObjectProperty<>();
    }

    public Occurrence(Integer occurrenceId, String type, String description, Date createTime) {
        this.occurrenceId = new SimpleObjectProperty<>(occurrenceId);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.createTime = new SimpleObjectProperty<>(createTime);
        this._occurrenceId = occurrenceId;
        this._type = type;
        this._description = description;
        this._createTime = createTime;
    }

    public Occurrence(String type, String description, Date createTime) {
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.createTime = new SimpleObjectProperty<>(createTime);
        this._type = type;
        this._description = description;
        this._createTime = createTime;
    }

    private ObjectProperty<Integer> occurrenceId;
    private Integer _occurrenceId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "occurrence_id", nullable = false)
    public Integer getOccurrenceId() {
        if (occurrenceId == null) {
            return _occurrenceId;
        } else {
            return occurrenceId.get();
        }
    }

    public void setOccurrenceId(Integer occurrenceId) {
        if (this.occurrenceId == null) {
            this._occurrenceId = occurrenceId;
        } else {
            this.occurrenceId.set(occurrenceId);
        }
    }

    public ObjectProperty<Integer> occurrenceIdProperty() {
        if (occurrenceId == null) {
            occurrenceId = new SimpleObjectProperty<>(_occurrenceId);
        }
        return occurrenceId;
    }

    private StringProperty type;
    private String _type;

    @Basic(optional = false)
    @Column(name = "type", nullable = false, length = 127) // ('Green', 'Yellow', 'Red')
    public String getType() {
        if (type == null) {
            return _type;
        } else {
            return type.get();
        }
    }

    public void setType(String type) {
        if (this.type == null) {
            this._type = type;
        } else {
            this.type.set(type);
        }
    }

    public StringProperty typeProperty() {
        if (type == null) {
            type = new SimpleStringProperty(this, "type", _type);
        }
        return type;
    }

    private StringProperty description;
    private String _description;

    @Basic(optional = false)
    @Column(name = "description", nullable = false, length = 127)
    public String getDescription() {
        if (description == null) {
            return _description;
        } else {
            return description.get();
        }
    }

    public void setDescription(String description) {
        if (this.description == null) {
            this._description = description;
        } else {
            this.description.set(description);
        }
    }

    public StringProperty descriptionProperty() {
        if (description == null) {
            description = new SimpleStringProperty(this, "description", _description);
        }
        return description;
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

    private ObjectProperty<Device> device;
    private Device _device;

    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    @ManyToOne(optional = false)
    @NotFound(action=NotFoundAction.IGNORE)
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (_occurrenceId != null ? _occurrenceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Occurrence)) {
            return false;
        }
        Occurrence other = (Occurrence) object;
        if ((this._occurrenceId == null && other._occurrenceId != null) || (this._occurrenceId != null && !this._occurrenceId.equals(other._occurrenceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ofm.model.entities.Occurrence[ occurrenceId=" + _occurrenceId + " - device=" + _device.toString() + " ]";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getOccurrenceId());
        out.writeObject(getType());
        out.writeObject(getDescription());
        out.writeObject(getCreateTime());
        out.writeObject(getDevice());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setOccurrenceId((Integer) in.readObject());
        setType((String) in.readObject());
        setDescription((String) in.readObject());
        setCreateTime((Date) in.readObject());
        setDevice((Device) in.readObject());
    }

}
