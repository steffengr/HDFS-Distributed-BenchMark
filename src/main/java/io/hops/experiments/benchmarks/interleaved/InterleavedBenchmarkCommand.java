/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.benchmarks.interleaved;

import io.hops.experiments.controller.commands.BenchmarkCommand;
import io.hops.experiments.benchmarks.common.BenchmarkType;


/**
 *
 * @author salman
 */
public class InterleavedBenchmarkCommand {
     public static class Request implements BenchmarkCommand.Request{

        private int createPercent;
        private int readPercent;
        private int renamePercent;
        private int deletePercent;
        private int fileStatPercent;
        private int dirStatPercent;
        private int chmodPercent;
        private int mkdirPercent;
        private long maxOperations;
        private long fileSize;
        private short replicationFactor;
        private String baseDir;

        public Request(int createPercent, int readPercent, int renamePercent, int deletePercent, int fileStatPercent,int dirStatPercent,
                int chmodPercent, int mkdirPercent, long maxOperations, long fileSize, short replicationFactor, String baseDir) {
            this.createPercent = createPercent;
            this.readPercent = readPercent;
            this.renamePercent = renamePercent;
            this.deletePercent = deletePercent;
            this.fileStatPercent = fileStatPercent;
            this.dirStatPercent = dirStatPercent;
            this.chmodPercent = chmodPercent;
            this.mkdirPercent = mkdirPercent;
            this.maxOperations = maxOperations;
            this.fileSize = fileSize;
            this.replicationFactor = replicationFactor;
            this.baseDir = baseDir;
        }

        public int getCreatePercent() {
            return createPercent;
        }

        public int getReadPercent() {
            return readPercent;
        }

        public int getRenamePercent() {
            return renamePercent;
        }

        public int getDeletePercent() {
            return deletePercent;
        }

        public int getFileStatPercent() {
            return fileStatPercent;
        }
        
        public int getDirStatPercent() {
            return dirStatPercent;
        }

        public int getChmodPercent() {
            return chmodPercent;
        }

        public long getMaxOperations() {
            return maxOperations;
        }

        public long getFileSize() {
            return fileSize;
        }

        public short getReplicationFactor() {
            return replicationFactor;
        }

        public String getBaseDir() {
            return baseDir;
        }

        public int getMkdirPercent() {
            return mkdirPercent;
        }
        
        @Override
        public BenchmarkType getBenchMarkType() {
            return BenchmarkType.INTERLEAVED;
        }
    }

    public static class Response implements BenchmarkCommand.Response{
        private final long runTime;
        private final long totalSuccessfulOps;
        private final long totalFailedOps;
        private final double opsPerSec;

        public Response(long runTime, long totalSuccessfulOps, long totalFailedOps, double opsPerSec) {
            this.runTime = runTime;
            this.totalSuccessfulOps = totalSuccessfulOps;
            this.totalFailedOps = totalFailedOps;
            this.opsPerSec = opsPerSec;
        }

        public long getRunTime() {
            return runTime;
        }

        public long getTotalSuccessfulOps() {
            return totalSuccessfulOps;
        }

        public long getTotalFailedOps() {
            return totalFailedOps;
        }

        public double getOpsPerSec() {
            return opsPerSec;
        }
    }
}
