/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.benchmarks.common;

/**
 *
 * @author salman
 */
public enum BenchmarkOperations {
    
    CREATE_FILE ("CREATE_FILE"),
    READ_FILE   ("READ_FILE"),
    RENAME_FILE ("RENAME_FILE"),
    DELETE_FILE ("DELETE_FILE"),
    STAT_DIR    ("STAT_DIR"),
    STAT_FILE   ("STAT_FILE"),
    CHMOD_FILE  ("CHMOD_FILE"),
    MKDIRS      ("MKDIR");

    private final String phase;
    private BenchmarkOperations(String phase){
        this.phase = phase;
    }
    
    public boolean equals(BenchmarkOperations otherName){
        return (otherName == null)? false:phase.equals(otherName.toString());
    }

    public String toString(){
       return phase;
    }
}

