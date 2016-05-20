/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.entities;

import com.net.multiway.ofm.utils.Utils;
import java.io.Serializable;
import java.util.Date;
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

    private Integer parameterId;

    private ObjectProperty<Integer> measuringRangeOfTest;

    private ObjectProperty<Integer> testPulseWidth;

    private ObjectProperty<Integer> measuringTime;

    private ObjectProperty<Integer> testWaveLength;

    private ObjectProperty<Integer> measureMode;

    private ObjectProperty<Float> refractiveIndex;

    private ObjectProperty<Float> nonReflactionThreshold;

    private ObjectProperty<Float> endThreshold;

    private ObjectProperty<Float> reflectionThreshold;

    private ObjectProperty<Integer> optimizeMode;

    private ObjectProperty<Integer> enabledRefresh;

    private ObjectProperty<Integer> refreshCycle;

    private ObjectProperty<Integer> cycleTime;

    private Date createTime;

    private Date updateTime;

    private Device device;

    private User user;

    public Parameter() {
        this.measuringRangeOfTest = new SimpleObjectProperty<>();
        this.testPulseWidth = new SimpleObjectProperty<>();
        this.measuringTime = new SimpleObjectProperty<>();
        this.testWaveLength = new SimpleObjectProperty<>();
        this.measureMode = new SimpleObjectProperty<>();
        this.refractiveIndex = new SimpleObjectProperty<>();
        this.nonReflactionThreshold = new SimpleObjectProperty<>();
        this.endThreshold = new SimpleObjectProperty<>();
        this.reflectionThreshold = new SimpleObjectProperty<>();

        this.optimizeMode = new SimpleObjectProperty<>();
        this.enabledRefresh = new SimpleObjectProperty<>();
        this.refreshCycle = new SimpleObjectProperty<>();
        this.cycleTime = new SimpleObjectProperty<>();
    }

    public Parameter(int measuringRangeOfTest, int testPulseWidth, int measuringTime,
            int testWaveLength, int measureMode, float refractiveIndex,
            float nonReflactionThreshold, float endThreshold, float reflectionThreshold,
            int optimizeMode, int enabledRefresh, int refreshCycle, int cycleTime, Date createTime) {
        this.measuringRangeOfTest = new SimpleObjectProperty<>(measuringRangeOfTest);
        this.testPulseWidth = new SimpleObjectProperty<>(testPulseWidth);
        this.measuringTime = new SimpleObjectProperty<>(measuringTime);
        this.testWaveLength = new SimpleObjectProperty<>(testWaveLength);
        this.measureMode = new SimpleObjectProperty<>(measureMode);
        this.refractiveIndex = new SimpleObjectProperty<>(refractiveIndex);
        this.nonReflactionThreshold = new SimpleObjectProperty<>(nonReflactionThreshold);
        this.endThreshold = new SimpleObjectProperty<>(endThreshold);
        this.reflectionThreshold = new SimpleObjectProperty<>(reflectionThreshold);

        this.optimizeMode = new SimpleObjectProperty<>(optimizeMode);
        this.enabledRefresh = new SimpleObjectProperty<>(enabledRefresh);
        this.refreshCycle = new SimpleObjectProperty<>(refreshCycle);
        this.cycleTime = new SimpleObjectProperty<>(cycleTime);
        this.createTime = createTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "parameter_id", nullable = false)
    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    @Basic(optional = false)
    @Column(name = "measure_mode", nullable = false)

    public Integer getMeasureMode() {
        return measureMode.get();
    }

    public void setMeasureMode(int measureMode) {
        this.measureMode.set(measureMode);
    }

    @Basic(optional = false)
    @Column(name = "optimize_mode", nullable = false)
    public Integer getOptimizeMode() {
        return optimizeMode.get();
    }

    public void setOptimizeMode(int optimizeMode) {
        this.optimizeMode.set(optimizeMode);
    }

    @Basic(optional = false)
    @Column(name = "reflection_threshold", nullable = false)
    public Float getReflectionThreshold() {
        return reflectionThreshold.get();
    }

    public void setReflectionThreshold(float reflectionThreshold) {
        this.reflectionThreshold.set(reflectionThreshold);
    }

    @Basic(optional = false)
    @Column(name = "enable_refresh", nullable = false)
    public Integer getEnabledRefresh() {
        return enabledRefresh.get();
    }

    public void setEnabledRefresh(int enabledRefresh) {
        this.enabledRefresh.set(enabledRefresh);
    }

    @Basic(optional = false)
    @Column(name = "refresh_cycle", nullable = false)
    public Integer getRefreshCycle() {
        return refreshCycle.get();
    }

    public void setRefreshCycle(int refreshCycle) {
        this.refreshCycle.set(refreshCycle);
    }

    @Basic(optional = false)
    @Column(name = "test_wave_length", nullable = false)
    public Integer getTestWaveLength() {
        return testWaveLength.get();
    }

    public void setTestWaveLength(int testWaveLength) {
        this.testWaveLength.set(testWaveLength);
    }

    @Basic(optional = false)
    @Column(name = "measuring_range", nullable = false)
    public Integer getMeasuringRangeOfTest() {
        return measuringRangeOfTest.get();
    }

    public void setMeasuringRangeOfTest(int measuringRangeOfTest) {
        this.measuringRangeOfTest.set(measuringRangeOfTest);
    }

    @Basic(optional = false)
    @Column(name = "test_pulse_width", nullable = false)
    public Integer getTestPulseWidth() {
        return testPulseWidth.get();
    }

    public void setTestPulseWidth(int testPulseWidth) {
        this.testPulseWidth.set(testPulseWidth);
    }

    @Basic(optional = false)
    @Column(name = "measuring_time", nullable = false)
    public Integer getMeasuringTime() {
        return measuringTime.get();
    }

    public void setMeasuringTime(int measuringTime) {
        this.measuringTime.set(measuringTime);
    }

    @Basic(optional = false)
    @Column(name = "refractive_index", nullable = false)
    public Float getRefractiveIndex() {
        return refractiveIndex.get();
    }

    public void setRefractiveIndex(float refractiveIndex) {
        this.refractiveIndex.set(refractiveIndex);
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
    @Column(name = "non_reflaction_threshold", nullable = false)
    public Float getNonReflactionThreshold() {
        return nonReflactionThreshold.get();
    }

    public void setNonReflactionThreshold(float nonReflactionThreshold) {
        this.nonReflactionThreshold.set(nonReflactionThreshold);
    }

    @Basic(optional = false)
    @Column(name = "cycle_time", nullable = false)
    public Integer getCycleTime() {
        return cycleTime.get();
    }

    public void setCycleTime(int cycleTime) {
        this.cycleTime.set(cycleTime);
    }

    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
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

    public byte[] takeData() {
        byte[] b = new byte[48];
        byte[] c = new byte[4];
        int i = 0;

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.intToByteArray(measureMode.get())[j];
        }

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.intToByteArray(optimizeMode.get())[j];
        }

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.floatToByteArray(reflectionThreshold.get())[j];
        }

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.intToByteArray(enabledRefresh.get())[j];
        }

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.intToByteArray(refreshCycle.get())[j];
        }

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.intToByteArray(testWaveLength.get())[j];
        }

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.intToByteArray(measuringRangeOfTest.get())[j];
        }

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.intToByteArray(testPulseWidth.get())[j];
        }

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.intToByteArray(measuringTime.get())[j];
        }

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.floatToByteArray(refractiveIndex.get())[j];
        }

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.floatToByteArray(endThreshold.get())[j];
        }

        for (int j = 0; j < 4; j++, i++) {
            b[i] = Utils.floatToByteArray(nonReflactionThreshold.get())[j];
        }

        return b;

    }

    public void copy(Parameter data) {
        setEnabledRefresh(data.getEnabledRefresh());
        setEndThreshold(data.getEndThreshold());
        //setID(data.getClass());
        setMeasureMode(data.getMeasureMode());
        setMeasuringRangeOfTest(data.getMeasuringRangeOfTest());
        setMeasuringTime(data.getMeasuringTime());
        setNonReflactionThreshold(data.getNonReflactionThreshold());
        setOptimizeMode(data.getOptimizeMode());
        setReflectionThreshold(data.getReflectionThreshold());
        setRefractiveIndex(data.getRefractiveIndex());
        setRefreshCycle(data.getRefreshCycle());
        setTestPulseWidth(data.getTestPulseWidth());
        setTestWaveLength(data.getTestWaveLength());
    }

}
