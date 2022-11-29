/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_4dz_task2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

/**
 *
 * @author gtoma
 */
public class Main{
    public static void main(String[] args) throws IOException {
       
        SparkConf conf = new SparkConf().setAppName("USBabyNameRecord");
        
        try{
            conf.get("spark.master");
        } catch(NoSuchElementException ex){
            conf.setMaster("local");
        }
        
        JavaSparkContext sc = new JavaSparkContext(conf);
        
        JavaRDD<String> lines = sc.textFile("StateNames.csv");
        
        JavaRDD<USBabyNameRecord> babyNames = lines.filter(line -> USBabyNameRecord.canParse(line))
                                                    .map(line -> new USBabyNameRecord(line)).cache();
        
       
        //System.out.println(babyNames.collect().get(0));//forEach(System.out::println);
        
        Writer writer = new BufferedWriter(new FileWriter("result.txt"));
        
        writer.write("Novo\n");
        
        //1. Task
        
        String mostUnpopularMName = babyNames.filter(babyName -> babyName.getGender().equals("M"))
                                             .cache()
                                             .mapToPair(babyName -> new Tuple2<>(babyName.getName(), babyName.getCount()))
                                             .reduceByKey((v1, v2) -> v1 + v2)
                                             .mapToPair(Tuple2::swap)
                                             .sortByKey()
                                             .first()._2;
        
             
        //System.out.println("Most Unpopular Male Name: " + mostUnpopularMName);
        
        writer.append("1. \n");
        writer.append("Most Unpopular Male Name: " + mostUnpopularMName + "\n");
        writer.append("\n");
        
        //2. Task
        
        List<String> mostPopularFName = babyNames.filter(babyName -> babyName.getGender().equals("F"))
                                             .cache()
                                             .mapToPair(babyName -> new Tuple2<>(babyName.getName(), babyName.getCount()))
                                             .reduceByKey((v1, v2) -> v1 + v2)
                                             .mapToPair(Tuple2::swap)
                                             .sortByKey(false)
                                             .map(tuple -> tuple._2)
                                             .take(10);
        
        writer.append("2. \n");
        writer.append("Most Popular Female Names: \n");
        
        mostPopularFName.stream().forEach(sr -> {
                try {
                    writer.append(sr.toString() + "\n");
                    
                } catch (IOException ex) {
                }
        });
        
        writer.append("\n");
        
        //3. Task
        
        String stateWithMostBirthIn1948 = babyNames.filter(babyName -> babyName.getYear() == 1948)
                                                    .cache()
                                                    .mapToPair(babyName -> new Tuple2<>(babyName.getState(), babyName.getCount()))
                                                    .reduceByKey((v1, v2) -> v1 + v2)
                                                    .mapToPair(Tuple2::swap)
                                                    .sortByKey(false)
                                                    .first()
                                                    ._2;
        
        //System.out.println("State With Most Birth In 1948: " + stateWithMostBirthIn1948);
        
        writer.append("3. \n"); 
        writer.append("State With Most Birth In 1948: " + stateWithMostBirthIn1948 + "\n");
        writer.append("\n");
        
        //4. Task
        
        List<Tuple2<Integer, Long>> maleBirthsPerYear = babyNames.filter(babyName -> babyName.getGender().equals("M"))
                                                        .cache()
                                                        .mapToPair(babyName -> new Tuple2<>(babyName.getYear(), babyName.getCount()))
                                                        .reduceByKey((v1, v2) -> v1 + v2)
                                                        .sortByKey()
                                                        .collect();
        
        writer.append("4. \n");
        writer.append("Male Births Per Year: \n");
        
        maleBirthsPerYear.forEach(sr -> {
                try {
                    writer.append(sr._2 + "\n");
                    
                } catch (IOException ex) {
                }
        });
       
        writer.append("\n");
        
        //5. Task
        
        List<Tuple2<Integer, Long>> LucyPerYear = babyNames.filter(babyName -> babyName.getName().equals("Lucy"))
                                                            .cache()
                                                            .mapToPair(babyName -> new Tuple2<>(babyName.getYear(), babyName.getCount()))
                                                            .reduceByKey((v1, v2) -> v1 + v2)
                                                            .sortByKey()
                                                            .collect();
        
        List<Tuple2<Integer, Long>> femalePerYear = babyNames.filter(babyName -> babyName.getGender().equals("F"))
                                                                .cache()
                                                                .mapToPair(babyName -> new Tuple2<>(babyName.getYear(), babyName.getCount()))
                                                                .reduceByKey((v1, v2) -> v1 + v2)
                                                                .sortByKey()
                                                                .collect();
        
        List<Tuple2<Integer, Double>> LucyAveragePerYear = new ArrayList<>();
        
        for(Tuple2<Integer, Long> tople1: LucyPerYear){
            for(Tuple2<Integer, Long> tople2: femalePerYear){
               
                if(tople1._1.equals(tople2._1)){
                
                    double value1 = tople1._2;
                    double value2 = tople2._2;
                    
                    double value = value1 / value2;
                    
                    Tuple2<Integer, Double> t = new Tuple2<>(tople1._1, value);
                    LucyAveragePerYear.add(t);
                }
            }
        }
        
        writer.append("5. \n");
        writer.append("Lucy Average Per Year: \n");
        
        LucyAveragePerYear.forEach(sr -> {
                try {
                    writer.append(sr._2.floatValue()*1000 + "\n");
                    
                } catch (IOException ex) {
                }
        });
        
        writer.append("\n");
        
        //6. Task
        
        Long countAllBirths = babyNames.map(babyName -> babyName.getCount())
                                        .reduce((v1, v2) -> v1+v2);
                
        //System.out.println("Sva rodena djeca: " + CountAllBirths);
        
        writer.append("6. \n"); 
        writer.append("Count All Births: " + countAllBirths + "\n");
        writer.append("\n");
        
        //7. Task
        
        Long differentName = babyNames.map(babyName -> babyName.getName())
                                      .distinct()
                                      .count();
        
        //System.out.println("Numeber of diferent names : " + diferentName);
        
        writer.append("7. \n"); 
        writer.append("Numeber of diferent names: " + differentName + "\n");
        writer.append("\n");
        
        writer.close();
    }
}
