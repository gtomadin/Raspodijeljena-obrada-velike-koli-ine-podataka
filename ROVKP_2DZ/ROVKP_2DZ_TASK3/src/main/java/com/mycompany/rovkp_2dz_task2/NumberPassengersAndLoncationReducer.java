/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_2dz_task2;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author gtoma
 */
public class NumberPassengersAndLoncationReducer extends Reducer<IntWritable, Text, NullWritable, Text>{

    @Override
    protected void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        
        for(Text value : values){
            context.write(NullWritable.get(), value);
        }
    
    }
    
    
}
