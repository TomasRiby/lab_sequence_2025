package org.alticeLabs.exercise.tomas.ribeiro.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigInteger;
import java.util.ArrayList;

public class LabSequence {
    @Schema(description = "In-memory cache shared across calls.")
    private static ArrayList<BigInteger> cache = new ArrayList<>();

    public LabSequence() {
        resetCache();
    }

    private void resetCache() {
        cache.clear();
        cache.add(BigInteger.ZERO);
        cache.add(BigInteger.ONE);
        cache.add(BigInteger.ZERO);
        cache.add(BigInteger.ONE);
    }


    public BigInteger labseq(int number) {
        if( number < 0) {
            throw new IllegalArgumentException("Number cannot be negative");
        }
        int cacheLength = cache.size();
        if (number < cacheLength) {
            return cache.get(number);
        }

        try {
            for (int i = cacheLength; i <= number; i++) {
                BigInteger calculation = cache.get(i - 4).add(cache.get(i - 3));
                cache.add(calculation);
            }
        } catch (OutOfMemoryError oom) {
            resetCache();
            throw new OutOfMemoryError("Please select a lower number: " + number);
        }
        return cache.get(number);
    }
}
