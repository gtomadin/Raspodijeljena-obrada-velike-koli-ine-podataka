/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab2_task1;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author gtoma
 */
public class FilterDriver {
    public static void main(String[] args) throws Exception {
        System.out.println("main");
        
        if(args.length != 3){
            System.err.println("Length: " + args.length);
            System.out.println("A0: " + args[1]);
            System.out.println("A1: " + args[2]);
            System.err.println("Usage: TotalDistance <input path> <output path>");
            System.exit(1);
        }
        
        Configuration conf = new Configuration();
        
        Job filterJob = Job.getInstance(conf);
        filterJob.setJarByClass(FilterDriver.class);
        filterJob.setJobName("Filtering job");
        
        FileInputFormat.addInputPath(filterJob, new Path(args[1]));
        FileOutputFormat.setOutputPath(filterJob, new Path(args[2]));
        
        filterJob.setMapperClass(FilterMapper.class);
        
       
        filterJob.setOutputKeyClass(Text.class);
        filterJob.setOutputValueClass(NullWritable.class);
         
        System.exit(filterJob.waitForCompletion(true) ? 0 : 1);
    }
}
