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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author joshua
 */
@Entity
@Table(catalog = "ofm", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"device_id"})})
@NamedQueries({
    @NamedQuery(name = "Parameter.findAll", query = "SELECT p FROM Parameter p"),
    @NamedQuery(name = "Parameter.findByParameterId", query = "SELECT p FROM Parameter p WHERE p.parameterId = :parameterId"),
    @NamedQuery(name = "Parameter.findByMeasuringRangeOfTest", query = "SELECT p FROM Parameter p WHERE p.measuringRangeOfTest = :measuringRangeOfTest"),
    @NamedQuery(name = "Parameter.findByTestPulseWidth", query = "SELECT p FROM Parameter p WHERE p.testPulseWidth = :testPulseWidth"),
    @NamedQuery(name = "Parameter.findByMeasuringTime", query = "SELECT p FROM Parameter p WHERE p.measuringTime = :measuringTime"),
    @NamedQuery(name = "Parameter.findByTestWaveLength", query = "SELECT p FROM Parameter p WHERE p.testWaveLength = :testWaveLength"),
    @NamedQuery(name = "Parameter.findByMeasureMode", query = "SELECT p FROM Parameter p WHERE p.measureMode = :measureMode"),
    @NamedQuery(name = "Parameter.findByRefractiveIndex", query = "SELECT p FROM Parameter p WHERE p.refractiveIndex = :refractiveIndex"),
    @NamedQuery(name = "Parameter.findByNonReflactionThreshold", query = "SELECT p FROM Parameter p WHERE p.nonReflactionThreshold = :nonReflactionThreshold"),
    @NamedQuery(name = "Parameter.findByEndThreshold", query = "SELECT p FROM Parameter p WHERE p.endThreshold = :endThreshold"),
    @NamedQuery(name = "Parameter.findByReflectionThreshold", query = "SELECT p FROM Parameter p WHERE p.reflectionThreshold = :reflectionThreshold"),
    @NamedQuery(name = "Parameter.findByOptimizeMode", query = "SELECT p FROM Parameter p WHERE p.optimizeMode = :optimizeMode"),
    @NamedQuery(name = "Parameter.findByEnabledRefresh", query = "SELECT p FROM Parameter p WHERE p.enabledRefresh = :enabledRefresh"),
    @NamedQuery(name = "Parameter.findByRefreshCycle", query = "SELECT p FROM Parameter p WHERE p.refreshCycle = :refreshCycle"),
    @NamedQuery(name = "Parameter.findByCycleTime", query = "SELECT p FROM Parameter p WHERE p.cycleTime = :cycleTime"),
    @NamedQuery(name = "Parameter.findByCreateTime", query = "SELECT p FROM Parameter p WHERE p.createTime = :createTime"),
    @NamedQuery(name = "Parameter.findByUpdateTime", query = "SELECT p FROM Parameter p WHERE p.updateTime = :updateTime")})
