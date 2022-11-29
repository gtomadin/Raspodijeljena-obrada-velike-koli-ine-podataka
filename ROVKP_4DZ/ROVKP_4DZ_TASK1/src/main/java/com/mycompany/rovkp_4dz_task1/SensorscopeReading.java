/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_4dz_task1;

/**
 *
 * @author gtoma
 */
public class SensorscopeReading implements Comparable<SensorscopeReading>{
    
    private int ID;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    
    private long timeSinceTheEpoch;
    private int SequenceNumber;
    
    private double configSamplingTime;
    private double dataSamplingTime;
    private double radioDutyCycle;
    private double radioTransmissionPower;
    private double radioTransmissionFrequency;
    private double primaryBufferVoltage;
    private double secondaryBufferVoltage;
    private double solarPanelCurrent;
    private double globalCurrent;
    private double energySource;

    public SensorscopeReading(String line) {
        String[] chucks = line.split(" ");
        
            this.ID = Integer.parseInt(chucks[0]);
            this.year = Integer.parseInt(chucks[1]);
            this.month = Integer.parseInt(chucks[2]);
            this.day = Integer.parseInt(chucks[3]);
            this.hour = Integer.parseInt(chucks[4]);
            this.minute = Integer.parseInt(chucks[5]);
            this.second = Integer.parseInt(chucks[6]);

            this.timeSinceTheEpoch = Long.parseLong(chucks[7]);
            this.SequenceNumber = Integer.parseInt(chucks[8]);

            this.configSamplingTime = Double.parseDouble(chucks[9]);
            this.dataSamplingTime = Double.parseDouble(chucks[10]);
            this.radioDutyCycle = Double.parseDouble(chucks[11]);
            this.radioTransmissionPower = Double.parseDouble(chucks[12]);
            this.radioTransmissionFrequency = Double.parseDouble(chucks[13]);
            this.primaryBufferVoltage = Double.parseDouble(chucks[14]);
            this.secondaryBufferVoltage = Double.parseDouble(chucks[15]);
            this.solarPanelCurrent = Double.parseDouble(chucks[16]);
            this.globalCurrent = Double.parseDouble(chucks[17]);
            this.energySource = Double.parseDouble(chucks[18]);
        
        
    }
    
    public static boolean canParse(String line){
        try{
           SensorscopeReading names = new SensorscopeReading(line);
        } catch(Exception e){
            return false;
        }
        return true;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public long getTimeSinceTheEpoch() {
        return timeSinceTheEpoch;
    }

    public void setTimeSinceTheEpoch(long timeSinceTheEpoch) {
        this.timeSinceTheEpoch = timeSinceTheEpoch;
    }

    public int getSequenceNumber() {
        return SequenceNumber;
    }

    public void setSequenceNumber(int SequenceNumber) {
        this.SequenceNumber = SequenceNumber;
    }

    public double getConfigSamplingTime() {
        return configSamplingTime;
    }

    public void setConfigSamplingTime(double configSamplingTime) {
        this.configSamplingTime = configSamplingTime;
    }

    public double getDataSamplingTime() {
        return dataSamplingTime;
    }

    public void setDataSamplingTime(double dataSamplingTime) {
        this.dataSamplingTime = dataSamplingTime;
    }

    public double getRadioDutyCycle() {
        return radioDutyCycle;
    }

    public void setRadioDutyCycle(double radioDutyCycle) {
        this.radioDutyCycle = radioDutyCycle;
    }

    public double getRadioTransmissionPower() {
        return radioTransmissionPower;
    }

    public void setRadioTransmissionPower(double radioTransmissionPower) {
        this.radioTransmissionPower = radioTransmissionPower;
    }

    public double getRadioTransmissionFrequency() {
        return radioTransmissionFrequency;
    }

    public void setRadioTransmissionFrequency(double radioTransmissionFrequency) {
        this.radioTransmissionFrequency = radioTransmissionFrequency;
    }

    public double getPrimaryBufferVoltage() {
        return primaryBufferVoltage;
    }

    public void setPrimaryBufferVoltage(double primaryBufferVoltage) {
        this.primaryBufferVoltage = primaryBufferVoltage;
    }

    public double getSecondaryBufferVoltage() {
        return secondaryBufferVoltage;
    }

    public void setSecondaryBufferVoltage(double secondaryBufferVoltage) {
        this.secondaryBufferVoltage = secondaryBufferVoltage;
    }

    public double getSolarPanelCurrent() {
        return solarPanelCurrent;
    }

    public void setSolarPanelCurrent(double solarPanelCurrent) {
        this.solarPanelCurrent = solarPanelCurrent;
    }

    public double getGlobalCurrent() {
        return globalCurrent;
    }

    public void setGlobalCurrent(double globalCurrent) {
        this.globalCurrent = globalCurrent;
    }

    public double getEnergySource() {
        return energySource;
    }

    public void setEnergySource(double energySource) {
        this.energySource = energySource;
    }

    @Override
    public int compareTo(SensorscopeReading o) {
        return Double.compare(timeSinceTheEpoch, o.getTimeSinceTheEpoch());
    }

    @Override
    public String toString() {
        return ID + "," + year + "," + month + "," + day + "," + hour + "," + minute + "," + second + "," + timeSinceTheEpoch + 
                "," + SequenceNumber + "," + configSamplingTime + "," + dataSamplingTime + "," + radioDutyCycle + 
                "," + radioTransmissionPower + "," + radioTransmissionFrequency + "," + primaryBufferVoltage + 
                "," + secondaryBufferVoltage + "," + solarPanelCurrent + "," + globalCurrent + "," + energySource;
    }

    

    
    
    
    
    
    
    
}
