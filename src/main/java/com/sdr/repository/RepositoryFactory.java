package com.sdr.repository;

import java.util.HashMap;

public class RepositoryFactory {

    public static final Integer IN_MEMORY = 1;
    private static HashMap<Integer, DistributionRository> instances;

    static {
        instances = new HashMap<>();
        instances.put(IN_MEMORY, new InMemoryDistributionRepository());
    }

    public static DistributionRository getReposytory(Integer repositoryType) {
        return instances.get(repositoryType);
    }
}
