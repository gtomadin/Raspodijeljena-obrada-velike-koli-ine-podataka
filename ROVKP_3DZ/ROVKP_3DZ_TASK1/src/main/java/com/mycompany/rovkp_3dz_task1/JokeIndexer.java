/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_3dz_task1;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 *
 * @author gtoma
 */
public class JokeIndexer {
    
    double[][] matrix;
   
    double[][] getSimilarityMatrix(Map<Integer, String> jokes) throws IOException, ParseException {
        
        calculate(jokes);
        normalize();
        return matrix;
    }

    private void calculate(Map<Integer, String> jokes) throws IOException, ParseException {
        
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory index = new RAMDirectory();
        
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        
        IndexWriter w = new IndexWriter(index, config);
        
        
        for(Integer key : jokes.keySet()){
            Document doc = new Document();
            
            FieldType idFieldType = new FieldType();
            idFieldType.setStored(true);
            idFieldType.setTokenized(false);
            idFieldType.setIndexOptions(IndexOptions.NONE);
            
            Field idField = new Field("ID", key.toString(), idFieldType);
            TextField textField = new TextField("TEXT", jokes.get(key), Field.Store.YES);
            
            doc.add(textField);
            doc.add(idField);
            
            w.addDocument(doc);
        } 
        
        
        w.close();
         
        matrix = new double[jokes.size()][jokes.size()];
        
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        
        for(int key : jokes.keySet()){
            
            Query query = new QueryParser("TEXT", analyzer).parse(QueryParser.escape(jokes.get(key)));
            
            TopDocs top = searcher.search(query, jokes.size());
            
            
            
            for(ScoreDoc scoreDoc : top.scoreDocs){
                String helpID = reader.document(scoreDoc.doc).get("ID");
                int ID = Integer.parseInt(helpID);
                matrix[key - 1][ID - 1] = scoreDoc.score;
            }
            
        }
       
        
        reader.close();
        
       
    }

    private void normalize() {
        
        double[][] newMatrix = new double[matrix.length][matrix.length];
        
        for(int i = 0; i < matrix.length; ++i){
            
            double max = Double.MIN_VALUE;
            
            for(int j = 0; j < matrix.length; ++j){
                if(max < matrix[i][j]){
                    max = matrix[i][j];
                }
            }
            
            for(int j = 0; j < matrix.length; ++j){
                newMatrix[i][j] = matrix[i][j] / max;
            }
        }
        
        for(int i = 0; i < matrix.length; ++i){
            for(int j = i; j < matrix.length; ++j){
                double average = (newMatrix[i][j] + newMatrix[j][i]) / 2;
                newMatrix[i][j] = newMatrix[j][i] = average;
                
            }
        }
        
        for(int i = 0; i < matrix.length; ++i){
            for(int j = 0; j < matrix.length; ++j){
                
                matrix[i][j] = newMatrix[i][j];
                        
            }
        }
        
    }

   
    
    
}
