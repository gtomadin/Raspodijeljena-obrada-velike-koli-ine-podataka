/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab2_task3;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author gtoma
 */
class FinalDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
                
                Configuration conf = new Configuration();
    
                Job FinalJob = Job.getInstance(conf);
                FinalJob.setJarByClass(FinalDriver.class);
                FinalJob.setJobName("Final");
                
                FileInputFormat.addInputPath(FinalJob, new Path(args[1]));
                FileOutputFormat.setOutputPath(FinalJob, new Path(args[2]));
                
                FinalJob.setMapperClass(FinalMapper.class);
                FinalJob.setPartitionerClass(FinalPartitioner.class);
                FinalJob.setReducerClass(FinalReducer.class);
                
                FinalJob.setOutputKeyClass(IntWritable.class);
                FinalJob.setOutputValueClass(Text.class);
                
                System.exit(FinalJob.waitForCompletion(true) ? 0 : 1);
    }
}
