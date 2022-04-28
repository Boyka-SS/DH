package com.jinke.driverhealth.data.network.tudingyun.beans;

/**
 * @author: fanlihao
 * @date: 2022/1/20
 */
public class HealthData {
    private BloodPressure mBloodPressure;

    private HeartRate mHeartRate;
    private Temperature mTemperature;

    public HealthData(BloodPressure bloodPressure, HeartRate heartRate, Temperature temperature) {
        mBloodPressure = bloodPressure;
        mHeartRate = heartRate;
        mTemperature = temperature;
    }

    public BloodPressure getBloodPressure() {
        return mBloodPressure;
    }

    public void setBloodPressure(BloodPressure bloodPressure) {
        mBloodPressure = bloodPressure;
    }

    public HeartRate getHeartRate() {
        return mHeartRate;
    }

    public void setHeartRate(HeartRate heartRate) {
        mHeartRate = heartRate;
    }

    public Temperature getTemperature() {
        return mTemperature;
    }

    public void setTemperature(Temperature temperature) {
        mTemperature = temperature;
    }
}
