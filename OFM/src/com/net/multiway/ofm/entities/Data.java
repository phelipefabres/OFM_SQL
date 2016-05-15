/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "data_id", nullable = false)
    private Integer dataId;
    @Basic(optional = false)
    @Column(name = "sample_frequency", nullable = false)
    private int sampleFrequency;
    @Basic(optional = false)
    @Column(name = "range_of_test", nullable = false)
    private int rangeOfTest;
    @Basic(optional = false)
    @Column(name = "pulse_width", nullable = false)
    private int pulseWidth;
    @Basic(optional = false)
    @Column(name = "wave_length", nullable = false)
    private int waveLength;
    @Basic(optional = false)
    @Column(name = "test_time", nullable = false)
    private int testTime;
    @Basic(optional = false)
    @Column(name = "group_refractive_index", nullable = false)
    private float groupRefractiveIndex;
    @Basic(optional = false)
    @Column(name = "link_length", nullable = false)
    private float linkLength;
    @Basic(optional = false)
    @Column(name = "link_loss", nullable = false)
    private float linkLoss;
    @Basic(optional = false)
    @Column(name = "link_attenuation", nullable = false)
    private float linkAttenuation;
    @Basic(optional = false)
    @Column(name = "non_reflecting_threshold", nullable = false)
    private float nonReflectingThreshold;
    @Basic(optional = false)
    @Column(name = "end_threshold", nullable = false)
    private float endThreshold;
    @Basic(optional = false)
    @Column(name = "test_mode", nullable = false)
    private float testMode;
    @Basic(optional = false)
    @Column(name = "test_way", nullable = false)
    private int testWay;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "data", fetch = FetchType.EAGER)
    private List<DataEvent> dataEventList;
    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Device device;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "data", fetch = FetchType.EAGER)
    private List<DataGraphic> dataGraphicList;

    public Data() {
    }

    public Data(int sampleFrequency, int rangeOfTest, int pulseWidth, int waveLength, int testTime, float groupRefractiveIndex, float linkLength, float linkLoss, float linkAttenuation, float nonReflectingThreshold, float endThreshold, float testMode, int testWay) {
        this.sampleFrequency = sampleFrequency;
        this.rangeOfTest = rangeOfTest;
        this.pulseWidth = pulseWidth;
        this.waveLength = waveLength;
        this.testTime = testTime;
        this.groupRefractiveIndex = groupRefractiveIndex;
        this.linkLength = linkLength;
        this.linkLoss = linkLoss;
        this.linkAttenuation = linkAttenuation;
        this.nonReflectingThreshold = nonReflectingThreshold;
        this.endThreshold = endThreshold;
        this.testMode = testMode;
        this.testWay = testWay;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public int getSampleFrequency() {
        return sampleFrequency;
    }

    public void setSampleFrequency(int sampleFrequency) {
        this.sampleFrequency = sampleFrequency;
    }

    public int getRangeOfTest() {
        return rangeOfTest;
    }

    public void setRangeOfTest(int rangeOfTest) {
        this.rangeOfTest = rangeOfTest;
    }

    public int getPulseWidth() {
        return pulseWidth;
    }

    public void setPulseWidth(int pulseWidth) {
        this.pulseWidth = pulseWidth;
    }

    public int getWaveLength() {
        return waveLength;
    }

    public void setWaveLength(int waveLength) {
        this.waveLength = waveLength;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public float getGroupRefractiveIndex() {
        return groupRefractiveIndex;
    }

    public void setGroupRefractiveIndex(float groupRefractiveIndex) {
        this.groupRefractiveIndex = groupRefractiveIndex;
    }

    public float getLinkLength() {
        return linkLength;
    }

    public void setLinkLength(float linkLength) {
        this.linkLength = linkLength;
    }

    public float getLinkLoss() {
        return linkLoss;
    }

    public void setLinkLoss(float linkLoss) {
        this.linkLoss = linkLoss;
    }

    public float getLinkAttenuation() {
        return linkAttenuation;
    }

    public void setLinkAttenuation(float linkAttenuation) {
        this.linkAttenuation = linkAttenuation;
    }

    public float getNonReflectingThreshold() {
        return nonReflectingThreshold;
    }

    public void setNonReflectingThreshold(float nonReflectingThreshold) {
        this.nonReflectingThreshold = nonReflectingThreshold;
    }

    public float getEndThreshold() {
        return endThreshold;
    }

    public void setEndThreshold(float endThreshold) {
        this.endThreshold = endThreshold;
    }

    public float getTestMode() {
        return testMode;
    }

    public void setTestMode(float testMode) {
        this.testMode = testMode;
    }

    public int getTestWay() {
        return testWay;
    }

    public void setTestWay(int testWay) {
        this.testWay = testWay;
    }

    public List<DataEvent> getDataEventList() {
        return dataEventList;
    }

    public void setDataEventList(List<DataEvent> dataEventList) {
        this.dataEventList = dataEventList;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

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
