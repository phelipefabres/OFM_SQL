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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author joshua
 */
@Entity
@Table(catalog = "ofm", schema = "")
@NamedQueries({
    @NamedQuery(name = "Occurrence.findAll", query = "SELECT o FROM Occurrence o"),
    @NamedQuery(name = "Occurrence.findByOccurrenceId", query = "SELECT o FROM Occurrence o WHERE o.occurrenceId = :occurrenceId"),
    @NamedQuery(name = "Occurrence.findByType", query = "SELECT o FROM Occurrence o WHERE o.type = :type"),
    @NamedQuery(name = "Occurrence.findByDescription", query = "SELECT o FROM Occurrence o WHERE o.description = :description"),
    @NamedQuery(name = "Occurrence.findByCreateTime", query = "SELECT o FROM Occurrence o WHERE o.createTime = :createTime")})
public class Occurrence implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer occurrenceId;

    private StringProperty type;

    private StringProperty description;

    private ObjectProperty<Date> createTime;

    private Device device;

    public Occurrence() {
        this.type = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.createTime = new SimpleObjectProperty<>();
    }

    public Occurrence(Integer occurrenceId) {
        this.occurrenceId = occurrenceId;
    }

    public Occurrence(Integer occurrenceId, String type, String description, Date createTime) {
        this.occurrenceId = occurrenceId;
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.createTime = new SimpleObjectProperty<>(createTime);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "occurrence_id", nullable = false)
    public Integer getOccurrenceId() {
        return occurrenceId;
    }

    public void setOccurrenceId(Integer occurrenceId) {
        this.occurrenceId = occurrenceId;
    }

    @Basic(optional = false)
    @Column(name = "type",nullable = false, length = 6)
    public String getType() {
        return type.get();
    }

    public void setType(String occurrence) {
        this.type.set(occurrence);
    }

    public StringProperty occurrenceProperty() {
        return type;
    }

    @Basic(optional = false)
    @Column(name = "description",nullable = false, length = 127)
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    // @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime.get();
    }

    public void setCreateTime(Date createTime) {
        this.createTime.set(createTime);
    }

    public ObjectProperty<Date> dateProperty() {
        return createTime;
    }

    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (occurrenceId != null ? occurrenceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Occurrence)) {
            return false;
        }
        Occurrence other = (Occurrence) object;
        if ((this.occurrenceId == null && other.occurrenceId != null) || (this.occurrenceId != null && !this.occurrenceId.equals(other.occurrenceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.net.multiway.ofm.entities.Occurrence[ occurrenceId=" + occurrenceId + " ]";
    }

}
