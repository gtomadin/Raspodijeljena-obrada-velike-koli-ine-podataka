/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab2_task1;

/**
 *
 * @author gtoma
 */
public class DEBSRecordParser {
    private String record;
    
    private String medallion;
    
    private double pickupLongitude;
    private double pickupLatitude;
    
    private double dropoffLongitude;
    private double dropoffLatitude;
    
    private double totalAmount;

    public void parse(String record){
        this.record = record;
        
        String[] splitted = record.split(",");
        
        this.medallion = splitted[0];
        
        this.pickupLongitude = Double.parseDouble(splitted[6]);
        this.pickupLatitude = Double.parseDouble(splitted[7]);
        
        this.dropoffLongitude = Double.parseDouble(splitted[8]);
        this.dropoffLatitude = Double.parseDouble(splitted[9]);
        
        this.totalAmount = Double.parseDouble(splitted[16]);
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getMedallion() {
        return medallion;
    }

    public void setMedallion(String medallion) {
        this.medallion = medallion;
    }

    public double getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(double pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public double getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(double pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public double getDropoffLongitude() {
        return dropoffLongitude;
    }

    public void setDropoffLongitude(double dropoffLongitude) {
        this.dropoffLongitude = dropoffLongitude;
    }

    public double getDropoffLatitude() {
        return dropoffLatitude;
    }

    public void setDropoffLatitude(double dropoffLatitude) {
        this.dropoffLatitude = dropoffLatitude;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
