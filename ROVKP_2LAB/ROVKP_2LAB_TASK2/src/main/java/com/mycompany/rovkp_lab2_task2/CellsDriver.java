/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab2_task2;

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
public class CellsDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
       System.out.println("main");
        
        if(args.length != 3){
            System.err.println("Length: " + args.length);
            System.out.println("A0: " + args[1]);
            System.out.println("A1: " + args[2]);
            System.err.println("Usage: TotalDistance <input path> <output path>");
            System.exit(1);
        }
        
        
        
        Configuration conf = new Configuration();
        
        Job cellsJob = Job.getInstance(conf);
        cellsJob.setJarByClass(CellsDriver.class);
        cellsJob.setJobName("Cells");
        
        
        //cellsJob.setNumReduceTasks(numReduceTasks);
         
        FileInputFormat.addInputPath(cellsJob, new Path(args[1]));
        FileOutputFormat.setOutputPath(cellsJob, new Path(args[2]));
        
        cellsJob.setMapperClass(CellsMapper.class);
        cellsJob.setPartitionerClass(CellsPartitioner.class);
        cellsJob.setReducerClass(CellsReducer.class);
        
        cellsJob.setOutputKeyClass(IntWritable.class);
        cellsJob.setOutputValueClass(Text.class);
        
        System.exit(cellsJob.waitForCompletion(true) ? 0 : 1);
    }
}
