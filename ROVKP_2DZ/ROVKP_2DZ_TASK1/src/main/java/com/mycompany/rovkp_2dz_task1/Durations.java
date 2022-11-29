/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rovkp_2dz_task1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.WritableComparable;

/**
 *
 * @author gtoma
 */
public class Durations implements WritableComparable<Durations>{
    
    private DoubleWritable totalDuration;
    private DoubleWritable minDuration;
    private DoubleWritable maxDuration;

    public Durations(){
        this(0, Double.MAX_VALUE, 0);
    }
    
    public Durations(double totalDuration, double minDuration, double maxDuration) {
        this.totalDuration = new DoubleWritable(totalDuration);
        this.minDuration = new DoubleWritable(minDuration);
        this.maxDuration = new DoubleWritable(maxDuration);
    }

    public DoubleWritable getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(double totalDuration) {
        this.totalDuration.set(totalDuration);
    }

    public DoubleWritable getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(double minDuration) {
        this.minDuration.set(minDuration);
    }

    public DoubleWritable getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(double maxDuration) {
        this.maxDuration.set(maxDuration);
    }
    
    
    
    @Override
    public void write(DataOutput d) throws IOException {
       totalDuration.write(d);
       minDuration.write(d);
       maxDuration.write(d);
    }

    @Override
    public void readFields(DataInput di) throws IOException {
       totalDuration.readFields(di);
       minDuration.readFields(di);
       maxDuration.readFields(di);
    }

    @Override
    public int compareTo(Durations o) {
        return totalDuration.compareTo(o.totalDuration);
    }

    @Override
    public String toString() {
        return "Durations{" + "totalDuration=" + totalDuration + ", minDuration=" + minDuration + ", maxDuration=" + maxDuration + '}';
    }
    
    
    
}
