/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_3dz_task1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.queryparser.classic.ParseException;

/**
 *
 * @author gtoma
 */
public class JokeMain {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        
        String uri = "C:\\Code\\NetBeans\\jester_dataset_2[1]\\jester_items.dat";
        Path path = Paths.get(uri);
        
        String file = new String(Files.readAllBytes(path), "UTF-8");
        
        JokeParser jokeParser = new JokeParser();
        jokeParser.parse(file);
        
        Map<Integer, String> jokes = jokeParser.getJokes();
        
        //for(int i : jokes.keySet()){
        //    System.out.println("i = " + i + " joke = " + jokes.get(i));
        //}
        
        JokeIndexer indexer = new JokeIndexer();
        double[][] matrix = null;
        try {
            matrix = indexer.getSimilarityMatrix(jokes);
        } catch (ParseException ex) {
            Logger.getLogger(JokeMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String destination = "C:\\Code\\NetBeans\\item_similarity.csv";
        Writer writer = new BufferedWriter(new FileWriter(destination));
        int counter = 0;
        for (int i = 0; i < matrix.length; ++i){
            for( int j = i + 1; j < matrix.length; ++j){
                counter++;
                if(i == 99 ){
                    System.out.println("j = " + j + "sim = " + matrix[i][j] );
                }    
                writer.write((i + 1) + "," + (j + 1) + "," + matrix[i][j] + "\n");
                
                //System.out.println(counter + " : " + (i + 1) + "," + (j + 1) + "," + matrix[i][j] + "\n");
               
            }
        }
        System.out.println(counter);
    }

    
}
