/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_2dz_task1;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author gtoma
 */
public class DurationMapper extends Mapper<LongWritable, Text, Text, Durations>{

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
       
        DEBSRecordParser parser = new DEBSRecordParser();
        
        if(key.get() > 0){
            try {
                parser.parse(value.toString());
                double dur = parser.getDuration();
                Durations duration = new Durations(dur, dur, dur);
                context.write(new Text(parser.getMedallion()), duration);
            } catch (Exception ex) {
                Logger.getLogger(DurationMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    
}
