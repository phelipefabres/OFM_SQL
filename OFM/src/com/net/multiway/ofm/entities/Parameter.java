/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.entities;

import com.net.multiway.ofm.utils.Utils;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author joshua
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "parameter", uniqueConstraints = {
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
public class Parameter implements Externalizable {

    private static final long serialVersionUID = 1L;

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
        this.createTime = new SimpleObjectProperty<>();
    }

    public Parameter(Integer parameterId, int measuringRangeOfTest, int testPulseWidth, int measuringTime,
            int testWaveLength, int measureMode, float refractiveIndex,
            float nonReflactionThreshold, float endThreshold, float reflectionThreshold,
            int optimizeMode, int enabledRefresh, int refreshCycle, int cycleTime, Date createTime) {
        this.parameterId = new SimpleObjectProperty<>(parameterId);
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
        this.createTime = new SimpleObjectProperty<>(createTime);
        this._parameterId = parameterId;
        this._measuringRangeOfTest = measuringRangeOfTest;
        this._testPulseWidth = testPulseWidth;
        this._measuringTime = measuringTime;
        this._testWaveLength = testWaveLength;
        this._measureMode = measureMode;
        this._refractiveIndex = refractiveIndex;
        this._nonReflactionThreshold = nonReflactionThreshold;
        this._endThreshold = endThreshold;
        this._reflectionThreshold = reflectionThreshold;
        this._optimizeMode = optimizeMode;
        this._enabledRefresh = enabledRefresh;
        this._refreshCycle = refreshCycle;
        this._cycleTime = cycleTime;
        this._createTime = createTime;
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
        this.createTime = new SimpleObjectProperty<>(createTime);
        this._measuringRangeOfTest = measuringRangeOfTest;
        this._testPulseWidth = testPulseWidth;
        this._measuringTime = measuringTime;
        this._testWaveLength = testWaveLength;
        this._measureMode = measureMode;
        this._refractiveIndex = refractiveIndex;
        this._nonReflactionThreshold = nonReflactionThreshold;
        this._endThreshold = endThreshold;
        this._reflectionThreshold = reflectionThreshold;
        this._optimizeMode = optimizeMode;
        this._enabledRefresh = enabledRefresh;
        this._refreshCycle = refreshCycle;
        this._cycleTime = cycleTime;
        this._createTime = createTime;
    }

    private ObjectProperty<Integer> parameterId;
    private Integer _parameterId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "parameter_id", nullable = false)
    public Integer getParameterId() {
        if (parameterId == null) {
            return _parameterId;
        } else {
            return parameterId.get();
        }
    }

    public void setParameterId(Integer parameterId) {
        if (this.parameterId == null) {
            this._parameterId = parameterId;
        } else {
            this.parameterId.set(parameterId);
        }
    }

    public ObjectProperty<Integer> parameterIdProperty() {
        if (parameterId == null) {
            parameterId = new SimpleObjectProperty<>(_parameterId);
        }
        return parameterId;
    }

    private ObjectProperty<Integer> measuringRangeOfTest;
    private Integer _measuringRangeOfTest;

    @Basic(optional = false)
    @Column(name = "measuring_range_of_test", nullable = false)
    public Integer getMeasuringRangeOfTest() {
        if (measuringRangeOfTest == null) {
            return _measuringRangeOfTest;
        } else {
            return measuringRangeOfTest.get();
        }
    }

    public void setMeasuringRangeOfTest(Integer measuringRangeOfTest) {
        if (this.measuringRangeOfTest == null) {
            this._measuringRangeOfTest = measuringRangeOfTest;
        } else {
            this.measuringRangeOfTest.set(measuringRangeOfTest);
        }
    }

    public ObjectProperty<Integer> measuringRangeOfTestProperty() {
        if (measuringRangeOfTest == null) {
            measuringRangeOfTest = new SimpleObjectProperty<>(_measuringRangeOfTest);
        }
        return measuringRangeOfTest;
    }

    private ObjectProperty<Integer> testPulseWidth;
    private Integer _testPulseWidth;

    @Basic(optional = false)
    @Column(name = "test_pulse_width", nullable = false)
    public Integer getTestPulseWidth() {
        if (testPulseWidth == null) {
            return _testPulseWidth;
        } else {
            return testPulseWidth.get();
        }
    }

    public void setTestPulseWidth(Integer testPulseWidth) {
        if (this.testPulseWidth == null) {
            this._testPulseWidth = testPulseWidth;
        } else {
            this.testPulseWidth.set(testPulseWidth);
        }
    }

    public ObjectProperty<Integer> testPulseWidthProperty() {
        if (testPulseWidth == null) {
            testPulseWidth = new SimpleObjectProperty<>(_testPulseWidth);
        }
        return testPulseWidth;
    }

    private ObjectProperty<Integer> measuringTime;
    private Integer _measuringTime;

    @Basic(optional = false)
    @Column(name = "measuring_time", nullable = false)
    public Integer getMeasuringTime() {
        if (measuringTime == null) {
            return _measuringTime;
        } else {
            return measuringTime.get();
        }
    }

    public void setMeasuringTime(Integer measuringTime) {
        if (this.measuringTime == null) {
            this._measuringTime = measuringTime;
        } else {
            this.measuringTime.set(measuringTime);
        }
    }

    public ObjectProperty<Integer> measuringTimeProperty() {
        if (measuringTime == null) {
            measuringTime = new SimpleObjectProperty<>(_measuringTime);
        }
        return measuringTime;
    }

    private ObjectProperty<Integer> testWaveLength;
    private Integer _testWaveLength;

    @Basic(optional = false)
    @Column(name = "test_wave_length", nullable = false)
    public Integer getTestWaveLength() {
        if (testWaveLength == null) {
            return _testWaveLength;
        } else {
            return testWaveLength.get();
        }
    }

    public void setTestWaveLength(Integer testWaveLength) {
        if (this.testWaveLength == null) {
            this._testWaveLength = testWaveLength;
        } else {
            this.testWaveLength.set(testWaveLength);
        }
    }

    public ObjectProperty<Integer> testWaveLengthProperty() {
        if (testWaveLength == null) {
            testWaveLength = new SimpleObjectProperty<>(_testWaveLength);
        }
        return testWaveLength;
    }

    private ObjectProperty<Integer> measureMode;
    private Integer _measureMode;

    @Basic(optional = false)
    @Column(name = "measure_mode", nullable = false)
    public Integer getMeasureMode() {
        if (measureMode == null) {
            return _measureMode;
        } else {
            return measureMode.get();
        }
    }

    public void setMeasureMode(Integer measureMode) {
        if (this.measureMode == null) {
            this._measureMode = measureMode;
        } else {
            this.measureMode.set(measureMode);
        }
    }

    public ObjectProperty<Integer> measureModeProperty() {
        if (measureMode == null) {
            measureMode = new SimpleObjectProperty<>(_measureMode);
        }
        return measureMode;
    }

    private ObjectProperty<Float> refractiveIndex;
    private Float _refractiveIndex;

    @Basic(optional = false)
    @Column(name = "refractive_index", nullable = false)
    public Float getRefractiveIndex() {
        if (refractiveIndex == null) {
            return _refractiveIndex;
        } else {
            return refractiveIndex.get();
        }
    }

    public void setRefractiveIndex(Float refractiveIndex) {
        if (this.refractiveIndex == null) {
            this._refractiveIndex = refractiveIndex;
        } else {
            this.refractiveIndex.set(refractiveIndex);
        }
    }

    public ObjectProperty<Float> refractiveIndexProperty() {
        if (refractiveIndex == null) {
            refractiveIndex = new SimpleObjectProperty<>(_refractiveIndex);
        }
        return refractiveIndex;
    }

    private ObjectProperty<Float> nonReflactionThreshold;
    private Float _nonReflactionThreshold;

    @Basic(optional = false)
    @Column(name = "non_reflaction_threshold", nullable = false)
    public Float getNonReflactionThreshold() {
        if (nonReflactionThreshold == null) {
            return _nonReflactionThreshold;
        } else {
            return nonReflactionThreshold.get();
        }
    }

    public void setNonReflactionThreshold(Float nonReflactionThreshold) {
        if (this.nonReflactionThreshold == null) {
            this._nonReflactionThreshold = nonReflactionThreshold;
        } else {
            this.nonReflactionThreshold.set(nonReflactionThreshold);
        }
    }

    public ObjectProperty<Float> nonReflactionThresholdProperty() {
        if (nonReflactionThreshold == null) {
            nonReflactionThreshold = new SimpleObjectProperty<>(_nonReflactionThreshold);
        }
        return nonReflactionThreshold;
    }

    private ObjectProperty<Float> endThreshold;
    private Float _endThreshold;

    @Basic(optional = false)
    @Column(name = "end_threshold", nullable = false)
    public Float getEndThreshold() {
        if (endThreshold == null) {
            return _endThreshold;
        } else {
            return endThreshold.get();
        }
    }

    public void setEndThreshold(Float endThreshold) {
        if (this.endThreshold == null) {
            this._endThreshold = endThreshold;
        } else {
            this.endThreshold.set(endThreshold);
        }
    }

    public ObjectProperty<Float> endThresholdProperty() {
        if (endThreshold == null) {
            endThreshold = new SimpleObjectProperty<>(_endThreshold);
        }
        return endThreshold;
    }

    private ObjectProperty<Float> reflectionThreshold;
    private Float _reflectionThreshold;

    @Basic(optional = false)
    @Column(name = "reflection_threshold", nullable = false)
    public Float getReflectionThreshold() {
        if (reflectionThreshold == null) {
            return _reflectionThreshold;
        } else {
            return reflectionThreshold.get();
        }
    }

    public void setReflectionThreshold(Float reflectionThreshold) {
        if (this.reflectionThreshold == null) {
            this._reflectionThreshold = reflectionThreshold;
        } else {
            this.reflectionThreshold.set(reflectionThreshold);
        }
    }

    public ObjectProperty<Float> reflectionThresholdProperty() {
        if (reflectionThreshold == null) {
            reflectionThreshold = new SimpleObjectProperty<>(_reflectionThreshold);
        }
        return reflectionThreshold;
    }

    private ObjectProperty<Integer> optimizeMode;
    private Integer _optimizeMode;

    @Basic(optional = false)
    @Column(name = "optimize_mode", nullable = false)
    public Integer getOptimizeMode() {
        if (optimizeMode == null) {
            return _optimizeMode;
        } else {
            return optimizeMode.get();
        }
    }

    public void setOptimizeMode(Integer optimizeMode) {
        if (this.optimizeMode == null) {
            this._optimizeMode = optimizeMode;
        } else {
            this.optimizeMode.set(optimizeMode);
        }
    }

    public ObjectProperty<Integer> optimizeModeProperty() {
        if (optimizeMode == null) {
            optimizeMode = new SimpleObjectProperty<>(_optimizeMode);
        }
        return optimizeMode;
    }

    private ObjectProperty<Integer> enabledRefresh;
    private Integer _enabledRefresh;

    @Basic(optional = false)
    @Column(name = "enabled_refresh", nullable = false)
    public Integer getEnabledRefresh() {
        if (enabledRefresh == null) {
            return _enabledRefresh;
        } else {
            return enabledRefresh.get();
        }
    }

    public void setEnabledRefresh(Integer enabledRefresh) {
        if (this.enabledRefresh == null) {
            this._enabledRefresh = enabledRefresh;
        } else {
            this.enabledRefresh.set(enabledRefresh);
        }
    }

    public ObjectProperty<Integer> enabledRefreshProperty() {
        if (enabledRefresh == null) {
            enabledRefresh = new SimpleObjectProperty<>(_enabledRefresh);
        }
        return enabledRefresh;
    }

    private ObjectProperty<Integer> refreshCycle;
    private Integer _refreshCycle;

    @Basic(optional = false)
    @Column(name = "refresh_cycle", nullable = false)
    public Integer getRefreshCycle() {
        if (refreshCycle == null) {
            return _refreshCycle;
        } else {
            return refreshCycle.get();
        }
    }

    public void setRefreshCycle(Integer refreshCycle) {
        if (this.refreshCycle == null) {
            this._refreshCycle = refreshCycle;
        } else {
            this.refreshCycle.set(refreshCycle);
        }
    }

    public ObjectProperty<Integer> refreshCycleProperty() {
        if (refreshCycle == null) {
            refreshCycle = new SimpleObjectProperty<>(_refreshCycle);
        }
        return refreshCycle;
    }

    private ObjectProperty<Integer> cycleTime;
    private Integer _cycleTime;

    @Basic(optional = false)
    @Column(name = "cycle_time", nullable = false)
    public Integer getCycleTime() {
        if (cycleTime == null) {
            return _cycleTime;
        } else {
            return cycleTime.get();
        }
    }

    public void setCycleTime(Integer cycleTime) {
        if (this.cycleTime == null) {
            this._cycleTime = cycleTime;
        } else {
            this.cycleTime.set(cycleTime);
        }
    }

    public ObjectProperty<Integer> cycleTimeProperty() {
        if (cycleTime == null) {
            cycleTime = new SimpleObjectProperty<>(_cycleTime);
        }
        return cycleTime;
    }

    private ObjectProperty<Date> createTime;
    private Date _createTime;

    @Basic(optional = false)
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        if (createTime == null) {
            return _createTime;
        } else {
            return createTime.get();
        }
    }

    public void setCreateTime(Date createTime) {
        if (this.createTime == null) {
            this._createTime = createTime;
        } else {
            this.createTime.set(createTime);
        }
    }

    public ObjectProperty<Date> createTimeProperty() {
        if (createTime == null) {
            createTime = new SimpleObjectProperty<>(this, "createTime", _createTime);
        }
        return createTime;
    }

    private ObjectProperty<Date> updateTime;
    private Date _updateTime;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        if (updateTime == null) {
            return _updateTime;
        } else {
            return updateTime.get();
        }
    }

    public void setUpdateTime(Date updateTime) {
        if (this.updateTime == null) {
            this._updateTime = updateTime;
        } else {
            this.updateTime.set(updateTime);
        }
    }

    public ObjectProperty<Date> updateTimeProperty() {
        if (updateTime == null) {
            updateTime = new SimpleObjectProperty<>(this, "updateTime", _updateTime);
        }
        return updateTime;
    }

    private ObjectProperty<Device> device;
    private Device _device;

    @JoinColumn(name = "device_id", referencedColumnName = "device_id", nullable = false)
    @OneToOne(optional = false)
    public Device getDevice() {
        if (device == null) {
            return _device;
        } else {
            return device.get();
        }
    }

    public void setDevice(Device device) {
        if (this.device == null) {
            this._device = device;
        } else {
            this.device.set(device);
        }
    }

    public ObjectProperty<Device> deviceProperty() {
        if (device == null) {
            device = new SimpleObjectProperty<>(this, "device", _device);
        }
        return device;
    }

    private ObjectProperty<User> user;
    private User _user;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    @NotFound(action = NotFoundAction.IGNORE)
    public User getUser() {
        if (user == null) {
            return _user;
        } else {
            return user.get();
        }
    }

    public void setUser(User user) {
        if (this.user == null) {
            this._user = user;
        } else {
            this.user.set(user);
        }
    }

    public ObjectProperty<User> userProperty() {
        if (user == null) {
            user = new SimpleObjectProperty<>(this, "user", _user);
        }
        return user;
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
        return "ofm.model.entities.Parameter[ parameterId=" + parameterId + " - device=" + device.toString() + " ]";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getParameterId());
        out.writeObject(getMeasuringRangeOfTest());
        out.writeObject(getTestPulseWidth());
        out.writeObject(getMeasuringTime());
        out.writeObject(getTestWaveLength());
        out.writeObject(getMeasureMode());
        out.writeObject(getRefractiveIndex());
        out.writeObject(getNonReflactionThreshold());
        out.writeObject(getEndThreshold());
        out.writeObject(getReflectionThreshold());
        out.writeObject(getOptimizeMode());
        out.writeObject(getEnabledRefresh());
        out.writeObject(getRefreshCycle());
        out.writeObject(getCycleTime());
        out.writeObject(getCreateTime());
        out.writeObject(getUpdateTime());
        out.writeObject(getDevice());
        out.writeObject(getUser());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setParameterId((Integer) in.readObject());
        setMeasuringRangeOfTest((Integer) in.readObject());
        setTestPulseWidth((Integer) in.readObject());
        setMeasuringTime((Integer) in.readObject());
        setTestWaveLength((Integer) in.readObject());
        setMeasureMode((Integer) in.readObject());
        setRefractiveIndex((Float) in.readObject());
        setNonReflactionThreshold((Float) in.readObject());
        setEndThreshold((Float) in.readObject());
        setReflectionThreshold((Float) in.readObject());
        setOptimizeMode((Integer) in.readObject());
        setEnabledRefresh((Integer) in.readObject());
        setRefreshCycle((Integer) in.readObject());
        setCycleTime((Integer) in.readObject());
        setCreateTime((Date) in.readObject());
        setUpdateTime((Date) in.readObject());
        setDevice((Device) in.readObject());
        setUser((User) in.readObject());
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
        // setID(data.getID());
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
        setUser(data.getUser());
        setDevice(data.getDevice());
        setCreateTime(data.getCreateTime());
        setCycleTime(data.getCycleTime());
    }

}
