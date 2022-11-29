/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab2_task2;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author gtoma
 */
public class CellsMapper extends Mapper<LongWritable, Text, IntWritable, Text>{

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
        double BEGIN_LON = -74.913585;
        double BEGIN_LAT = 41.474937;
        
        double GRID_WIDTH = 0.008983112;
        double GRID_LENGTH = 0.011972;
                
        DEBSRecordParser parser = new DEBSRecordParser();
        
        
        parser.parse(value.toString());
        
        int cellDropoffLon = (int) (((parser.getDropoffLongitude() - BEGIN_LON) / GRID_LENGTH)) + 1;
        int cellDropoffLat = (int) (((BEGIN_LAT - parser.getDropoffLatitude()) / GRID_WIDTH)) + 1;
        
        //System.out.println("cell : " + cellDropoffLon + " , " + cellDropoffLat);
        
        if(cellDropoffLon >= 1 && cellDropoffLon <= 150 && cellDropoffLat >= 1 && cellDropoffLat <= 150){ 
            int intcellNumber = cellDropoffLon + cellDropoffLat * 150;

            double totalAmount = parser.getTotalAmount();

            CellWritable cell = new CellWritable(intcellNumber, totalAmount);

            context.write(new IntWritable(intcellNumber), cell.toText());
        }
    }
    
    
    
}
