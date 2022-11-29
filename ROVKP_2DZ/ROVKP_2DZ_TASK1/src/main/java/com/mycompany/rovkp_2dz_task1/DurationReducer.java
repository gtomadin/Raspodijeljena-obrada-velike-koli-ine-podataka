/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_2dz_task1;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author gtoma
 */
public class DurationReducer extends Reducer<Text, Durations, Text, Durations>{

    @Override
    protected void reduce(Text key, Iterable<Durations> values, Context context) throws IOException, InterruptedException {
        Durations duration = new Durations(0, Double.MAX_VALUE, 0);
        
        for(Durations value : values){
            duration.setTotalDuration(value.getTotalDuration().get() + duration.getTotalDuration().get());
            duration.setMaxDuration(Math.max(duration.getMaxDuration().get(), value.getMaxDuration().get()));
            duration.setMinDuration(Math.min(duration.getMinDuration().get(), value.getMinDuration().get()));
        }
        
        context.write(key, duration);
    }
}
