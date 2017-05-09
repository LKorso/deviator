package com.sdr.domain;

import lombok.Data;
import org.rosuda.JRI.Rengine;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Data
public class Distribution {

    private static Rengine rEngine = new Rengine(new String[] { "--no-save" }, false, null);
    private static final String NORMAL_DISTRIBUTION_SCRIPT = "rnorm(%s, mean = %s, sd = %s)";
    private static final String NORMAL_DISTRIBUTION_SORTED_SCRIPT = "sort(" + NORMAL_DISTRIBUTION_SCRIPT + ")";
    private static final String MEAN_SCRIPT = "mean(%s)";
    private static final String STANDARD_DEVIATION_SCRIPT = "sd(%s, na.rm = FALSE)";
    private static final String PROBABILITY_SCRIPT = "dnorm(%s, mean = %s, sd = %s)";

    private static Long idGenerator = new Long(0);

    private Long id;
    private Long observationNumber;
    private Double mean;
    private Double standardDeviation;
    private boolean sorted = false;
    private List<Double> values;
    private List<Double> probability;
    private String rVector;

    public Distribution() {
        id = idGenerator++;
    }

    public Distribution(List<Double> values) {
        this();
        this.values = values;
    }

    public Distribution(Long observationNumber, Double mean, Double standardDeviation, boolean sorted) {
        this();
        this.observationNumber = observationNumber;
        this.mean = mean;
        this.standardDeviation = standardDeviation;
        this.sorted = sorted;
    }

    public List<Double> getValues() {
        if (values == null) {
            calculateDistribution();
        }
        return values;
    }

    public Double getMean() {
        if (mean == null) {
            calculateMean();
        }
        return mean;
    }

    public Double getStandardDeviation() {
        if (standardDeviation == null) {
            calculateStandardDeviation();
        }
        return standardDeviation;
    }

    public List<Double> getProbability() {
        if(probability == null) {
            calcualteProbability();
        }
        return  probability;
    }

    public void sort() {
        Collections.sort(values);
    }

    private void calculateDistribution() {
        values = DoubleStream
                .of(rEngine.eval(setUpDistributionScript()).asDoubleArray())
                .mapToObj(Double::valueOf)
                .collect(Collectors.toList());
    }

    private void calcualteProbability() {
        probability = DoubleStream
                .of(rEngine.eval(prepareScript(
                        PROBABILITY_SCRIPT,
                        distributionToRVector(values),
                        getMean(),
                        getStandardDeviation()
                    )
                ).asDoubleArray())
                .mapToObj(Double::valueOf)
                .collect(Collectors.toList());
    }

    private void calculateMean() {
        rEngine.eval("rVector = " + distributionToRVector(values));
        mean = calculateDoubleFromR(prepareScript(MEAN_SCRIPT, "rVector"));
    }

    private void calculateStandardDeviation() {
        rEngine.eval("rVector = " + distributionToRVector(values));
        standardDeviation = calculateDoubleFromR(
                prepareScript(STANDARD_DEVIATION_SCRIPT, "rVector")
        );
    }

    private String setUpDistributionScript(){
        String script;
        if (sorted) {
            script = prepareScript(NORMAL_DISTRIBUTION_SORTED_SCRIPT, observationNumber, mean, standardDeviation);
        } else {
            script = prepareScript(NORMAL_DISTRIBUTION_SCRIPT, observationNumber, mean, standardDeviation);
        }
        return script;
    }

    private String distributionToRVector(List<Double> distribution) {
        if (rVector == null) {
            final StringBuilder builder = new StringBuilder("c(");
            distribution.forEach(value -> builder.append(value + ","));
            rVector = builder.toString().substring(0, builder.length() - 1) + ")";
        }
        return rVector;
    }

    private Double calculateDoubleFromR(String script) {
        return roundDouble(rEngine.eval(script).asDouble(), 5);
    }

    private String prepareScript(String script, Object... variabels) {
        return String.format(script, variabels);
    }

    private Double roundDouble(Double value, int round) {
        return new BigDecimal(value).setScale(round, BigDecimal.ROUND_UP).doubleValue();
    }
}