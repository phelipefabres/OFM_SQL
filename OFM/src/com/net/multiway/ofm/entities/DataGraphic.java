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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javax.persistence.Table;

/**
 *
 * @author joshua
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(schema = "ofm", name = "data_graphic")
@NamedQueries({
    @NamedQuery(name = "DataGraphic.findAll", query = "SELECT d FROM DataGraphic d"),
    @NamedQuery(name = "DataGraphic.findByDataGraphicId", query = "SELECT d FROM DataGraphic d WHERE d.dataGraphicId = :dataGraphicId"),
    @NamedQuery(name = "DataGraphic.findByPoint", query = "SELECT d FROM DataGraphic d WHERE d.point = :point")})
public class DataGraphic implements Externalizable {

    private static final long serialVersionUID = 1L;

    public DataGraphic() {
        this.dataGraphicId = new SimpleObjectProperty<>();
        this.point = new SimpleObjectProperty<>();
    }

    public DataGraphic(Long dataGraphicId, Integer point) {
        this.dataGraphicId = new SimpleObjectProperty<>(dataGraphicId);
        this.point = new SimpleObjectProperty<>(point);
        this._dataGraphicId = dataGraphicId;
        this._point = point;
    }

    public DataGraphic(Integer point) {
        this.point = new SimpleObjectProperty<>(point);
        this._point = point;
    }

//    private ObjectProperty<Long> dataGraphicId;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "data_graphic_id", nullable = false)
//    public Long getDataGraphicId() {
//
//        return dataGraphicId.get();
//
//    }
//
//    public void setDataGraphicId(Long dataGraphicId) {
//
//        this.dataGraphicId.set(dataGraphicId);
//
//    }
//
//    public ObjectProperty<Long> dataGraphicIdProperty() {
//
//        return dataGraphicId;
//    }
//
//    private ObjectProperty<Integer> point;
//
//    @Basic(optional = false)
//    @Column(name = "point", nullable = false)
//    public Integer getPoint() {
//
//        return point.get();
//
//    }
//
//    public void setPoint(Integer point) {
//
//        this.point.set(point);
//
//    }
//
//    public ObjectProperty<Integer> dataEventIdProperty() {
//
//        return point;
//    }
//
//    private Data data;
//
//    @JoinColumn(name = "data_id", referencedColumnName = "data_id", nullable = false)
//    @ManyToOne(optional = false)
//    public Data getData() {
//
//        return data;
//
//    }
//
//    public void setData(Data data) {
//
//        this.data = data;
//
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (dataGraphicId != null ? dataGraphicId.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof DataGraphic)) {
//            return false;
//        }
//        DataGraphic other = (DataGraphic) object;
//        if ((this.dataGraphicId == null && other.dataGraphicId != null) || (this.dataGraphicId != null && !this.dataGraphicId.equals(other.dataGraphicId))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "ofm.model.entities.DataGraphic[ dataGraphicId=" + dataGraphicId + " - data=" + data.toString() + " ]";
//    }
    private ObjectProperty<Long> dataGraphicId;
    private Long _dataGraphicId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "data_graphic_id", nullable = false)
    public Long getDataGraphicId() {
        if (dataGraphicId == null) {
            return _dataGraphicId;
        } else {
            return dataGraphicId.get();
        }
    }

    public void setDataGraphicId(Long dataGraphicId) {
        if (this.dataGraphicId == null) {
            this._dataGraphicId = dataGraphicId;
        } else {
            this.dataGraphicId.set(dataGraphicId);
        }
    }

    public ObjectProperty<Long> dataGraphicIdProperty() {
        if (dataGraphicId == null) {
            dataGraphicId = new SimpleObjectProperty<>(_dataGraphicId);
        }
        return dataGraphicId;
    }

    private ObjectProperty<Integer> point;
    private Integer _point;

    @Basic(optional = false)
    @Column(name = "point", nullable = false)
    public Integer getPoint() {
        if (point == null) {
            return _point;
        } else {
            return point.get();
        }
    }

    public void setPoint(Integer point) {
        if (this.point == null) {
            this._point = point;
        } else {
            this.point.set(point);
        }
    }

    public ObjectProperty<Integer> dataEventIdProperty() {
        if (point == null) {
            point = new SimpleObjectProperty<>( _point);
        }
        return point;
    }

    private ObjectProperty<Data> data;
    private Data _data;

    @JoinColumn(name = "data_id", referencedColumnName = "data_id", nullable = false)
    @ManyToOne(optional = false)
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
        int hash = 0;
        hash += (_dataGraphicId != null ? _dataGraphicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataGraphic)) {
            return false;
        }
        DataGraphic other = (DataGraphic) object;
        if ((this._dataGraphicId == null && other._dataGraphicId != null) || (this._dataGraphicId != null && !this._dataGraphicId.equals(other._dataGraphicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ofm.model.entities.DataGraphic[ dataGraphicId=" + _dataGraphicId + " - data=" + _data.toString() + " ]";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getDataGraphicId());
        out.writeObject(getPoint());
        out.writeObject(getData());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setDataGraphicId((Long) in.readObject());
        setPoint((Integer) in.readObject());
        setData((Data) in.readObject());
    }

}
