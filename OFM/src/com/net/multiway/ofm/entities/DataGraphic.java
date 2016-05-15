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
 * @author joshua
 */
@Entity
@Table(name = "data_graphic", catalog = "ofm", schema = "")
@NamedQueries({
    @NamedQuery(name = "DataGraphic.findAll", query = "SELECT d FROM DataGraphic d"),
    @NamedQuery(name = "DataGraphic.findByDataGraphicId", query = "SELECT d FROM DataGraphic d WHERE d.dataGraphicId = :dataGraphicId"),
    @NamedQuery(name = "DataGraphic.findByValue", query = "SELECT d FROM DataGraphic d WHERE d.value = :value")})
public class DataGraphic implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "data_graphic_id", nullable = false)
    private Long dataGraphicId;
    @Basic(optional = false)
    @Column(nullable = false)
    private int value;
    @JoinColumn(name = "data_id", referencedColumnName = "data_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Data data;

    public DataGraphic() {
    }

    public DataGraphic(Long dataGraphicId) {
        this.dataGraphicId = dataGraphicId;
    }

    public DataGraphic(Long dataGraphicId, int value) {
        this.dataGraphicId = dataGraphicId;
        this.value = value;
    }

    public Long getDataGraphicId() {
        return dataGraphicId;
    }

    public void setDataGraphicId(Long dataGraphicId) {
        this.dataGraphicId = dataGraphicId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
        hash += (dataGraphicId != null ? dataGraphicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataGraphic)) {
            return false;
        }
        DataGraphic other = (DataGraphic) object;
        if ((this.dataGraphicId == null && other.dataGraphicId != null) || (this.dataGraphicId != null && !this.dataGraphicId.equals(other.dataGraphicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.net.multiway.ofm.entities.DataGraphic[ dataGraphicId=" + dataGraphicId + " ]";
    }
    
}
