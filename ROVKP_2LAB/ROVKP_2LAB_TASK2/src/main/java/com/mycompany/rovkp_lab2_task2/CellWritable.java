/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_lab2_task2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

/**
 *
 * @author gtoma
 */
public class CellWritable implements WritableComparable<CellWritable>{
    
    private IntWritable cellNumber;
    private DoubleWritable totalAmount;
    
    public CellWritable(){
        this(0, 0);
    }
    
    public CellWritable(int cellNumber, double totalAmount){
        this.cellNumber = new IntWritable(cellNumber);
        this.totalAmount = new DoubleWritable(totalAmount);
    }

    CellWritable(Text value) {
        String s = value.toString();
        String[] splitted = s.split(",");
        
        int cell = Integer.parseInt(splitted[0]);
        double amount = Double.parseDouble(splitted[1]);
        
        this.cellNumber = new IntWritable(cell);
        this.totalAmount = new DoubleWritable(amount);
       
    }
    
    @Override
    public void write(DataOutput d) throws IOException {
        cellNumber.write(d);
        totalAmount.write(d);
    }

    @Override
    public void readFields(DataInput di) throws IOException {
        cellNumber.readFields(di);
        totalAmount.readFields(di);
    }

    @Override
    public int compareTo(CellWritable o) {
        return cellNumber.compareTo(o.cellNumber);
    }

    public IntWritable getCellNumber() {
        return cellNumber;
    }

    public DoubleWritable getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return  cellNumber + "," + totalAmount;
    }
    
    public Text toText(){
        return new Text(toString());
    }
}
