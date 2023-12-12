package com.craw.crawlingprogram.crawUpbit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UpbitStakingInfoRepository extends JpaRepository<UpbitStakingInfo, UUID> {
}
