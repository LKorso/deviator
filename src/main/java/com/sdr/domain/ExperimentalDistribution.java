package com.sdr.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExperimentalDistribution extends Distribution{

    private List<Distribution> subDistributions = new ArrayList<>();

    public List<Distribution> calculateSubDistributions(int valuesNumber, double... coefficients) {
        int index = 0;
        List<Double> currentValues = new ArrayList<>();
        currentValues.add(getValues().get(index));
        for (int i = 0; i < coefficients.length; i++){
            index = findIndex(getValues().get(index) + coefficients[i]);
            currentValues.add(getValues().get(index));
        }
        index++;
        for (; index < getValues().size(); index++) {
            currentValues.add(coefficients.length, getValues().get(index));
            Distribution distribution = new Distribution(currentValues);
            subDistributions.add(distribution);
        }
        return subDistributions;
    }

    public List<Double> split(final double start, final double end) {
        final int startIndex = findIndex(start);
        final int endIndex = findIndex(end);
        setValues(getValues().subList(startIndex, endIndex));
        return getValues();
    }

    private int findIndex(double value) {
        final int size = getValues().size();
        if (value > getMean()) {
            for (int i = size - 1; i >= 0; i--) {
                if (getValues().get(i) <= value) {
                    return i;
                }
            }
            return size;
        } else {
            for (int i = 0; i < size; i++) {
                if (getValues().get(i) >= value) {
                    return i;
                }
            }
            return 0;
        }
    }

}
