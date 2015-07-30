package io.hops.experiments.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;


import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateRandomFile {

  static int numFiles;
  static int numThreads;

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      throw new RuntimeException("Wrong number of arguments. Expected two arguments. arg1: number of files, arg2 number of threads");
      
    }
    else{
      numFiles = Integer.parseInt(args[0]);
      numThreads = Integer.parseInt(args[1]);
      System.out.println("Creating "+numFiles+" files using "+numThreads+" threads");
    }
    
    
    CreateRandomFile f = new CreateRandomFile();
    f.startWriting();
    System.exit(0);
  }
  
  public void startWriting() throws InterruptedException{
    
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    for (int i = 0; i < numFiles; i++) {
      Runnable worker = new Writer();
      executor.execute(worker);
    }
    executor.shutdown();
    while (!executor.isTerminated()) {
      Thread.sleep(100);
    }
  }

  public class Writer implements Runnable {

    private UUID fileName;

    public Writer() {
      fileName = UUID.randomUUID();
    }

    @Override
    public void run() {
      try{
    System.out.println("Starting to write "+fileName.toString());
    Configuration conf = new Configuration();
    DistributedFileSystem dfs = (DistributedFileSystem) FileSystem.get(conf);
    long startTime = System.currentTimeMillis();
    Path path = new Path("/"+fileName.toString()+"/"+fileName.toString());
    BenchmarkUtils.createFile(dfs, path, (short) 1, 1);
    System.out.println("Finished file "+fileName.toString()+". Time taken : " + (System.currentTimeMillis()-startTime)/1000 + " sec");
      }catch(Exception e){
        e.printStackTrace();
      }
    }
  }
}
