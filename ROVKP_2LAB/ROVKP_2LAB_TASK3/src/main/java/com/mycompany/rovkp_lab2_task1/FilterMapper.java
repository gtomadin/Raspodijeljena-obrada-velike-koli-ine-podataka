/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab2_task1;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author gtoma
 */
public class FilterMapper extends Mapper<LongWritable, Text, Text, NullWritable>{

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
         DEBSRecordParser parser = new DEBSRecordParser();
       
        if( key.get() > 0){
                        
            parser.parse(value.toString());
            
            
            
            
            if(parser.getTotalAmount() > 0){
                
                if(inRigthLocation(parser.getPickupLongitude(), parser.getPickupLatitude(), parser.getDropoffLongitude(), parser.getDropoffLatitude())){
                    
                    
                    context.write(value, NullWritable.get());
                    
                }
            }
    }
    
    
}

    private boolean inRigthLocation(double pickupLongitude, double pickupLatitude, double dropoffLongitude, double dropoffLatitude) {
        double MaxLat = 41.474937;
        double MaxLon = -73.117785;
        
        double MinLat = 40.1274702;
        double MinLon = -74.913585;
        
          
        return pickupLatitude >= MinLat && pickupLatitude <= MaxLat &&
                pickupLongitude >= MinLon && pickupLongitude <= MaxLon &&
                dropoffLatitude >= MinLat && dropoffLatitude <= MaxLat &&
                dropoffLongitude >= MinLon && dropoffLongitude <= MaxLon;
    }
}