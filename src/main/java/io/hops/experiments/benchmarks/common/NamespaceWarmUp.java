/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.benchmarks.common;

import io.hops.experiments.controller.commands.WarmUpCommand;

/**
 *
 * @author salman
 */
public class NamespaceWarmUp {

    public static class Request implements WarmUpCommand.Request {
        private BenchmarkType benchMarkType;
        private int filesToCreate;
        private short replicationFactor;
        private long fileSize;
        private String baseDir;
        

        public Request(BenchmarkType benchMarkType, int filesToCreate,
                short replicationFactor, long fileSize,String baseDir) {
            this.benchMarkType = benchMarkType;
            this.filesToCreate = filesToCreate;
            this.replicationFactor = replicationFactor;
            this.fileSize = fileSize;
            this.baseDir = baseDir;
        }

        public String getBaseDir() {
            return baseDir;
        }

        public int getFilesToCreate() {
            return filesToCreate;
        }
        
        
        public BenchmarkType getBenchMarkType() {
            return benchMarkType;
        }

        public short getReplicationFactor() {
            return replicationFactor;
        }

        public long getFileSize() {
            return fileSize;
        }

    }
  
   public static class Response implements WarmUpCommand.Response {
       
   }
}
