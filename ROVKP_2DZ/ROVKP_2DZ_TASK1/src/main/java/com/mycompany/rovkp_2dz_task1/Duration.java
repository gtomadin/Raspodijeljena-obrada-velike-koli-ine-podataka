/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_2dz_task1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author gtoma
 */
public class Duration {
    public static void main(String[] args) throws Exception {
        System.out.println("main");
       
        long startTime = System.currentTimeMillis();
        
        if(args.length != 3){
            System.err.println("Length: " + args.length);
            System.out.println("A1: " + args[1]);
            System.out.println("A1: " + args[2]);
            System.err.println("Usage: TotalDistance <input path> <output path>");
            System.exit(1);
        }
        
        Configuration conf = new Configuration();
        
        Job job = Job.getInstance(conf);
        job.setJarByClass(Duration.class);
        job.setJobName("Trip Duration");
        
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        
        job.setMapperClass(DurationMapper.class);
        job.setCombinerClass(DurationReducer.class);
        job.setReducerClass(DurationReducer.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Durations.class);
        
       
        if(job.waitForCompletion(true)){
            long endtime = System.currentTimeMillis();
            System.out.println("Vrijeme trajanja: " + (endtime - startTime));
            System.exit(1);
        }
    }
}
