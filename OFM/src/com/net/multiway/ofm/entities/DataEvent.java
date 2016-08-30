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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author joshua
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "data_event")
@NamedQueries({
    @NamedQuery(name = "DataEvent.findAll", query = "SELECT d FROM DataEvent d"),
    @NamedQuery(name = "DataEvent.findByDataEventId", query = "SELECT d FROM DataEvent d WHERE d.dataEventId = :dataEventId"),
    @NamedQuery(name = "DataEvent.findByDistance", query = "SELECT d FROM DataEvent d WHERE d.distance = :distance"),
    @NamedQuery(name = "DataEvent.findByType", query = "SELECT d FROM DataEvent d WHERE d.type = :type"),
    @NamedQuery(name = "DataEvent.findByEchoLoss", query = "SELECT d FROM DataEvent d WHERE d.echoLoss = :echoLoss"),
    @NamedQuery(name = "DataEvent.findByInsertionLoss", query = "SELECT d FROM DataEvent d WHERE d.insertionLoss = :insertionLoss"),
    @NamedQuery(name = "DataEvent.findByAverageAttenuationCoefficient", query = "SELECT d FROM DataEvent d WHERE d.averageAttenuationCoefficient = :averageAttenuationCoefficient"),
    @NamedQuery(name = "DataEvent.findByAcumulativeLoss", query = "SELECT d FROM DataEvent d WHERE d.acumulativeLoss = :acumulativeLoss")})
public class DataEvent implements Externalizable {

    private static final long serialVersionUID = 1L;

    public DataEvent() {
        this.distance = new SimpleObjectProperty<>();
        this.type = new SimpleObjectProperty<>();
        this.echoLoss = new SimpleObjectProperty<>();
        this.insertionLoss = new SimpleObjectProperty<>();
        this.averageAttenuationCoefficient = new SimpleObjectProperty<>();
        this.acumulativeLoss = new SimpleObjectProperty<>();
    }

    public DataEvent(Integer dataEventId, Float distance, Integer type, Float echoLoss, Float insertionLoss, Float averageAttenuationCoefficient, Float acumulativeLoss) {
        this.dataEventId = new SimpleObjectProperty<>(dataEventId);
        this.distance = new SimpleObjectProperty<>(distance);
        this.type = new SimpleObjectProperty<>(type);
        this.echoLoss = new SimpleObjectProperty<>(echoLoss);
        this.insertionLoss = new SimpleObjectProperty<>(insertionLoss);
        this.averageAttenuationCoefficient = new SimpleObjectProperty<>(averageAttenuationCoefficient);
        this.acumulativeLoss = new SimpleObjectProperty<>(acumulativeLoss);
        this._dataEventId = dataEventId;
        this._distance = distance;
        this._type = type;
        this._echoLoss = echoLoss;
        this._insertionLoss = insertionLoss;
        this._averageAttenuationCoefficient = averageAttenuationCoefficient;
        this._acumulativeLoss = acumulativeLoss;
    }

    public DataEvent(Float distance, Integer type, Float echoLoss, Float insertionLoss, Float averageAttenuationCoefficient, Float acumulativeLoss) {
        this.distance = new SimpleObjectProperty<>(distance);
        this.type = new SimpleObjectProperty<>(type);
        this.echoLoss = new SimpleObjectProperty<>(echoLoss);
        this.insertionLoss = new SimpleObjectProperty<>(insertionLoss);
        this.averageAttenuationCoefficient = new SimpleObjectProperty<>(averageAttenuationCoefficient);
        this.acumulativeLoss = new SimpleObjectProperty<>(acumulativeLoss);
        this._distance = distance;
        this._type = type;
        this._echoLoss = echoLoss;
        this._insertionLoss = insertionLoss;
        this._averageAttenuationCoefficient = averageAttenuationCoefficient;
        this._acumulativeLoss = acumulativeLoss;
    }

