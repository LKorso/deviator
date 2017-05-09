package com.sdr.services;

import com.sdr.repository.DistributionRository;
import com.sdr.domain.Distribution;
import com.sdr.repository.FileDistributionRepository;
import lombok.AllArgsConstructor;

import java.io.File;

public class DistributionService {

    private DistributionRository distributionRository;
    private FileDistributionRepository fileRepository;

    public DistributionService(DistributionRository distributionRository) {
        this.distributionRository = distributionRository;
        fileRepository = new FileDistributionRepository();
    }

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
}
