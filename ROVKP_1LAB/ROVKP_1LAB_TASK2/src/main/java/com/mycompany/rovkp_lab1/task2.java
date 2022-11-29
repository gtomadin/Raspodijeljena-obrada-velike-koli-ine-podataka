/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

/**
 *
 * @author gtoma
 */
public class task2 {
        protected static int bookCounter = 0;
	protected static int lineCounter = 0;
	
	
	
	public static void main(String[] args) throws URISyntaxException {
		
                long StartTime = System.currentTimeMillis();
		
                Path gutenberg = Paths.get("gutenberg");
		
                Configuration conf = new Configuration();
                
		try {
			
                        FileSystem hdfs = FileSystem.get(new URI("hdfs://primary.streamslab.fer.hr:8020"), conf);
                        
                        org.apache.hadoop.fs.Path hdfsPath = new org.apache.hadoop.fs.Path("/user/rovkp/gtomadin2/gutenberg_books.txt");
                        
			BufferedWriter bw = new BufferedWriter( new  OutputStreamWriter(hdfs.create(hdfsPath)));
		
			Files.walkFileTree(gutenberg,  new FileVisitor<Path>() {

                            @Override
                            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                                return FileVisitResult.CONTINUE;
                            }

                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				     
                                if(file.toString().endsWith(".txt")){
                               
                                    ++bookCounter;

                                    BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
                                    //System.out.println(".visitFile()");
                                    String line;
                                     
                                    while(br.readLine() != null) {
                                        line = br.readLine();
                                        ++lineCounter;
                                        //System.out.println(line);
                                        bw.write(line + "\n");
                                    }
                                }		
                                return FileVisitResult.CONTINUE;
                               }
                            @Override
                            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
                            }

                            @Override
                            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            	return FileVisitResult.CONTINUE;
                            }
			});
			
			System.out.println("Number books: " + bookCounter);
			System.out.println("Number lines: " +lineCounter);
			long endTime = System.currentTimeMillis();
			long duration = endTime - StartTime;
			
			System.out.println("Time: " + duration/1000);
			
                        bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
