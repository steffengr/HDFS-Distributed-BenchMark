/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.coin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import io.hops.experiments.benchmarks.common.BenchmarkOperations;

/**
 *
 * @author salman
 */
public class CoinTest {
    public static void main(String [] argv) throws FileNotFoundException, IOException{
        MultiFaceCoin coin = new MultiFaceCoin(20,20,20,20,15,5,0,0);
        HashMap<BenchmarkOperations,Integer> map  = new HashMap<BenchmarkOperations,Integer>();
        
        int times = 100000;
        
        for(int i =0 ; i < times;i++){
            BenchmarkOperations op = coin.flip();
            
            Integer count = map.get(op);
            if(count == null){
                count = new Integer(0);
                map.put(op,count);
            }
            count = count + 1;
            map.put(op, count);
        }
        
        
        for(BenchmarkOperations op:map.keySet()){
            System.out.println("Operation "+op+" Count "+map.get(op)+" = "+Math.ceil(((map.get(op)/(double)times)*100))+"%");
        }
        

    }
}
