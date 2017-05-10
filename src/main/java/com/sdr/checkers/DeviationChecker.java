package com.sdr.checkers;

import com.sdr.domain.Distribution;

import java.util.ArrayList;
import java.util.List;

public interface DeviationChecker {

    boolean isDeviation(int index, Distribution distribution);

    default List<Double> findDeviations(Distribution distribution){
        List<Double> deviations = new ArrayList<>();
        for (int i = 0; i < distribution.getValues().size(); i++) {
            if (isDeviation(i, distribution)) {
                deviations.add(distribution.getValues().get(i));
            }
        }
        return deviations;
    }
}
