/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab1_task3;

import com.google.common.primitives.Ints;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;


/**
 *
 * @author gtoma
 */
public class task3 {
    public static void main(String[] args) {
      Configuration conf = new Configuration();
        
        Path path = new Path("/user/rovkp/gtomadin2/ocitanja.bin");
        
        try {
            createBin(conf, path);
            calcAverage(conf, path);
        } catch (IOException ex) {
            Logger.getLogger(task3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    private static void createBin(Configuration conf, Path path) throws IOException {
        
        Random rand = new  Random();
         
        SequenceFile.Writer writer = SequenceFile.createWriter(conf, SequenceFile.Writer.file(path),
                SequenceFile.Writer.keyClass(IntWritable.class), SequenceFile.Writer.valueClass(DoubleWritable.class));
        
        for(int i = 0; i < 100000; ++i){
            IntWritable key = new IntWritable(rand.nextInt(100) +1);
            DoubleWritable value = new DoubleWritable(rand.nextDouble() * 100);
            
            writer.append(key, value);
           
        }
        
        writer.close();
    }

    private static void calcAverage(Configuration conf, Path path) throws IOException {
       
          SequenceFile.Reader reader = new SequenceFile.Reader(conf, SequenceFile.Reader.file(path));
           
          IntWritable key = new IntWritable();
          DoubleWritable value = new DoubleWritable();
          
          int[] counters = new int[100];
          double[] sumValue = new double[100];
          
          while(reader.next(key, value)){
              int i = key.get() -1 ;
              counters[i]++;
              sumValue[i] += value.get();
          }
          
          reader.close();
          
          for(int i=0; i<100; ++i){
             double rez = sumValue[i]/counters[i]; 
              System.out.println("Senzor: " + (i+1) + " = " + rez );
          }
          
    }   
        
}
