package com.craw.crawlingprogram.repository;

import com.craw.crawlingprogram.domain.StakingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StakingInfoRepository extends JpaRepository<StakingInfo, UUID> {
}
