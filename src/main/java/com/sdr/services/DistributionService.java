package com.sdr.services;

import com.sdr.domain.Distribution;
import com.sdr.repository.DistributionRository;
import com.sdr.repository.FileDistributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

@Service
public class DistributionService {

    @Autowired
    private DistributionRository distributionRository;

    @Autowired
    private FileDistributionRepository fileRepository;

    private Properties temp;

    public Distribution createDistribution(Long observationNumber, Double mean, Double standardDeviation, boolean sorted) {
        Distribution distribution = new Distribution(observationNumber, mean, standardDeviation, sorted);
        return distribution;
    }

    public void saveDistribution(Distribution distribution) {
        distributionRository.save(distribution);
    }

    public Distribution getDistribution(String identifier) {
        return distributionRository.getByIdentifier(identifier);
    }

    public Distribution readFromFile(File file) {
        return fileRepository.readFromFile(file);
    }

    public void saveToFile(Distribution distribution, File file){
        fileRepository.save(distribution, file);
    }

    public Distribution getCurrentDistribution(){
        return distributionRository.getByCurrentId();
    }

    public void changeCurrentDistribution(Distribution distribution) {
        distributionRository.changeCurrentId(distribution.getId());
    }
}
