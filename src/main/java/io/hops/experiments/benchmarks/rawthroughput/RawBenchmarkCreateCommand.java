/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hops.experiments.benchmarks.rawthroughput;

import io.hops.experiments.controller.commands.BenchmarkCommand;
import io.hops.experiments.benchmarks.common.BenchmarkOperations;

import java.io.Serializable;

/**
 *
 * @author salman
 */
public class RawBenchmarkCreateCommand {
    public static class Request extends RawBenchmarkCommand.Request implements
        BenchmarkCommand{
        private final long maxFilesToCreate;

        public Request(long maxFilesToCreate, BenchmarkOperations phase, long duration) {
            super(phase, duration);
            this.maxFilesToCreate = maxFilesToCreate;
        }

        public long getMaxFilesToCreate() {
            return maxFilesToCreate;
        }
    }
}
