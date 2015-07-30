/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.coin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import io.hops.experiments.benchmarks.common.BenchmarkOperations;

/**
 *
 * @author salman
 */
public class MultiFaceCoin {
    
    private int create;
    private int read;
    private int rename;
    private int delete;
    private int statFile;
    private int statDir;
    private int chmod;
    private int mkdirs;
    private Random rand;
    private int expansion = 10;
    //1000 face dice
    ArrayList<BenchmarkOperations> dice = new ArrayList<BenchmarkOperations>();

    public MultiFaceCoin(int create, int read, int rename, int delete, int statFile, int statDir, int chmod, int mkdirs) {
        this.create = create;
        this.read = read;
        this.rename = rename;
        this.delete = delete;
        this.chmod = chmod;
        this.mkdirs = mkdirs;
        this.statFile = statFile;
        this.statDir = statDir;
        this.rand = new Random(System.currentTimeMillis());
        
        createCoin();
    }

    private void createCoin(){
        
       System.out.println("Percentages create: "+create+" read: "+read+" mdir: "+mkdirs+" rename: "+rename+" delete: "+delete+" stat File: "+statFile+" statDir: "+statDir+" chmod: "+chmod);
       int total = create+read+rename+delete+statFile+statDir+chmod+mkdirs;
       if(total != 100){
           throw new IllegalArgumentException("All probabilities should add to 100. Got: "+total);
       }

       for(int i = 0 ; i < create * expansion ; i++){
           dice.add(BenchmarkOperations.CREATE_FILE);
       }
       
       for(int i = 0 ; i < read * expansion ; i++){
           dice.add(BenchmarkOperations.READ_FILE);
       }
       
       for(int i = 0 ; i < rename * expansion ; i++){
           dice.add(BenchmarkOperations.RENAME_FILE);
       }
       
       for(int i = 0 ; i < delete * expansion ; i++){
           dice.add(BenchmarkOperations.DELETE_FILE);
       }
       
       for(int i = 0 ; i < statFile * expansion ; i++){
           dice.add(BenchmarkOperations.STAT_FILE);
       }
       
       for(int i = 0 ; i < statDir * expansion ; i++){
           dice.add(BenchmarkOperations.STAT_DIR);
       }
       
       for(int i = 0 ; i < chmod * expansion ; i++){
           dice.add(BenchmarkOperations.CHMOD_FILE);
       }
       
       for(int i = 0 ; i < mkdirs * expansion ; i++){
           dice.add(BenchmarkOperations.MKDIRS);
       }
       
       if (dice.size() != expansion * 100){
           throw new IllegalStateException("Dice is not properfly created");
       }
       
       Collections.shuffle(dice);
    }
    
    public BenchmarkOperations flip(){
        int choice = rand.nextInt(100* expansion);
        return dice.get(choice);
    }
}
