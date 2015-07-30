/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.controller.commands;

import io.hops.experiments.benchmarks.common.BenchmarkType;

import java.io.Serializable;

/**
 *
 * @author salman
 */
public class Handshake implements Serializable {

    public static class Request implements Serializable {

        private int slaveId;
        private final int numThreads;
        private final long fileSize;
        private final short replicationFactor;
        private BenchmarkType benchMarkType;
        private String baseDir;
        private boolean enableRemoteLogging;
        private int remoteLoggingPort;
        private String namenodeRpcAddress;
        private String namenodeSelectionPolicy;
        private long nameNodeListRefreshTime;

        public Request(int numThreads, long fileSize, short replicationFactor,
                BenchmarkType benchMarkType, String baseDir,
                boolean enableRemoteLogging, int remoteLoggingPort,
                String namenodeRpcAddress, String namenodeSelectionPolicy,
                long nameNodeListRefreshTime) {
            this.numThreads = numThreads;
            this.fileSize = fileSize;
            this.benchMarkType = benchMarkType;
            this.replicationFactor = replicationFactor;
            this.baseDir = baseDir;
            this.enableRemoteLogging = enableRemoteLogging;
            this.remoteLoggingPort = remoteLoggingPort;
            this.nameNodeListRefreshTime = nameNodeListRefreshTime;
            this.namenodeSelectionPolicy = namenodeSelectionPolicy;
            this.namenodeRpcAddress = namenodeRpcAddress;
        }

        public int getNumThreads() {
            return numThreads;
        }

        public long getFileSize() {
            return fileSize;
        }

        public BenchmarkType getBenchMarkType() {
            return benchMarkType;
        }

        public short getReplicationFactor() {
            return replicationFactor;
        }

        public String getBaseDir() {
            return baseDir;
        }      

        public boolean isEnableRemoteLogging() {
            return enableRemoteLogging;
        }

        public int getRemoteLoggingPort() {
            return remoteLoggingPort;
        }

        public String getNamenodeRpcAddress() {
            return namenodeRpcAddress;
        }

        public String getNamenodeSelectionPolicy() {
            return namenodeSelectionPolicy;
        }

        public long getNameNodeListRefreshTime() {
            return nameNodeListRefreshTime;
        }

        public int getSlaveId() {
            return slaveId;
        }

        public void setSlaveId(int slaveId) {
            this.slaveId = slaveId;
        }
    }
  
   public static class Response implements Serializable {
       
   }
}
