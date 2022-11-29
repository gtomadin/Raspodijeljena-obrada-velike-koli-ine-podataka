/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_2dz_task2;

/**
 *
 * @author gtoma
 */
public class DEBSRecordParser {
    private String record;
    
    private String medallion;
    
    private int passengerNumber;
    
    private double pickupLongitude;
    private double pickupLatitude;
    
    private double dropoffLongitude;
    private double dropoffLatitude;
    
    public void parse(String record){
        this.record = record;
        
        String[] splitted = record.split(",");
        
        if(splitted.length == 14){
            this.medallion = splitted[0];

            this.passengerNumber = Integer.parseInt(splitted[7]);

            this.pickupLongitude = Double.parseDouble(splitted[10]);
            this.pickupLatitude = Double.parseDouble(splitted[11]);

            this.dropoffLongitude = Double.parseDouble(splitted[12]);
            this.dropoffLatitude = Double.parseDouble(splitted[13]);
        }else{
            this.medallion = "0";

            this.passengerNumber = 0;

            this.pickupLongitude = 0;
            this.pickupLatitude = 0;

            this.dropoffLongitude = 0;
            this.dropoffLatitude = 0;
            
            
            
        }
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

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public void setPassengerNumber(int passengerNumber) {
        this.passengerNumber = passengerNumber;
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

    @Override
    public String toString() {
        return "medallion=" + medallion + ", passengerNumber=" + passengerNumber + ", pickupLongitude=" + pickupLongitude + ", pickupLatitude=" + pickupLatitude + ", dropoffLongitude=" + dropoffLongitude + ", dropoffLatitude=" + dropoffLatitude + '}';
    }
     
    
}
