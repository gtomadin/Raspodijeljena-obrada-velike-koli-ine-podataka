/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

/**
 *
 * @author gtoma
 */
public class Main {

    public static void main(String[] args) throws IOException, TasteException, Exception {
        DataModel model = new FileDataModel(
                new File("jester_dataset_2[2]\\jester_ratings.dat"), "\t\t");
        ItemSimilarity collaborativeItemSimilarity = new CollaborativeItemSimilarity(model);
        ItemSimilarity contentItemSimilarity = new FileItemSimilarity(new File("item_similarity.csv"));
        
        
        int matrixsize = 150;
        double[][] collaborativeItemSimilarityMatrix = new double[matrixsize][matrixsize];
        double[][] contentItemSimilarityMatrix = new double[matrixsize][matrixsize];
        
        LongPrimitiveIterator iterator = model.getItemIDs();
        while (iterator.hasNext()) {
            long itemId = iterator.nextLong();

            for (long simItem : collaborativeItemSimilarity.allSimilarItemIDs(itemId)) {
                //System.out.println(itemId + ", " + simItem + ", " + collaborativeItemSimilarity.itemSimilarity(itemId, simItem) + ", " + contentItemSimilarity.itemSimilarity(itemId, simItem));
                
                int i = (int) (itemId - 1);
                int j = (int) (simItem - 1);
                
                if(Double.isNaN(collaborativeItemSimilarity.itemSimilarity(itemId, simItem))){
                    collaborativeItemSimilarityMatrix[i][j] = 0;
                }else{
                    collaborativeItemSimilarityMatrix[i][j] = collaborativeItemSimilarity.itemSimilarity(itemId, simItem);
                }
                
                if(Double.isNaN(contentItemSimilarity.itemSimilarity(itemId, simItem))){
                    collaborativeItemSimilarityMatrix[i][j] = 0;
                }else{
                    contentItemSimilarityMatrix[i][j] = contentItemSimilarity.itemSimilarity(itemId, simItem);
                }
                
                
            }
        }
        
        //printMatrix(collaborativeItemSimilarityMatrix);
        //printMatrix(contentItemSimilarityMatrix);
        
        double[][] normCollaborativeItemSimilarityMatrix = normalize(collaborativeItemSimilarityMatrix);
        double[][] normContentItemSimilarityMatrix = normalize(contentItemSimilarityMatrix);
        
        //printMatrix(normCollaborativeItemSimilarityMatrix);
        //printMatrix(normContentItemSimilarityMatrix);
        
        double factor = 0.5;
        double[][] hybridMatrix = getHybridMatrix(normCollaborativeItemSimilarityMatrix, normContentItemSimilarityMatrix, factor, (1 - factor));
    
        //printMatrix(hybridMatrix);
        
        writeInFile(hybridMatrix, "hybrid_matrix.csv");
        
    }

    private static void printMatrix(double[][] matrix) {
        for(int i = 0; i < matrix.length; ++i){
            for(int j = 0; j < matrix.length; ++j){
                System.out.println((i+1) + "," + (j+1) + "," + matrix[i][j]);
            }
        }
    }

    private static double[][] normalize(double[][] similarityMatrix) {
         double[][] matrix = new double[similarityMatrix.length][similarityMatrix.length];
        
        for(int i = 0; i< similarityMatrix.length; ++i){
            
            double sum = 0;
            
            for(int j = 0; j < similarityMatrix.length; ++j){
                
                sum += similarityMatrix[i][j];
            }
            
            for(int j = 0; j < similarityMatrix.length; ++j){
                if(sum != 0){
                    
                    matrix[i][j] = similarityMatrix[i][j] / sum;
                }
            }   
        }
        
        return matrix;
    }

    private static double[][] getHybridMatrix(double[][] similarityMatrix1, double[][] similarityMatrix2, double factor1, double factor2) throws Exception {
        if(similarityMatrix1.length != similarityMatrix2.length || similarityMatrix1[0].length != similarityMatrix2[0].length){
            throw new Exception();
        }
        
        double[][] matrix = new double[similarityMatrix1.length][similarityMatrix1.length];

        for(int i = 0; i < similarityMatrix1.length; ++i){
            for(int j = 0; j < similarityMatrix1.length; ++j){
                matrix[i][j] = factor1 * similarityMatrix1[i][j] + factor2 * similarityMatrix2[i][j];
            }
        }
        
        return matrix;
    }

    private static void writeInFile(double[][] hybridMatrix, String destination) throws IOException {
         Writer writer = new BufferedWriter(new FileWriter(destination));
        
        for (int i = 0; i < hybridMatrix.length; ++i){
            for( int j = 0; j < hybridMatrix.length; ++j){  
                writer.write((i + 1) + "," + (j + 1) + "," + hybridMatrix[i][j] + "\n");
            }
        }
    }
    
}
