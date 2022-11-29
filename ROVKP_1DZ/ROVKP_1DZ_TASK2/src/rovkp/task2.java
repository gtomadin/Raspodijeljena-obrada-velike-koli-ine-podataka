/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rovkp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author gtoma
 */
public class task2 {
    
        protected static int bookCounter = 0;
	protected static int lineCounter = 0;
	
	
	
	public static void main(String[] args) {
		
                long StartTime = System.currentTimeMillis();
		
                Path gutenberg = Paths.get("C:\\Code\\NetBeans\\ROVKP DZ.1\\ROVKP_1DZ\\gutenberg");
		
		try {
			
			BufferedWriter bw = new BufferedWriter( new  FileWriter("C:\\Code\\NetBeans\\ROVKP DZ.1\\ROVKP_1DZ\\gutenberg_books.txt"));
		
			Files.walkFileTree(gutenberg,  new FileVisitor<Path>() {

                            @Override
                            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                                return FileVisitResult.CONTINUE;
                            }

                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				
                                ++bookCounter;
				
                                BufferedReader br = new BufferedReader(new FileReader(file.toFile()));
						
                                String line;
						
                                while(br.readLine() != null) {
                                    line = br.readLine();
                                    ++lineCounter;
                                    bw.write(line + "\n");
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
			
			System.out.println("Time: " + duration);
			
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