    private ObjectProperty<Integer> dataEventId;
    private Integer _dataEventId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "data_event_id", nullable = false)
    public Integer getDataEventId() {
        if (dataEventId == null) {
            return _dataEventId;
        } else {
            return dataEventId.get();
        }
    }

    public void setDataEventId(Integer dataEventId) {
        if (this.dataEventId == null) {
            this._dataEventId = dataEventId;
        } else {
            this.dataEventId.set(dataEventId);
        }
    }

    public ObjectProperty<Integer> dataEventIdProperty() {
        if (dataEventId == null) {
            dataEventId = new SimpleObjectProperty<>(_dataEventId);
        }
        return dataEventId;
    }

    private ObjectProperty<Float> distance;
    private Float _distance;

    @Basic(optional = false)
    @Column(name = "distance", nullable = false)
    public Float getDistance() {
        if (distance == null) {
            return _distance;
        } else {
            return distance.get();
        }
    }

    public void setDistance(Float distance) {
        if (this.distance == null) {
            this._distance = distance;
        } else {
            this.distance.set(distance);
        }
    }

    public ObjectProperty<Float> distanceProperty() {
        if (distance == null) {
            distance = new SimpleObjectProperty<>(_distance);
        }
        return distance;
    }

    private ObjectProperty<Integer> type;
    private Integer _type;

    @Basic(optional = false)
    @Column(name = "type", nullable = false)
    public Integer getType() {
        if (type == null) {
            return _type;
        } else {
            return type.get();
        }
    }

    public void setType(Integer type) {
        if (this.type == null) {
            this._type = type;
        } else {
            this.type.set(type);
        }
    }

    public SimpleStringProperty typeProperty() {
        if (type.get() == 0) {
            return new SimpleStringProperty("Início");
        } else if (type.get() == 1) {
            return new SimpleStringProperty("Reflexão");
        } else if (type.get() == 2) {
            return new SimpleStringProperty("Não-Reflexão");
        } else if (type.get() == 3) {
            return new SimpleStringProperty("Fim");
        }
        return null;
    }

    private ObjectProperty<Float> echoLoss;
    private Float _echoLoss;

    @Basic(optional = false)
    @Column(name = "echo_loss", nullable = false)
    public Float getEchoLoss() {
        if (echoLoss == null) {
            return _echoLoss;
        } else {
            return echoLoss.get();
        }
    }

    public void setEchoLoss(Float echoLoss) {
        if (this.echoLoss == null) {
            this._echoLoss = echoLoss;
        } else {
            this.echoLoss.set(echoLoss);
        }
    }

    public ObjectProperty<Float> echoLossProperty() {
        if (echoLoss == null) {
            echoLoss = new SimpleObjectProperty<>(_echoLoss);
        }
        return echoLoss;
    }

    private ObjectProperty<Float> insertionLoss;
    private Float _insertionLoss;

    @Basic(optional = false)
    @Column(name = "insertion_loss", nullable = false)
    public Float getInsertionLoss() {
        if (insertionLoss == null) {
            return _insertionLoss;
        } else {
            return insertionLoss.get();
        }
    }

    public void setInsertionLoss(Float insertionLoss) {
        if (this.insertionLoss == null) {
            this._insertionLoss = insertionLoss;
        } else {
            this.insertionLoss.set(insertionLoss);
        }
    }

    public ObjectProperty<Float> insertionLossProperty() {
        if (insertionLoss == null) {
            insertionLoss = new SimpleObjectProperty<>(_insertionLoss);
        }
        return insertionLoss;
    }

    private ObjectProperty<Float> averageAttenuationCoefficient;
    private Float _averageAttenuationCoefficient;

    @Basic(optional = false)
    @Column(name = "average_attenuation_coefficient", nullable = false)
    public Float getAverageAttenuationCoefficient() {
        if (averageAttenuationCoefficient == null) {
            return _averageAttenuationCoefficient;
        } else {
            return averageAttenuationCoefficient.get();
        }
    }

    public void setAverageAttenuationCoefficient(Float averageAttenuationCoefficient) {
        if (this.averageAttenuationCoefficient == null) {
            this._averageAttenuationCoefficient = averageAttenuationCoefficient;
        } else {
            this.averageAttenuationCoefficient.set(averageAttenuationCoefficient);
        }
    }

    public ObjectProperty<Float> averageAttenuationCoefficientProperty() {
        if (averageAttenuationCoefficient == null) {
            averageAttenuationCoefficient = new SimpleObjectProperty<>(_averageAttenuationCoefficient);
        }
        return averageAttenuationCoefficient;
    }

    private ObjectProperty<Float> acumulativeLoss;
    private Float _acumulativeLoss;

    @Basic(optional = false)
    @Column(name = "acumulative_loss", nullable = false)
    public Float getAcumulativeLoss() {
        if (acumulativeLoss == null) {
            return _acumulativeLoss;
        } else {
            return acumulativeLoss.get();
        }
    }

    public void setAcumulativeLoss(Float acumulativeLoss) {
        if (this.acumulativeLoss == null) {
            this._acumulativeLoss = acumulativeLoss;
        } else {
            this.acumulativeLoss.set(acumulativeLoss);
        }
    }

    public ObjectProperty<Float> acumulativeLossProperty() {
        if (acumulativeLoss == null) {
            acumulativeLoss = new SimpleObjectProperty<>(_acumulativeLoss);
        }
        return acumulativeLoss;
    }

    private ObjectProperty<Data> data;
    private Data _data;

    @JoinColumn(name = "data_id", referencedColumnName = "data_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
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

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (_dataEventId != null ? _dataEventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataEvent)) {
            return false;
        }
        DataEvent other = (DataEvent) object;
        if ((this._dataEventId == null && other._dataEventId != null) || (this._dataEventId != null && !this._dataEventId.equals(other._dataEventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ofm.model.entities.DataEvent[ dataEventId=" + _dataEventId + " - data=" + _data.toString() + " ]";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getDataEventId());
        out.writeObject(getDistance());
        out.writeObject(getType());
        out.writeObject(getEchoLoss());
        out.writeObject(getInsertionLoss());
        out.writeObject(getAverageAttenuationCoefficient());
        out.writeObject(getAcumulativeLoss());
        out.writeObject(getData());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setDataEventId((Integer) in.readObject());
        setDistance((Float) in.readObject());
        setType((Integer) in.readObject());
        setEchoLoss((Float) in.readObject());
        setInsertionLoss((Float) in.readObject());
        setAverageAttenuationCoefficient((Float) in.readObject());
        setAcumulativeLoss((Float) in.readObject());
        setData((Data) in.readObject());
    }

}