public class Parameter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "parameter_id", nullable = false)
    private Integer parameterId;
    @Basic(optional = false)
    @Column(name = "measuring_range_of_test", nullable = false)
    private int measuringRangeOfTest;
    @Basic(optional = false)
    @Column(name = "test_pulse_width", nullable = false)
    private int testPulseWidth;
    @Basic(optional = false)
    @Column(name = "measuring_time", nullable = false)
    private int measuringTime;
    @Basic(optional = false)
    @Column(name = "test_wave_length", nullable = false)
    private int testWaveLength;
    @Basic(optional = false)
    @Column(name = "measure_mode", nullable = false)
    private int measureMode;
    @Basic(optional = false)
    @Column(name = "refractive_index", nullable = false)
    private float refractiveIndex;
    @Basic(optional = false)
    @Column(name = "non_reflaction_threshold", nullable = false)
    private float nonReflactionThreshold;
    @Basic(optional = false)
    @Column(name = "end_threshold", nullable = false)
    private float endThreshold;
    @Basic(optional = false)
    @Column(name = "reflection_threshold", nullable = false)
    private float reflectionThreshold;
    @Basic(optional = false)
    @Column(name = "optimize_mode", nullable = false)
    private int optimizeMode;
    @Basic(optional = false)
    @Column(name = "enabled_refresh", nullable = false)
    private int enabledRefresh;
    @Basic(optional = false)
    @Column(name = "refresh_cycle", nullable = false)
    private int refreshCycle;
    @Basic(optional = false)
    @Column(name = "cycle_time", nullable = false)
    private int cycleTime;
    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Device device;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    public Parameter() {
    }

    public Parameter(int measuringRangeOfTest, int testPulseWidth, int measuringTime, int testWaveLength, int measureMode, float refractiveIndex, float nonReflactionThreshold, float endThreshold, float reflectionThreshold, int optimizeMode, int enabledRefresh, int refreshCycle, int cycleTime, Date createTime) {
        this.measuringRangeOfTest = measuringRangeOfTest;
        this.testPulseWidth = testPulseWidth;
        this.measuringTime = measuringTime;
        this.testWaveLength = testWaveLength;
        this.measureMode = measureMode;
        this.refractiveIndex = refractiveIndex;
        this.nonReflactionThreshold = nonReflactionThreshold;
        this.endThreshold = endThreshold;
        this.reflectionThreshold = reflectionThreshold;
        this.optimizeMode = optimizeMode;
        this.enabledRefresh = enabledRefresh;
        this.refreshCycle = refreshCycle;
        this.cycleTime = cycleTime;
        this.createTime = createTime;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public int getMeasuringRangeOfTest() {
        return measuringRangeOfTest;
    }

    public void setMeasuringRangeOfTest(int measuringRangeOfTest) {
        this.measuringRangeOfTest = measuringRangeOfTest;
    }

    public int getTestPulseWidth() {
        return testPulseWidth;
    }

    public void setTestPulseWidth(int testPulseWidth) {
        this.testPulseWidth = testPulseWidth;
    }

    public int getMeasuringTime() {
        return measuringTime;
    }

    public void setMeasuringTime(int measuringTime) {
        this.measuringTime = measuringTime;
    }

    public int getTestWaveLength() {
        return testWaveLength;
    }

    public void setTestWaveLength(int testWaveLength) {
        this.testWaveLength = testWaveLength;
    }

    public int getMeasureMode() {
        return measureMode;
    }

    public void setMeasureMode(int measureMode) {
        this.measureMode = measureMode;
    }

    public float getRefractiveIndex() {
        return refractiveIndex;
    }

    public void setRefractiveIndex(float refractiveIndex) {
        this.refractiveIndex = refractiveIndex;
    }

    public float getNonReflactionThreshold() {
        return nonReflactionThreshold;
    }

    public void setNonReflactionThreshold(float nonReflactionThreshold) {
        this.nonReflactionThreshold = nonReflactionThreshold;
    }

    public float getEndThreshold() {
        return endThreshold;
    }

    public void setEndThreshold(float endThreshold) {
        this.endThreshold = endThreshold;
    }

    public float getReflectionThreshold() {
        return reflectionThreshold;
    }

    public void setReflectionThreshold(float reflectionThreshold) {
        this.reflectionThreshold = reflectionThreshold;
    }

    public int getOptimizeMode() {
        return optimizeMode;
    }

    public void setOptimizeMode(int optimizeMode) {
        this.optimizeMode = optimizeMode;
    }

    public int getEnabledRefresh() {
        return enabledRefresh;
    }

    public void setEnabledRefresh(int enabledRefresh) {
        this.enabledRefresh = enabledRefresh;
    }

    public int getRefreshCycle() {
        return refreshCycle;
    }

    public void setRefreshCycle(int refreshCycle) {
        this.refreshCycle = refreshCycle;
    }

    public int getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parameterId != null ? parameterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parameter)) {
            return false;
        }
        Parameter other = (Parameter) object;
        if ((this.parameterId == null && other.parameterId != null) || (this.parameterId != null && !this.parameterId.equals(other.parameterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.net.multiway.ofm.entities.Parameter[ parameterId=" + parameterId + " ]";
    }
    
}
