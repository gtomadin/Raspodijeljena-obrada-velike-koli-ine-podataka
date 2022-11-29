/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_4dz_task1;

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
        
        Stream<Path> paths = Files.list(Paths.get("sensorscope-monitor[2]"));
        
        
        
        Stream<String> lines = paths.filter(p -> p.getFileName().toString().startsWith("sensorscope") && !p.getFileName().toString().endsWith("def.txt"))
                                    .flatMap(p -> {
                                        try {
                                            return Files.lines(p);
                                        } catch (IOException ex) {
                                            return Stream.of("");
                                        }
                                       
                                    });
        
        //lines.forEach(System.out::println);
        
        
        
        Writer writer = new BufferedWriter(new FileWriter("sensorscope-monitor-all.csv"));
        
        lines.map(line -> new SensorscopeReading(line))
             .sorted()
             .forEach(sr -> {
                try {
                    writer.write(sr.toString() + "\n");
                    
                } catch (IOException ex) {
                }
            });
        
        Stream<Path> paths2 = Files.list(Paths.get("sensorscope-monitor[2]"));
        
        List<String> lines2 = Files.readAllLines(Paths.get("sensorscope-monitor-all.csv"));
        
        System.out.println("Broj datoteka: " + paths2.filter(p -> !p.getFileName().toString().endsWith("def.txt")).count());
        System.out.println("Broj linija: " + lines2.size());
    }
}
