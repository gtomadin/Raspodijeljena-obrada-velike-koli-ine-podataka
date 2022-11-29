/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_3dz_task2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 *
 * @author gtoma
 */
public class CollaborativeRecommendation {
    public static void main(String[] args) throws IOException, TasteException {
        
        DataModel model = new FileDataModel(new File("C:/Code/NetBeans/jester_dataset_2[1]/jester_ratings.dat"), "\\s+");
        
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.9, similarity, model);
        
        UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        
        List<RecommendedItem> recommendations = recommender.recommend(22, 10);
        
        for(RecommendedItem recommendation : recommendations){
            System.out.println(recommendation);
        }
        
        RecommenderBuilder builder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        
                UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.9, similarity, model);
        
               return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
        
        RecommenderEvaluator recEvaluator = new RMSRecommenderEvaluator();
        
        double score = recEvaluator.evaluate(builder, null, model, 0.3, 0.3);
        
        System.out.println("Score = " + score);
        
        System.exit(0);
        
        String destination = "C:\\Code\\NetBeans\\task2_output.csv";
        Writer writer = new BufferedWriter(new FileWriter(destination));
        LongPrimitiveIterator it = model.getUserIDs();
        for(int i = 1; i <= 100; ++i){
            
            writer.write(it.peek() + "\n");
            
            List<RecommendedItem> rec = recommender.recommend(it.nextLong(), 10);
           
            for(RecommendedItem recommendation : rec){
                writer.write(recommendation + "\n");
            }
            writer.write("\n");
        }
                
    }
}
