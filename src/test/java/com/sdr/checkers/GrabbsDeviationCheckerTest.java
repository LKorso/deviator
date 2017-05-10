package com.sdr.checkers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdr.domain.Distribution;

class GrabbsDeviationCheckerTest {

    private static Distribution noOutliersDistribution;
    private static Distribution withOutliersDistribution;

    private static DeviationChecker testInstance;

    @BeforeAll
    static void setUp() throws Exception {
        final List<Double> noOutliersValues = Arrays.asList(3.1, 3.2, 3.5, 2.9);
        noOutliersDistribution = new Distribution(noOutliersValues);

        final List<Double> withOutlierValues = Arrays.asList(3.1, 2.91, 8.1, 3.2, 9.0, 3.31);
        withOutliersDistribution = new Distribution(withOutlierValues);

        testInstance = new GrabbsDeviationChecker();
    }

    @Test
    @DisplayName("Should return false cause there are no outliers in distribution")
    void isDeviationForNotOutlier() {
        final boolean actual = testInstance.isDeviation(1, noOutliersDistribution);

        assertThat(actual, is(false));
    }

    @Test
    @DisplayName("Should return true cause there is outlier")
    void isDeviationForOutlier() {
        final boolean actual = testInstance.isDeviation(2, withOutliersDistribution);

        assertThat(actual, is(true));
    }

    @Test
    @DisplayName("Should find two outliers in distribution")
    void findDeviationsForOutliers() {
        final List<Double> actual = testInstance.findDeviations(withOutliersDistribution);

        assertAll(
                () -> assertThat(actual.size(), is(2)),
                () -> assertThat(actual.get(0), is(8.1)),
                () -> assertThat(actual.get(1), is(9.0)));
    }

    @Test
    @DisplayName("Should not find any outliers in distribution")
    void findDeviationsForNoOutliers() {
        final List<Double> actual = testInstance.findDeviations(withOutliersDistribution);

        assertThat(actual.isEmpty(), is(true));
    }
}
