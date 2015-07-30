/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.benchmarks.rawthroughput;

import io.hops.experiments.controller.commands.BenchmarkCommand;
import io.hops.experiments.benchmarks.common.BenchmarkOperations;
import io.hops.experiments.benchmarks.common.BenchmarkType;


/**
 *
 * @author salman
 */
public class RawBenchmarkCommand {
     public static class Request implements BenchmarkCommand.Request{

        private final BenchmarkOperations phase;
        private final long duration;

        public Request(BenchmarkOperations phase, long duration) {
            this.phase = phase;
            this.duration = duration;
        }

        public BenchmarkOperations getPhase() {
            return phase;
        }

        public long getDurationInMS() {
            return duration;
        }

        @Override
        public BenchmarkType getBenchMarkType() {
            return BenchmarkType.RAW;
        }
    }

    public static class Response implements BenchmarkCommand.Response{

        private final BenchmarkOperations phase;
        private final long runTime;
        private final long totalSuccessfulOps;
        private final long totalFailedOps;
        private final double opsPerSec;

        public Response(BenchmarkOperations phase, long runTime, long totalSuccessfulOps, long totalFailedOps, double opsPerSec) {
            this.phase = phase;
            this.runTime = runTime;
            this.totalSuccessfulOps = totalSuccessfulOps;
            this.totalFailedOps = totalFailedOps;
            this.opsPerSec = opsPerSec;
        }

        public BenchmarkOperations getPhase() {
            return phase;
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
