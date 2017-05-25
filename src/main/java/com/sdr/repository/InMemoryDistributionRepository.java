package com.sdr.repository;

import com.sdr.domain.Distribution;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryDistributionRepository implements DistributionRository{

    private final Map<Long, Distribution> distributions;
    private Long currentDistributionId;

    public InMemoryDistributionRepository() {
        distributions = new HashMap<>();
    }

    @Override
    public void save(Distribution distribution) {
        distributions.put(distribution.getId(), distribution);
    }

    @Override
    public Distribution getByIdentifier(String identifier) {
        return distributions.get(identifier);
    }

    public void changeCurrentId(Long newId) {
        currentDistributionId = newId;
    }

    public Distribution getByCurrentId() {
        return distributions.get(currentDistributionId);
    }
}
