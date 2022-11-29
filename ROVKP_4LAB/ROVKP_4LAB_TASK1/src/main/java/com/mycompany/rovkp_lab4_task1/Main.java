/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab4_task1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author gtoma
 */
public class Main {
    public static void main(String[] args) throws IOException {
        
        Stream<Path> paths = Files.list(Paths.get("pollutionData[1]"));
        
        Stream<String> lines = paths.flatMap(p -> {
                                        try {
                                            return Files.lines(p);
                                        } catch (IOException ex) {
                                            return Stream.of("");
                                        }
                                       
                                    });
        
        Writer writer = new BufferedWriter(new FileWriter("pollutionData-all.csv"));
        
        lines.filter(line ->  PollutionReading.canParse(line))
             .map(line -> new PollutionReading(line))
             .sorted()
             .forEach(pr -> {
                 try{
                     writer.write(pr.toString() + "\n");
                 } catch (IOException ex){
                 }
             });
        
        Stream<Path> paths2 = Files.list(Paths.get("pollutionData[1]"));
        
        List<String> lines2 = Files.readAllLines(Paths.get("pollutionData-all.csv"));
        
        System.out.println("Broj datoteka: " + paths2.count());
        System.out.println("Broj linija: " + lines2.size());
        
    }
}
