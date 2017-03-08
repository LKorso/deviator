package com.sdr.repository;

import com.sdr.domain.Distribution;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDistributionRepository implements DistributionRository{

    private final Map<String, Distribution> distributions;

    public InMemoryDistributionRepository() {
        distributions = new HashMap<>();
    }

    @Override
    public void save(Distribution distribution) {
        distributions.put(distribution.getId().toString(), distribution);
    }

    @Override
    public Distribution getByIdentifier(String identifier) {
        return distributions.get(identifier);
    }
}
