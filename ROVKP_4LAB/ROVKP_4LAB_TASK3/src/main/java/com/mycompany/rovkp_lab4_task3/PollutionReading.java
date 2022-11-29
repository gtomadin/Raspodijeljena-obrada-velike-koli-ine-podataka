/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab4_task3;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gtoma
 */
public class PollutionReading implements Comparable<PollutionReading>{
    private int ozone;
    private int particullate_matter;
    private int carbon_monoxide;
    private int sulfure_dioxide;
    private int nitrogen_dioxide;
    private double longitude;
    private double latitude;
    private Date timestamp;

    public PollutionReading(String line) {
        
        
            String[] chunks = line.split(",");
            
            this.ozone = Integer.parseInt(chunks[0]);
            this.particullate_matter = Integer.parseInt(chunks[1]);
            this.carbon_monoxide = Integer.parseInt(chunks[2]);
            this.sulfure_dioxide = Integer.parseInt(chunks[3]);
            this.nitrogen_dioxide = Integer.parseInt(chunks[4]);
            
            this.longitude = Double.parseDouble(chunks[5]);
            this.latitude = Double.parseDouble(chunks[6]);
            
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        try {    
            this.timestamp  = df.parse(chunks[7]);
        } catch (ParseException ex) {
            Logger.getLogger(PollutionReading.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static boolean canParse(String line){
        
        try{
            PollutionReading pr = new PollutionReading(line);
        } catch(Exception e){
            return false;
        }
        return true;
    }
    
    public int getOzone() {
        return ozone;
    }

    public void setOzone(int ozone) {
        this.ozone = ozone;
    }

    public int getParticullate_matter() {
        return particullate_matter;
    }

    public void setParticullate_matter(int particullate_matter) {
        this.particullate_matter = particullate_matter;
    }

    public int getCarbon_monoxide() {
        return carbon_monoxide;
    }

    public void setCarbon_monoxide(int carbon_monoxide) {
        this.carbon_monoxide = carbon_monoxide;
    }

    public int getSulfure_dioxide() {
        return sulfure_dioxide;
    }

    public void setSulfure_dioxide(int sulfure_dioxide) {
        this.sulfure_dioxide = sulfure_dioxide;
    }

    public int getNitrogen_dioxide() {
        return nitrogen_dioxide;
    }

    public void setNitrogen_dioxide(int nitrogen_dioxide) {
        this.nitrogen_dioxide = nitrogen_dioxide;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(PollutionReading o) {
        return timestamp.compareTo(o.timestamp);
    }

    @Override
    public String toString() {
        
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        
        return  ozone + "," + particullate_matter + "," + carbon_monoxide + "," + sulfure_dioxide
                + "," + nitrogen_dioxide + "," + longitude + "," + latitude + "," + df.format(timestamp);
    }
    
    
    
}
