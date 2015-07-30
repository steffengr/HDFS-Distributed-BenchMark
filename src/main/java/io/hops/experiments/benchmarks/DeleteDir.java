/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.benchmarks;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 *
 * @author salman
 */
public class DeleteDir {

  @Option(name = "-createDir", usage = "Create a dir. Supply Path")
  private String createDir = "";
  @Option(name = "-src", usage = "Src folder to delete")
  private String src = "";

  private Configuration conf = new Configuration();
  
  public static void main(String[] args) throws InterruptedException, IOException {
    new DeleteDir().runYouFool(args);
  }

  private void runYouFool(String[] args) throws InterruptedException, IOException {
    CmdLineParser parser = new CmdLineParser(this);

    // if you have a wider console, you could increase the value;
    // here 80 is also the default
    parser.setUsageWidth(80);

    try {
      // parse the arguments.
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      System.err.println(e.getMessage());
      parser.printUsage(System.err);
      System.err.println();
      return;
    }
    
    // do shit here
    DistributedFileSystem dfs = (DistributedFileSystem) FileSystem.newInstance(conf);
    if(createDir != ""){
      dfs.mkdirs(new Path(createDir)); 
    }
    
    if(src.equals("")){
      System.out.println("Invalid arguments. ");
      parser.printUsage(System.err);
      System.exit(0);
    }
    
    System.out.println("Src is:\""+src+"\"");
    Long startTime = System.currentTimeMillis();
    dfs.delete(new Path(src),true);
    Long endTime = System.currentTimeMillis();
    
    System.out.println("Delete time taken in ms "+(endTime- startTime));
    System.out.println("Delete time taken in minutes "+(endTime- startTime)/(double)(1000*60));
    
    System.exit(0);

  }
}
