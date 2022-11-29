/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_2dz_task3;


import com.mycompany.rovkp_2dz_task1.Duration;
import com.mycompany.rovkp_2dz_task1.DurationMapper;
import com.mycompany.rovkp_2dz_task1.DurationReducer;
import com.mycompany.rovkp_2dz_task1.Durations;
import com.mycompany.rovkp_2dz_task2.NumberPassengersAndLoncation;
import com.mycompany.rovkp_2dz_task2.NumberPassengersAndLoncationMapper;
import com.mycompany.rovkp_2dz_task2.NumberPassengersAndLoncationPartitioner;
import com.mycompany.rovkp_2dz_task2.NumberPassengersAndLoncationReducer;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
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
public class MapReduceTask {
    private static final String INTERMEDIATE_PATH = "/user/gtoma/dz2/intermediate";
    
    public static void main(String[] args) throws Exception {
        System.out.println("main");
    
        
        if(args.length != 3){
            System.err.println("Length: " + args.length);
            System.err.println("Usage: TotalDistance <input path> <output path>");
            System.exit(1);
        }
        
        Configuration conf = new Configuration();
        
        
        FileSystem fileSystem = FileSystem.get(conf);
        
        Job NumberPassengersAndLoncationJob = Job.getInstance(conf);
        NumberPassengersAndLoncationJob.setJarByClass(NumberPassengersAndLoncation.class);
        NumberPassengersAndLoncationJob.setJobName("Number Passengers And Loncation");
        
        int numReduceTasks = 7;
        
        NumberPassengersAndLoncationJob.setNumReduceTasks(numReduceTasks);
        
        FileInputFormat.addInputPath(NumberPassengersAndLoncationJob, new Path(args[1]));
        FileOutputFormat.setOutputPath(NumberPassengersAndLoncationJob, new Path(INTERMEDIATE_PATH));
        
        NumberPassengersAndLoncationJob.setMapperClass(NumberPassengersAndLoncationMapper.class);
        NumberPassengersAndLoncationJob.setPartitionerClass(NumberPassengersAndLoncationPartitioner.class);
        NumberPassengersAndLoncationJob.setReducerClass(NumberPassengersAndLoncationReducer.class);
        
        NumberPassengersAndLoncationJob.setOutputKeyClass(IntWritable.class);
        NumberPassengersAndLoncationJob.setOutputValueClass(Text.class);
        
        int code1 = -1;
        
        while(true){
                        
            TimeUnit.SECONDS.sleep(10);
                        
            code1 = NumberPassengersAndLoncationJob.waitForCompletion(true) ? 0 : 1;
                        
            if(code1 == 0){
                break;
            }
        }
       
        
        int code2 = 0;
        
        if(code1 == 0){
            TimeUnit.SECONDS.sleep(10);
            FileStatus[] list = fileSystem.listStatus(new Path(INTERMEDIATE_PATH));
            
            for(FileStatus status : list){
                if(status.getPath().getName().startsWith("part")){
                    Job DurationJob = Job.getInstance();
            
                    DurationJob.setJarByClass(Duration.class);
                    DurationJob.setJobName("Trip Duration");
        
                    FileInputFormat.addInputPath(DurationJob, status.getPath());
                    FileOutputFormat.setOutputPath(DurationJob, new Path(args[2] + "/" + status.getPath().getName()));
        
                    DurationJob.setMapperClass(DurationMapper.class);
                    DurationJob.setCombinerClass(DurationReducer.class);
                    DurationJob.setReducerClass(DurationReducer.class);
        
                    DurationJob.setOutputKeyClass(Text.class);
                    DurationJob.setOutputValueClass(Durations.class);
            
                    
                    while(true){
                        
                        TimeUnit.SECONDS.sleep(10);
                        
                        code2 = DurationJob.waitForCompletion(true) ? 0 : 1;
                        
                        if(code2 == 0){
                            
                            break;
                        }
                    }
                    
                    
                }
            }
            
        }
        
        if(code2 == 0){
            //FileSystem.get(conf).delete(TEMP_RESULT, true);
            fileSystem.delete(new Path(INTERMEDIATE_PATH), true);

            System.exit(0);
        }
    }
}
