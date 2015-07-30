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
public interface BenchmarkCommand {
    public static interface Request extends Serializable{
        public BenchmarkType getBenchMarkType();
    }

    public static interface Response extends Serializable{
    }
}
