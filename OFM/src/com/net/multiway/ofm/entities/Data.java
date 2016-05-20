/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author joshua
 */
@Entity
@Table(catalog = "ofm", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"device_id"})})
@NamedQueries({
    @NamedQuery(name = "Data.findAll", query = "SELECT d FROM Data d"),
    @NamedQuery(name = "Data.findByDataId", query = "SELECT d FROM Data d WHERE d.dataId = :dataId"),
    @NamedQuery(name = "Data.findBySampleFrequency", query = "SELECT d FROM Data d WHERE d.sampleFrequency = :sampleFrequency"),
    @NamedQuery(name = "Data.findByRangeOfTest", query = "SELECT d FROM Data d WHERE d.rangeOfTest = :rangeOfTest"),
    @NamedQuery(name = "Data.findByPulseWidth", query = "SELECT d FROM Data d WHERE d.pulseWidth = :pulseWidth"),
    @NamedQuery(name = "Data.findByWaveLength", query = "SELECT d FROM Data d WHERE d.waveLength = :waveLength"),
    @NamedQuery(name = "Data.findByTestTime", query = "SELECT d FROM Data d WHERE d.testTime = :testTime"),
    @NamedQuery(name = "Data.findByGroupRefractiveIndex", query = "SELECT d FROM Data d WHERE d.groupRefractiveIndex = :groupRefractiveIndex"),
    @NamedQuery(name = "Data.findByLinkLength", query = "SELECT d FROM Data d WHERE d.linkLength = :linkLength"),
    @NamedQuery(name = "Data.findByLinkLoss", query = "SELECT d FROM Data d WHERE d.linkLoss = :linkLoss"),
    @NamedQuery(name = "Data.findByLinkAttenuation", query = "SELECT d FROM Data d WHERE d.linkAttenuation = :linkAttenuation"),
    @NamedQuery(name = "Data.findByNonReflectingThreshold", query = "SELECT d FROM Data d WHERE d.nonReflectingThreshold = :nonReflectingThreshold"),
    @NamedQuery(name = "Data.findByEndThreshold", query = "SELECT d FROM Data d WHERE d.endThreshold = :endThreshold"),
    @NamedQuery(name = "Data.findByTestMode", query = "SELECT d FROM Data d WHERE d.testMode = :testMode"),
    @NamedQuery(name = "Data.findByTestWay", query = "SELECT d FROM Data d WHERE d.testWay = :testWay")})
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer dataId;
    private ObjectProperty<Integer> sampleFrequency;
    private ObjectProperty<Integer> rangeOfTest;
    private ObjectProperty<Integer> pulseWidth;
    private ObjectProperty<Integer> waveLength;
    private ObjectProperty<Integer> testTime;
    private ObjectProperty<Float> groupRefractiveIndex;
    private ObjectProperty<Float> linkLength;
    private ObjectProperty<Float> linkLoss;
    private ObjectProperty<Float> linkAttenuation;
    private ObjectProperty<Float> nonReflectingThreshold;
    private ObjectProperty<Float> endThreshold;
    private ObjectProperty<Float> testMode;
    private ObjectProperty<Integer> testWay;

    private List<DataEvent> dataEventList;

    private Device device;

    private List<DataGraphic> dataGraphicList;

    public Data() {
        this.sampleFrequency = new SimpleObjectProperty<>();
        this.rangeOfTest = new SimpleObjectProperty<>();
        this.pulseWidth = new SimpleObjectProperty<>();
        this.waveLength = new SimpleObjectProperty<>();
        this.testTime = new SimpleObjectProperty<>();
        this.groupRefractiveIndex = new SimpleObjectProperty<>();
        this.linkLength = new SimpleObjectProperty<>();
        this.linkLoss = new SimpleObjectProperty<>();
        this.linkAttenuation = new SimpleObjectProperty<>();
        this.nonReflectingThreshold = new SimpleObjectProperty<>();
        this.endThreshold = new SimpleObjectProperty<>();
        this.testMode = new SimpleObjectProperty<>();
        this.testWay = new SimpleObjectProperty<>();
        this.dataGraphicList = new ArrayList<>();
        this.dataEventList = new ArrayList<>();
    }

    public Data(int sampleFrequency, int rangeOfTest, int pulseWidth, int waveLength, int testTime, float groupRefractiveIndex, float linkLength, float linkLoss, float linkAttenuation, float nonReflectingThreshold, float endThreshold, float testMode, int testWay) {
        this.sampleFrequency = new SimpleObjectProperty<>(sampleFrequency);
        this.rangeOfTest = new SimpleObjectProperty<>(rangeOfTest);
        this.pulseWidth = new SimpleObjectProperty<>(pulseWidth);
        this.waveLength = new SimpleObjectProperty<>(waveLength);
        this.testTime = new SimpleObjectProperty<>(testTime);
        this.groupRefractiveIndex = new SimpleObjectProperty<>(groupRefractiveIndex);
        this.linkLength = new SimpleObjectProperty<>(linkLength);
        this.linkLoss = new SimpleObjectProperty<>(linkLoss);
        this.linkAttenuation = new SimpleObjectProperty<>(linkAttenuation);
        this.nonReflectingThreshold = new SimpleObjectProperty<>(nonReflectingThreshold);
        this.endThreshold = new SimpleObjectProperty<>(endThreshold);
        this.testMode = new SimpleObjectProperty<>(testMode);
        this.testWay = new SimpleObjectProperty<>(testWay);
        this.dataGraphicList = new ArrayList<>();
        this.dataEventList = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "data_id", nullable = false)
    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    @Basic(optional = false)
    @Column(name = "sample_frequency", nullable = false)
    public Integer getSampleFrequency() {
        return sampleFrequency.get();
    }

    public void setSampleFrequency(int sampleFrequency) {
        this.sampleFrequency.set(sampleFrequency);
    }

    @Basic(optional = false)
    @Column(name = "range_of_test", nullable = false)
    public Integer getRangeOfTest() {
        return rangeOfTest.get();
    }

    public void setRangeOfTest(int rangeOfTest) {
        this.rangeOfTest.set(rangeOfTest);
    }

    @Basic(optional = false)
    @Column(name = "pulse_width", nullable = false)
    public Integer getPulseWidth() {
        return pulseWidth.get();
    }

    public void setPulseWidth(int pulseWidth) {
        this.pulseWidth.set(pulseWidth);
    }

    @Basic(optional = false)
    @Column(name = "wave_length", nullable = false)
    public Integer getWaveLength() {
        return waveLength.get();
    }

    public void setWaveLength(int waveLength) {
        this.waveLength.set(waveLength);
    }

    @Basic(optional = false)
    @Column(name = "test_time", nullable = false)
    public Integer getTestTime() {
        return testTime.get();
    }

    public void setTestTime(int testTime) {
        this.testTime.set(testTime);
    }

    @Basic(optional = false)
    @Column(name = "group_refractive_index", nullable = false)
    public Float getGroupRefractiveIndex() {
        return groupRefractiveIndex.get();
    }

    public void setGroupRefractiveIndex(float groupRefractiveIndex) {
        this.groupRefractiveIndex.set(groupRefractiveIndex);
    }

    @Basic(optional = false)
    @Column(name = "link_length", nullable = false)
    public Float getLinkLength() {
        return linkLength.get();
    }

    public void setLinkLength(float linkLength) {
        this.linkLength.set(linkLength);
    }

    @Basic(optional = false)
    @Column(name = "link_loss", nullable = false)
    public Float getLinkLoss() {
        return linkLoss.get();
    }

    public void setLinkLoss(float linkLoss) {
        this.linkLoss.set(linkLoss);
    }

    @Basic(optional = false)
    @Column(name = "link_attenuation", nullable = false)
    public Float getLinkAttenuation() {
        return linkAttenuation.get();
    }

    public void setLinkAttenuation(float linkAttenuation) {
        this.linkAttenuation.set(linkAttenuation);
    }

    @Basic(optional = false)
    @Column(name = "non_reflecting_threshold", nullable = false)
    public Float getNonReflectingThreshold() {
        return nonReflectingThreshold.get();
    }

    public void setNonReflectingThreshold(float nonReflectingThreshold) {
        this.nonReflectingThreshold.set(nonReflectingThreshold);
    }

    @Basic(optional = false)
    @Column(name = "end_threshold", nullable = false)
    public Float getEndThreshold() {
        return endThreshold.get();
    }

    public void setEndThreshold(float endThreshold) {
        this.endThreshold.set(endThreshold);
    }

    @Basic(optional = false)
    @Column(name = "test_mode", nullable = false)
    public Float getTestMode() {
        return testMode.get();
    }

    public void setTestMode(float testMode) {
        this.testMode.set(testMode);
    }

    @Basic(optional = false)
    @Column(name = "test_way", nullable = false)
    public Integer getTestWay() {
        return testWay.get();
    }

    public void setTestWay(int testWay) {
        this.testWay.set(testWay);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "data", fetch = FetchType.LAZY)
    public List<DataEvent> getDataEventList() {
        return dataEventList;
    }

    public void addEvents(DataEvent events) {
        this.dataEventList.add(events);
    }

    public void setDataEventList(List<DataEvent> dataEventList) {
        this.dataEventList = dataEventList;
    }

    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "data", fetch = FetchType.LAZY)
    public List<DataGraphic> getDataGraphicList() {
        return dataGraphicList;
    }

    public void setDataGraphicList(List<DataGraphic> dataGraphicList) {
        this.dataGraphicList = dataGraphicList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataId != null ? dataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Data)) {
            return false;
        }
        Data other = (Data) object;
        if ((this.dataId == null && other.dataId != null) || (this.dataId != null && !this.dataId.equals(other.dataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.net.multiway.ofm.entities.Data[ dataId=" + dataId + " ]";
    }

}
