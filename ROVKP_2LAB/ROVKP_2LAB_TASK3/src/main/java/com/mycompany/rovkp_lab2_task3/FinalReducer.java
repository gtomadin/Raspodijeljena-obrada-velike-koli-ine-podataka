/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab2_task3;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author gtoma
 */
class FinalReducer extends Reducer<IntWritable, Text, IntWritable, Text> {

    @Override
    protected void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        
        String cellMaxRides = "";
        double cellMaxRidesAmount = Double.MAX_VALUE;
        String cellMinRides = "";
        double cellMinRidesAmount = 0;
        
        int maxRides = 0;
        int minRides = Integer.MAX_VALUE;
        
        for(Text value : values){
            
            String s = value.toString();
            String[] splitted = s.split(",");
            String cell = splitted[1];
            int numRides = Integer.parseInt(splitted[2]);
            double amount = Double.parseDouble(splitted[3]);
            
            if(numRides > maxRides){
                maxRides = numRides;
                cellMaxRidesAmount = amount;
                cellMaxRides = cell;
            }
            
            if(numRides < minRides){
                minRides = numRides;
                cellMinRidesAmount = amount;
                cellMinRides = cell;
                
            }
                 
        }
        
        Text result = new Text(cellMaxRides + "," + maxRides +"," + cellMaxRidesAmount + " , " + cellMinRides + "," + maxRides + "," + cellMinRidesAmount);
        
        context.write(key, result);
        
        
        
        
    }
    
}
