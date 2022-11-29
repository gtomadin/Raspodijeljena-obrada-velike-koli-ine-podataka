/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_4dz_task2;

import java.io.Serializable;

/**
 *
 * @author gtoma
 */
public class USBabyNameRecord implements Serializable{
    
    private long id;
    private String name;
    private int year;
    private String gender;
    private String state;
    private long count;

   
    
    public USBabyNameRecord(String line){
       
        String[] chucks = line.split(",");

        this.id = Long.parseLong(chucks[0]);
        this.name = chucks[1];
        this.year = Integer.parseInt(chucks[2]);
        this.gender = chucks[3]; 
        this.state = chucks[4];
        this.count = Long.parseLong(chucks[5]);
       
    }
    
    public static boolean canParse(String line){
        try{
           USBabyNameRecord names = new USBabyNameRecord(line);
        } catch(Exception e){
            return false;
        }
        return true;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + year + "," + gender + "," + state + "," + count;
    }
    
    
    
}
