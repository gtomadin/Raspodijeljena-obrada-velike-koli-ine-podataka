/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_3dz_task2;

import java.io.File;
import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.file.FileItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

/**
 *
 * @author gtoma
 */
public class ItemBasedRecommendation {
    public static void main(String[] args) throws Exception {
        
        DataModel model = new FileDataModel(new File("C:/Code/NetBeans/jester_dataset_2[1]/jester_ratings.dat"), "\\s+");
        
        ItemSimilarity similarity = new FileItemSimilarity(new File("C:/Code/NetBeans/item_similarity.csv"));
        
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
        
        List<RecommendedItem> recommendations = recommender.recommend(22, 10);
        
        for(RecommendedItem recommendation : recommendations){
            System.out.println(recommendation);
        }
        
        
        
        RecommenderBuilder builder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
               ItemSimilarity similarity = new FileItemSimilarity(new File("C:/Code/NetBeans/item_similarity.csv")); 
               
               return new GenericItemBasedRecommender(model, similarity);
            }
        };
        
        
        
        RecommenderEvaluator recEvaluator = new RMSRecommenderEvaluator();
        
        double score = recEvaluator.evaluate(builder, null, model, 0.3, 0.3);
        
        System.err.println("Score = " + score);
    }
}
