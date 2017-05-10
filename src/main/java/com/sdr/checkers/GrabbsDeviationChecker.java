package com.sdr.checkers;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import com.sdr.domain.Distribution;

public class GrabbsDeviationChecker implements DeviationChecker {

    private Properties coefficients;

    public GrabbsDeviationChecker() throws Exception {
        coefficients = new Properties();
        coefficients.load(new FileInputStream("coeffisients/coef_grabbs.properties"));
    }

    @Override
    public boolean isDeviation(final int index, final Distribution distribution) {
        final double currentValue = distribution.getValues().get(index);
        final double mean = distribution.getMean();
        final double S = calculateS(distribution);
        final double G = (currentValue - mean) / S;
        final double Gr = defineGr(distribution.getValues().size());
        return G >= Gr;
    }

    private double calculateS(final Distribution distribution) {
        final List<Double> values = distribution.getValues();
        final double mean = distribution.getMean();
        double sumOfDifferentSquare = values
                .stream()
                .map(v -> Math.pow((v - mean), 2))
                .reduce(0.0, Double::sum);
        return Math.sqrt(sumOfDifferentSquare / (values.size() - 1));
    }

    private double defineGr(final int size) {
        return Double.parseDouble(coefficients.getProperty(String.valueOf(size)));
    }
}
