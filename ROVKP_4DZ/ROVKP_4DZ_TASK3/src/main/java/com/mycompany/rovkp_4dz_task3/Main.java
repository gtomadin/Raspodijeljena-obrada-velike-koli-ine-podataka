/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_4dz_task3;


import java.util.NoSuchElementException;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;


/**
 *
 * @author gtoma
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        
        SparkConf conf = new SparkConf().setAppName("SensorStream");
        
        try{
            conf.get("spark.master");
        } catch(NoSuchElementException ex){
            conf.setMaster("local");
        }
        
        JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(5));
        
        JavaDStream<String> lines = jsc.socketTextStream("localhost", 10002);
        
        JavaPairDStream<Integer, Double> MaxSolarPanelCurrent = lines.filter(line -> SensorscopeReading.canParse(line))
                                                                  .map(line -> new SensorscopeReading(line))
                                                                  .mapToPair(sr -> new Tuple2<Integer, Double>(sr.getID(), sr.getSolarPanelCurrent()))
                                                                  .reduceByKeyAndWindow((v1, v2) -> Math.max(v1, v2), Durations.seconds(60), Durations.seconds(10));
                                                                  
        MaxSolarPanelCurrent.dstream().saveAsTextFiles("outputs/output", "txt");
        
        
        jsc.start();
        
        jsc.awaitTermination();
        
        jsc.close();
    }
}
