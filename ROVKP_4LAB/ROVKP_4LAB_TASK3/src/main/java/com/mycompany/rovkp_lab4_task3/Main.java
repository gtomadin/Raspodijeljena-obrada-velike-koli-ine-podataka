/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab4_task3;

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
        
        JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(3));
        
        JavaDStream<String> lines = jsc.socketTextStream("localhost", 10002);
        
        JavaPairDStream<Location, Integer> MinOzone = lines.filter(line -> PollutionReading.canParse(line))
                                            .map(line -> new PollutionReading(line))
                                            .mapToPair(pr -> new Tuple2<>(new Location(pr.getLongitude(), pr.getLatitude()), pr.getOzone()))
                                            .reduceByKeyAndWindow((v1, v2) -> Math.min(v1, v2), Durations.seconds(45), Durations.seconds(15));
        
        
        MinOzone.dstream().saveAsTextFiles("outputs/output", "txt");
        
        jsc.start();
        
        jsc.awaitTermination();
        
        jsc.close();
        
    }
}
