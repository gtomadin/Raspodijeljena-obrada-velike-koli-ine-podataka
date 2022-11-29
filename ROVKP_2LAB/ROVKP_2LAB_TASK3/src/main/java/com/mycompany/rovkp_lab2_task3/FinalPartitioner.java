/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab2_task3;

import org.apache.hadoop.mapreduce.Partitioner;

/**
 *
 * @author gtoma
 */
class FinalPartitioner extends Partitioner {

    @Override
    public int getPartition(Object key, Object value, int i) {
        return 0;
    }
    
}
