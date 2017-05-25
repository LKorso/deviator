package com.sdr.repository;

import com.sdr.domain.Distribution;

public interface DistributionRository {

    void save(Distribution distribution);

    Distribution getByIdentifier(String identifier);

    void changeCurrentId(Long newId);

    Distribution getByCurrentId();
}
