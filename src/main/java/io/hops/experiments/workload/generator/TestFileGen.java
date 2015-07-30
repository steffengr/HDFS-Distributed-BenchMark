/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.workload.generator;


/**
 *
 * @author salman
 */
public class TestFileGen {
    public static void main(String[] argv){
        TreeFileGenerator gen = new TreeFileGenerator("/test",100,10,0);
        
        
        for(int i = 0; i < 100; i++){
            System.out.println(gen.getDirToCreate());
        }
        
        
        for(int i = 0; i < 150; i++){
            System.out.println(gen.getFileToCreate());
        }
    }
        
}
