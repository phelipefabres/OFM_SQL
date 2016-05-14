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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author phelipe
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "occurrence_id", nullable = false)
    private Integer occurrenceId;
    @Basic(optional = false)
    @Column(nullable = false, length = 6)
    private String type;
    @Basic(optional = false)
    @Column(nullable = false, length = 127)
    private String description;
    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Device device;

    public Occurrence() {
    }

    public Occurrence(Integer occurrenceId) {
        this.occurrenceId = occurrenceId;
    }

    public Occurrence(Integer occurrenceId, String type, String description, Date createTime) {
        this.occurrenceId = occurrenceId;
        this.type = type;
        this.description = description;
        this.createTime = createTime;
    }

    public Integer getOccurrenceId() {
        return occurrenceId;
    }

    public void setOccurrenceId(Integer occurrenceId) {
        this.occurrenceId = occurrenceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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
