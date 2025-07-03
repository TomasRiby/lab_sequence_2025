package org.alticeLabs.exercise.tomas.ribeiro.service;


import jakarta.enterprise.context.ApplicationScoped;
import org.alticeLabs.exercise.tomas.ribeiro.model.LabSequence;

import java.math.BigInteger;


@ApplicationScoped
public class LabSequenceService {
    private LabSequence labSequence;

    public LabSequenceService() {
        this.labSequence = new LabSequence();
    }

    public BigInteger calculateLabSeq(int number) {
        return labSequence.labseq(number);
    }
}
