package com.sdr.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DistributionTest {

    private Distribution testInstance;
    private static final Distribution BASIC_DISTRIBUTION = new Distribution(Arrays.asList(3.0, 2.0, 4.0, 3.0));

    @Test
    @DisplayName("Should return the mean value of distribution")
    void getMean() {
        // given
        testInstance = BASIC_DISTRIBUTION;

        // when
        final double actual = testInstance.getMean();

        // then
        assertThat(actual, is(3.0));
    }

    @Test
    @DisplayName("Should return list of distribution values")
    void getValues() {
        // given
        testInstance = BASIC_DISTRIBUTION;

        // when
        final List<Double> actual = testInstance.getValues();

        // then
        assertAll(
                () -> assertThat(actual.size(), is(4)),
                () -> assertThat(actual.get(0), is(3.0)),
                () -> assertThat(actual.get(1), is(2.0)),
                () -> assertThat(actual.get(2), is(4.0)),
                () -> assertThat(actual.get(3), is(3.0))
        );
    }
}
