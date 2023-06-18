package com.dfernandezaller.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeDistributionTest {

    @Test
    public void testValuesAreRounded() {
        TimeDistribution timeDistribution = new TimeDistribution(1.2345, 2.3456, 3.4567);

        assertEquals(1.23, timeDistribution.busy());
        assertEquals(2.35, timeDistribution.tentative());
        assertEquals(3.46, timeDistribution.ooo());
    }

    @Test
    public void testValuesAreStored() {
        TimeDistribution timeDistribution = new TimeDistribution(1.0, 2.0, 3.0);

        assertEquals(1.0, timeDistribution.busy());
        assertEquals(2.0, timeDistribution.tentative());
        assertEquals(3.0, timeDistribution.ooo());
    }
}
