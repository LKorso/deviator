package com.sdr.services.distributions;

import com.sdr.services.RBasedService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class NormalDistribution extends RBasedService implements Distribution{

    private static final String NORMAL_DISTRIBUTION_SCRIPT = "rnorm(%s, mean = %s, sd = %s)";
    private static final String NORMAL_DISTRIBUTION_SORTED_SCRIPT = "sort(" + NORMAL_DISTRIBUTION_SCRIPT + ")";

    private Long observationNumber;
    private Double mean;
    private Double standardDeviation;
    private boolean sorted = false;
    private List<Double> distribution;

    public NormalDistribution() {
    }

    public NormalDistribution(Long observationNumber, Double mean, Double standardDeviation, boolean sorted) {
        this.observationNumber = observationNumber;
        this.mean = mean;
        this.standardDeviation = standardDeviation;
        this.sorted = sorted;
    }

    public List<Double> calculateDistribution() {
        distribution = DoubleStream.of(rEngine.eval(setUpScript()).asDoubleArray()).mapToObj(Double::valueOf).collect(Collectors.toList());
        System.out.println(distribution);
        return distribution;
    }

    private String setUpScript(){
        String script;

        if (sorted) {
            script = String.format(NORMAL_DISTRIBUTION_SORTED_SCRIPT, observationNumber, mean, standardDeviation);
        } else {
            script = String.format(NORMAL_DISTRIBUTION_SCRIPT, observationNumber, mean, standardDeviation);
        }
        System.out.println(script);

        return script;
    }
}