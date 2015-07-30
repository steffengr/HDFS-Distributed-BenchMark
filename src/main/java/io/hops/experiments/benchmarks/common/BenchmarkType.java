/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.benchmarks.common;

/**
 *
 * @author salman
 */
public enum BenchmarkType {
    RAW ("RAW"),
    INTERLEAVED   ("INTERLEAVED"),
    BR ("BR");
    
    private final String type;
    private BenchmarkType(String type){
        this.type = type;
    }
    
    public boolean equals(BenchmarkType otherName){
        return (otherName == null)? false:type.equals(otherName.toString());
    }

    @Override
    public String toString(){
       return type;
    }
}
