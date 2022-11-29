/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab2_task3;

import com.mycompany.rovkp_lab2_task1.FilterDriver;
import com.mycompany.rovkp_lab2_task1.FilterMapper;
import com.mycompany.rovkp_lab2_task2.CellsDriver;
import com.mycompany.rovkp_lab2_task2.CellsMapper;
import com.mycompany.rovkp_lab2_task2.CellsReducer;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author gtoma
 */
public class FilteredCells {
    
    private static final String INTERMEDIATE_PATH = "/user/rovkp/gtomadin/intermediate";
    private static final String INTERMEDIATE_PATH_2 = "/user/rovkp/gtomadin/intermediate2";
    
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        System.out.println("main");
        
        long start = System.currentTimeMillis();
        
        if(args.length != 3){
            System.err.println("Length: " + args.length);
            System.out.println("A0: " + args[1]);
            System.out.println("A1: " + args[2]);
            System.err.println("Usage: TotalDistance <input path> <output path>");
            System.exit(1);
        }
        
        Configuration conf = new Configuration();
        
        FileSystem fileSystem = FileSystem.get(conf);
        
        Job filterJob = Job.getInstance(conf);
        filterJob.setJarByClass(FilterDriver.class);
        filterJob.setJobName("Filtering job");
        
        FileInputFormat.addInputPath(filterJob, new Path(args[1]));
        FileOutputFormat.setOutputPath(filterJob, new Path(INTERMEDIATE_PATH));
        
        filterJob.setMapperClass(FilterMapper.class);
        
       
        filterJob.setOutputKeyClass(Text.class);
        filterJob.setOutputValueClass(NullWritable.class);
         
        
        
        
        TimeUnit.SECONDS.sleep(5);
         
        int code1 = filterJob.waitForCompletion(true) ? 0 : 1;
        int code2 = -1;
        if(code1 == 0){
            Job cellsJob = Job.getInstance(conf);
            cellsJob.setJarByClass(CellsDriver.class);
            cellsJob.setJobName("Cells");


            //cellsJob.setNumReduceTasks(numReduceTasks);

            FileInputFormat.addInputPath(cellsJob, new Path(INTERMEDIATE_PATH));
            FileOutputFormat.setOutputPath(cellsJob, new Path(INTERMEDIATE_PATH_2));

            cellsJob.setMapperClass(CellsMapper.class);
            //cellsJob.setPartitionerClass(CellsPartitioner.class);
            cellsJob.setReducerClass(CellsReducer.class);

            cellsJob.setOutputKeyClass(IntWritable.class);
            cellsJob.setOutputValueClass(Text.class);
            
            while(true){
                TimeUnit.SECONDS.sleep(5);
                code2 = cellsJob.waitForCompletion(true) ? 0 : 1;
                if(code2 == 0){
                    break;
                }
            }
        }
        
        TimeUnit.SECONDS.sleep(5);
         
        int code3 = -1;
        
        if(code2 == 0){
                

                Job FinalJob = Job.getInstance(conf);
                FinalJob.setJarByClass(FinalDriver.class);
                FinalJob.setJobName("Final");
                
                FinalJob.setNumReduceTasks(1);
                
                FileInputFormat.addInputPath(FinalJob, new Path(INTERMEDIATE_PATH_2));
                FileOutputFormat.setOutputPath(FinalJob, new Path(args[2]));
                
                FinalJob.setMapperClass(FinalMapper.class);
                FinalJob.setPartitionerClass(FinalPartitioner.class);
                FinalJob.setReducerClass(FinalReducer.class);
                
                FinalJob.setOutputKeyClass(IntWritable.class);
                FinalJob.setOutputValueClass(Text.class);
                
                while(true){
                    TimeUnit.SECONDS.sleep(5);
                    code3 = FinalJob.waitForCompletion(true) ? 0 : 1;
                    if(code3 == 0){
                        break;
                    }
                }
            }
        
        fileSystem.delete(new Path(INTERMEDIATE_PATH), true);
        fileSystem.delete(new Path(INTERMEDIATE_PATH_2), true);
        
        long end = System.currentTimeMillis();
        
        long time = end - start;
        System.out.println("time: " + time);
    }
}
