/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_2dz_task2;

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
public class NumberPassengersAndLoncation {
    public static void main(String[] args) throws Exception {
        System.out.println("main");
       
        
        if(args.length != 3){
            System.err.println("Length: " + args.length);
            System.out.println("A0: " + args[1]);
            System.out.println("A1: " + args[2]);
            System.err.println("Usage: TotalDistance <input path> <output path>");
            System.exit(1);
        }
        int numReduceTasks = 7; // u 7 skupinu idu sve voznje sa 0 putnika ili sa manje od 14 polja u zapisu
        
        Configuration conf = new Configuration();
        
        Job job = Job.getInstance(conf);
        job.setJarByClass(NumberPassengersAndLoncation.class);
        job.setJobName("Number Passengers And Loncation");
        
        job.setNumReduceTasks(numReduceTasks);
        
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        
        job.setMapperClass(NumberPassengersAndLoncationMapper.class);
        job.setPartitionerClass(NumberPassengersAndLoncationPartitioner.class);
        job.setReducerClass(NumberPassengersAndLoncationReducer.class);
        
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        
        
         if(job.waitForCompletion(true)){
            System.exit(1);
        }
    }
}
