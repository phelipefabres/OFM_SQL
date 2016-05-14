/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.entities;

import java.io.Serializable;
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

/**
 *
 * @author phelipe
 */
@Entity
@Table(name = "data_event", catalog = "ofm", schema = "")
@NamedQueries({
    @NamedQuery(name = "DataEvent.findAll", query = "SELECT d FROM DataEvent d"),
    @NamedQuery(name = "DataEvent.findByDataEventId", query = "SELECT d FROM DataEvent d WHERE d.dataEventId = :dataEventId"),
    @NamedQuery(name = "DataEvent.findByDistance", query = "SELECT d FROM DataEvent d WHERE d.distance = :distance"),
    @NamedQuery(name = "DataEvent.findByType", query = "SELECT d FROM DataEvent d WHERE d.type = :type"),
    @NamedQuery(name = "DataEvent.findByEchoLoss", query = "SELECT d FROM DataEvent d WHERE d.echoLoss = :echoLoss"),
    @NamedQuery(name = "DataEvent.findByInsertionLoss", query = "SELECT d FROM DataEvent d WHERE d.insertionLoss = :insertionLoss"),
    @NamedQuery(name = "DataEvent.findByAverageAttenuationCoefficient", query = "SELECT d FROM DataEvent d WHERE d.averageAttenuationCoefficient = :averageAttenuationCoefficient"),
    @NamedQuery(name = "DataEvent.findByAcumulativeLoss", query = "SELECT d FROM DataEvent d WHERE d.acumulativeLoss = :acumulativeLoss")})
public class DataEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "data_event_id", nullable = false)
    private Integer dataEventId;
    @Basic(optional = false)
    @Column(nullable = false)
    private float distance;
    @Basic(optional = false)
    @Column(nullable = false)
    private int type;
    @Basic(optional = false)
    @Column(name = "echo_loss", nullable = false)
    private float echoLoss;
    @Basic(optional = false)
    @Column(name = "insertion_loss", nullable = false)
    private float insertionLoss;
    @Basic(optional = false)
    @Column(name = "average_attenuation_coefficient", nullable = false)
    private float averageAttenuationCoefficient;
    @Basic(optional = false)
    @Column(name = "acumulative_loss", nullable = false)
    private float acumulativeLoss;
    @JoinColumn(name = "data_id", referencedColumnName = "data_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Data data;

    public DataEvent() {
    }

   

    public DataEvent( float distance, int type, float echoLoss, float insertionLoss, float averageAttenuationCoefficient, float acumulativeLoss) {
      
        this.distance = distance;
        this.type = type;
        this.echoLoss = echoLoss;
        this.insertionLoss = insertionLoss;
        this.averageAttenuationCoefficient = averageAttenuationCoefficient;
        this.acumulativeLoss = acumulativeLoss;
    }

    public Integer getDataEventId() {
        return dataEventId;
    }

    public void setDataEventId(Integer dataEventId) {
        this.dataEventId = dataEventId;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getEchoLoss() {
        return echoLoss;
    }

    public void setEchoLoss(float echoLoss) {
        this.echoLoss = echoLoss;
    }

    public float getInsertionLoss() {
        return insertionLoss;
    }

    public void setInsertionLoss(float insertionLoss) {
        this.insertionLoss = insertionLoss;
    }

    public float getAverageAttenuationCoefficient() {
        return averageAttenuationCoefficient;
    }

    public void setAverageAttenuationCoefficient(float averageAttenuationCoefficient) {
        this.averageAttenuationCoefficient = averageAttenuationCoefficient;
    }

    public float getAcumulativeLoss() {
        return acumulativeLoss;
    }

    public void setAcumulativeLoss(float acumulativeLoss) {
        this.acumulativeLoss = acumulativeLoss;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataEventId != null ? dataEventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataEvent)) {
            return false;
        }
        DataEvent other = (DataEvent) object;
        if ((this.dataEventId == null && other.dataEventId != null) || (this.dataEventId != null && !this.dataEventId.equals(other.dataEventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.net.multiway.ofm.entities.DataEvent[ dataEventId=" + dataEventId + " ]";
    }
    
}
