/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_2dz_task1;

/**
 *
 * @author gtoma
 */
public class DEBSRecordParser  {
    private String medallion;
    private double duration;

    public void parse(String record) throws Exception {
        String[] splitted = record.split(",");
        medallion = splitted[0];
        duration = Double.parseDouble(splitted[8]);
    }

    public String getMedallion() {
        return medallion;
    }

    public double getDuration() {
        return duration;
    }
}
