/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_2dz_task2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 *
 * @author gtoma
 */
public class NumberPassengersAndLoncationPartitioner extends Partitioner<IntWritable, Text>{

    @Override
    public int getPartition(IntWritable key, Text value, int i) {
        
        DEBSRecordParser parser = new DEBSRecordParser();
        
        parser.parse(value.toString());
        
        int subGroup = 0;
        
        if(parser.getPassengerNumber() == 1){
            subGroup = 0;
        }
        
        if(parser.getPassengerNumber() == 2 || parser.getPassengerNumber() == 3){
            subGroup = 1;
        }
        
        if(parser.getPassengerNumber() >= 4){
            subGroup = 2;
        }
        
        subGroup += (key.get() * 3);
        
        if(parser.getPassengerNumber() == 0){
            subGroup = 6;
        }
        
        return (subGroup) % i; 
    }
    
    
}
