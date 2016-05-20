/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.entities;

import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author joshua
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

    private Integer dataEventId;

    private ObjectProperty<Integer> distance;
    private ObjectProperty<Integer> type;
    private ObjectProperty<Float> echoLoss;
    private ObjectProperty<Float> insertionLoss;
    private ObjectProperty<Float> averageAttenuationCoefficient;
    private ObjectProperty<Float> acumulativeLoss;

    private Data data;

    public DataEvent() {
        this.distance = new SimpleObjectProperty<>();
        this.type = new SimpleObjectProperty<>();
        this.echoLoss = new SimpleObjectProperty<>();
        this.insertionLoss = new SimpleObjectProperty<>();
        this.averageAttenuationCoefficient = new SimpleObjectProperty<>();
        this.acumulativeLoss = new SimpleObjectProperty<>();
    }

    public DataEvent(int distance, int type, float echoLoss, float insertionLoss, float averageAttenuationCoefficient, float acumulativeLoss) {
        this.distance = new SimpleObjectProperty<>(distance);
        this.type = new SimpleObjectProperty<>(type);
        this.echoLoss = new SimpleObjectProperty<>(echoLoss);
        this.insertionLoss = new SimpleObjectProperty<>(insertionLoss);
        this.averageAttenuationCoefficient = new SimpleObjectProperty<>(averageAttenuationCoefficient);
        this.acumulativeLoss = new SimpleObjectProperty<>(acumulativeLoss);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "data_event_id", nullable = false)
    public Integer getDataEventId() {
        return dataEventId;
    }

    public void setDataEventId(Integer dataEventId) {
        this.dataEventId = dataEventId;
    }

    @Basic(optional = false)
    @Column(name = "distance", nullable = false)
    public Integer getDistance() {
        return distance.get();
    }

    public void setDistance(int distance) {
        this.distance.set(distance);
    }

    public ObjectProperty<Integer> distanceProperty() {
        return this.distance;
    }

    @Basic(optional = false)
    @Column(name = "type", nullable = false)
    public Integer getType() {
        return type.get();
    }

    public void setType(int type) {
        this.type.set(type);
    }

    public ObjectProperty<Integer> typeProperty() {
        return this.type;
    }

    @Basic(optional = false)
    @Column(name = "echo_loss", nullable = false)
    public Float getEchoLoss() {
        return echoLoss.get();
    }

    public void setEchoLoss(float echoLoss) {
        this.echoLoss.set(echoLoss);
    }

    public ObjectProperty<Float> echoLossProperty() {
        return this.echoLoss;
    }

    @Basic(optional = false)
    @Column(name = "insertion_loss", nullable = false)
    public Float getInsertionLoss() {
        return insertionLoss.get();
    }

    public void setInsertionLoss(float insertionLoss) {
        this.insertionLoss.set(insertionLoss);
    }

    public ObjectProperty<Float> insertLossProperty() {
        return this.insertionLoss;
    }

    @Basic(optional = false)
    @Column(name = "average_attenuation_coefficient", nullable = false)
    public Float getAverageAttenuationCoefficient() {
        return averageAttenuationCoefficient.get();
    }

    public void setAverageAttenuationCoefficient(float averageAttenuationCoefficient) {
        this.averageAttenuationCoefficient.set(averageAttenuationCoefficient);
    }

    public ObjectProperty<Float> averageAttenuationCoefficientProperty() {
        return this.averageAttenuationCoefficient;
    }

    @Basic(optional = false)
    @Column(name = "acumulative_loss", nullable = false)
    public Float getAcumulativeLoss() {
        return acumulativeLoss.get();
    }

    public void setAcumulativeLoss(float acumulativeLoss) {
        this.acumulativeLoss.set(acumulativeLoss);
    }

    public ObjectProperty<Float> acumulativeLossProperty() {
        return this.acumulativeLoss;
    }

    @JoinColumn(name = "data_id", referencedColumnName = "data_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
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

    public void copy(DataEvent data) {
        setAcumulativeLoss(data.getAcumulativeLoss());
        setAverageAttenuationCoefficient(data.getAverageAttenuationCoefficient());
        setDistance(data.getDistance());
        setEchoLoss(data.getEchoLoss());
        setInsertionLoss(data.getInsertionLoss());
        setType(data.getType());
    }

}
