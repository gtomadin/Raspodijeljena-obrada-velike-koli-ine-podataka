/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab2_task2;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author gtoma
 */
public class CellsReducer extends Reducer<IntWritable, Text, IntWritable, Text>{

    @Override
    protected void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        
        double[][] TotalAmountByCell = new double[150][150];
        int[][] RidesByCell = new int[150][150];
        
        
        for(Text value : values){
            CellWritable cell = new CellWritable(value);
            int cellNumber = cell.getCellNumber().get();
            
            int cellDropoffLon = cellNumber % 150;
            int cellDropoffLat = (cellNumber - cellDropoffLon) / 150;
            
            double totalAmount = cell.getTotalAmount().get();
            
            if(cellDropoffLon >= 1 && cellDropoffLon <= 150 && cellDropoffLat >= 1 && cellDropoffLat <= 150){
                TotalAmountByCell[cellDropoffLon - 1][cellDropoffLat - 1] += totalAmount;
                RidesByCell[cellDropoffLon - 1][cellDropoffLat - 1]++;
                
            }
            
            
        }
        
        
        int cellDropoffLon = key.get() % 150;
        int cellDropoffLat = (key.get() - cellDropoffLon) / 150;
        
        if(cellDropoffLon >= 1 && cellDropoffLon <= 150 && cellDropoffLat >= 1 && cellDropoffLat <= 150){
            int rides = RidesByCell[cellDropoffLon - 1][cellDropoffLat - 1];
            double amount = TotalAmountByCell[cellDropoffLon - 1][cellDropoffLat - 1];        
            Text result = new Text("," + cellDropoffLon + "." + cellDropoffLat + "," +  rides + "," + amount);
            context.write(key, result);
        }
        
    }
    
    
    
}
