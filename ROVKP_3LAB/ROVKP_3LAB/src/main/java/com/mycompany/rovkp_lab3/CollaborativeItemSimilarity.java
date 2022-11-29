/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab3;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

/**
 *
 * @author gtoma
 */
public class CollaborativeItemSimilarity implements ItemSimilarity {

    private final double[][] matrix;
    private final double[] norms;
    private final Map<Integer, Long> seqIdMap;
    private final Map<Long, Integer> idSeqMap;

    public CollaborativeItemSimilarity(DataModel model) throws TasteException {
        int n = model.getNumItems();
        matrix = new double[n][n];
        norms = new double[n];
        seqIdMap = new HashMap<>();
        idSeqMap = new HashMap<>();

        calculateCollaborativeModelMatrix(model);
    }

    private void calculateCollaborativeModelMatrix(DataModel model) throws TasteException {

        int counter = 0;
        LongPrimitiveIterator iterator = model.getUserIDs();
        while (iterator.hasNext()) {
            long userId = iterator.nextLong();

            for (long ratedItemId1 : model.getItemIDsFromUser(userId)) {

                //get correct item1 seq num
                Integer seqId1 = idSeqMap.get(ratedItemId1);
                if (seqId1 == null) {
                    seqId1 = counter++;
                    seqIdMap.put(seqId1, ratedItemId1);
                    idSeqMap.put(ratedItemId1, seqId1);
                }

                norms[seqId1] += Math.pow(model.getPreferenceValue(userId, ratedItemId1), 2);

                for (long ratedItemId2 : model.getItemIDsFromUser(userId)) {

                    //get correct item2 seq num
                    Integer seqId2 = idSeqMap.get(ratedItemId2);
                    if (seqId2 == null) {
                        seqId2 = counter++;
                        seqIdMap.put(seqId2, ratedItemId2);
                        idSeqMap.put(ratedItemId2, seqId2);
                    }

                    matrix[seqId1][seqId2] += model.getPreferenceValue(userId, ratedItemId1)
                            * model.getPreferenceValue(userId, ratedItemId2);
                }
            }
        }

	//get cosine similarity from similarity sums
        for (int seqId1 = 0; seqId1 < matrix.length; seqId1++) {
            for (int seqId2 = 0; seqId2 < matrix.length; seqId2++) {
                if (matrix[seqId1][seqId2] != 0) {                                       
                    matrix[seqId1][seqId2] /= Math.sqrt(norms[seqId1]) * Math.sqrt(norms[seqId2]);
                }
            }
        }
    }

    @Override
    public double itemSimilarity(long itemID1, long itemID2) throws TasteException {
        if(idSeqMap.containsKey(itemID1) && idSeqMap.containsKey(itemID2)){
           int id1 = idSeqMap.get(itemID1);
           int id2 = idSeqMap.get(itemID2);
           
           return matrix[id1][id2];
        }
        return 0;
    }

    @Override
    public double[] itemSimilarities(long itemID1, long[] itemID2s) throws TasteException {
        int n = itemID2s.length;
        double[] help = new double[n];
        
        for(int i = 0; i < n; ++i){
            help[i] = itemSimilarity(itemID1, itemID2s[i]);
        }
        
        return help;
    }

    @Override
    public long[] allSimilarItemIDs(long itemID) throws TasteException {
        int id = idSeqMap.get(itemID);
        
        List<Long> idList = new ArrayList<>();
        
        for(int i = 0; i < matrix.length; ++i){
            if(matrix[id][i] > 0){
                idList.add(seqIdMap.get(i));
            }
        }
        
        long[] allIds = new long[idList.size()];
        
        for(int i = 0; i < idList.size(); ++i){
            allIds[i] = idList.get(i);
        }
        
        return allIds;
    }

    @Override
    public void refresh(Collection<Refreshable> alreadyRefreshed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
