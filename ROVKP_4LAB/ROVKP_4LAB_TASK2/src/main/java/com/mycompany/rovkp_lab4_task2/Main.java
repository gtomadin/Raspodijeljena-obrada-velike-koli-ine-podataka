/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab4_task2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
public class Main {
    public static void main(String[] args) throws IOException {
        
        SparkConf conf = new SparkConf().setAppName("USDeathRecord");
        
        try{
            conf.get("spark.master");
        } catch(NoSuchElementException ex){
            conf.setMaster("local");
        }
        
        JavaSparkContext sc = new JavaSparkContext(conf);
        
        JavaRDD<String> lines = sc.textFile("/user/rovkp/gtomadin/DeathRecords.csv");
        
        JavaRDD<USDeathRecord> deaths = lines.filter(line -> USDeathRecord.isParsable(line))
                                             .map(line -> new USDeathRecord(line)).cache();
        
        
        Writer writer = new BufferedWriter(new FileWriter("/user/rovkp/gtomadin/result.txt"));
        
        writer.write("Novo\n");
        
        //1. Task
        
        long FemaleOlderThen40 = deaths.filter(death -> death.getGender().equals("F"))
                                      .filter(death -> death.getAge() > 40)
                                      .cache()
                                      .count();
        
        writer.append("1. \n");
        writer.append("Female older then 40 : " + FemaleOlderThen40 + "\n");
        writer.append("\n");
        
        //2. Task
        
        Integer MonthMostMaleUnder50 =  deaths.filter( death -> death.getGender().equals("M"))
                                            .filter(death -> death.getAge() < 50)
                                            .mapToPair(death -> new Tuple2<>(death.getMonthOfDeath(), 1))
                                            .reduceByKey((v1, v2) -> v1 + v2)
                                            .mapToPair(Tuple2::swap)
                                            .sortByKey(false)
                                            .first()
                                            ._2;
                                            
                                            
                                                                           
        writer.append("2. \n");
        writer.append("Month with most male under 50 : " + MonthMostMaleUnder50 + "\n");
        writer.append("\n");
        
        
        //3. Task
        
        long FemaleAutopsy =  deaths.filter(death -> death.getGender().equals("F"))
                                       .filter(death -> death.getAutopsy().equals("Y"))
                                       .cache()
                                       .count();
                                            
                                            
                                                                           
        writer.append("3. \n");
        writer.append("Female with autopsy : " + FemaleAutopsy + "\n");
        writer.append("\n");
        
        //4. Task
        
        List<String> FemaleBetween5065ByWeekDay =   deaths.filter(death -> death.getGender().equals("F"))
                                       .filter(death -> death.getAge() > 50 && death.getAge() < 65)
                                       .cache()
                                       .mapToPair(death -> new Tuple2<>(death.getDayOfWeekOfDeath(), 1))
                                       .reduceByKey((v1, v2) -> v1 + v2)
                                       .sortByKey()
                                       .map(tuple -> tuple._1 + ","  + tuple._2 )
                                       .take(7);
                                            
                                                                           
        writer.append("4. \n");
        writer.append("Female between 50 and 65 by week day : \n");
        
        FemaleBetween5065ByWeekDay.stream().forEach(sr ->{
                try {
                    writer.append(sr.toString() + "\n");
                    
                } catch (IOException ex) {
                }
        });
        
        writer.append("\n");
        
        //5. Task
        
        List<Tuple2<Integer, Integer>> FemaleBetween5065MarriedByWeekDay =   deaths.filter(death -> death.getGender().equals("F"))
                                       .filter(death -> death.getAge() > 50 && death.getAge() < 65)
                                       .filter(death -> death.getMaritialStatus().equals("M"))
                                       .cache()
                                       .mapToPair(death -> new Tuple2<>(death.getDayOfWeekOfDeath(), 1))
                                       .reduceByKey((v1, v2) -> v1 + v2)
                                       .sortByKey()
                                       .take(7);
        
        
        
        long helpsum = FemaleBetween5065MarriedByWeekDay.stream().mapToLong(death -> death._2).sum();
        
        
                                                                           
        writer.append("5. \n");
        writer.append("Percentage of female between 50 and 65 by week day : \n");
        
        FemaleBetween5065MarriedByWeekDay.stream().forEach(sr ->{
                try {
                    double value1 = sr._2;
                    double value2 = helpsum;
                    double value = value1 / value2;
                    writer.append(sr._1 + ", " + value + "\n");
                    
                } catch (IOException ex) {
                }
        });
        
        writer.append("\n");
        
        //6. Task
        
        long MaleAccident =  deaths.filter(death -> death.getGender().equals("M"))
                                       .filter(death -> death.getMannerOfDeath() == 1)
                                       .cache()
                                       .count();
                                            
                                            
                                                                           
        writer.append("6. \n");
        writer.append("Male dead in accident : " + MaleAccident + "\n");
        writer.append("\n");
        
        //7. Task
        
        long DifferentAges =  deaths.map(death -> death.getAge())
                                    .distinct()
                                    .count();
                                            
                                            
                                                                           
        writer.append("7. \n");
        writer.append("different ages : " + DifferentAges + "\n");
        writer.append("\n");
        
        
        writer.close();
        
        
        
    }
}
