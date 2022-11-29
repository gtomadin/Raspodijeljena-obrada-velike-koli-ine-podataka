/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_2dz_task2;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author gtoma
 */
public class NumberPassengersAndLoncationMapper extends Mapper<LongWritable, Text, IntWritable, Text>{

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
        double minLongitute = -74;
        double maxLongitute = -73.95;
        double minLatitude = 40.75;
        double maxLatitude = 40.8;
        
        if( key.get() > 0){
            
            DEBSRecordParser parser = new DEBSRecordParser();
            
            parser.parse(value.toString());
            
            IntWritable location;
            
            if(parser.getPickupLongitude() >= minLongitute && parser.getPickupLongitude() <= maxLongitute){
                if(parser.getDropoffLongitude() >= minLongitute && parser.getDropoffLongitude() <= maxLongitute){
                    if(parser.getPickupLatitude() >= minLatitude && parser.getPickupLatitude() <= maxLatitude){
                        if(parser.getDropoffLatitude()>= minLatitude && parser.getDropoffLatitude()<= maxLatitude){
                            location = new IntWritable(1);
                        }else{
                            location = new IntWritable(0);
                        }
                       
                    }else{
                        location = new IntWritable(0);
                    }
                }else{
                    location = new IntWritable(0);
                }
            }else{
                location = new IntWritable(0);
            }
                
            context.write(location, new Text(parser.getRecord()));
        }
        
    }
    
    
    
    
}
