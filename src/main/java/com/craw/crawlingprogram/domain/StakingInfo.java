package com.craw.crawlingprogram.domain;

import com.craw.crawlingprogram.common.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class StakingInfo extends BaseTime implements Serializable {
    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID stakingId;
    private String coinName; //코인이름
    private String annualRewardRate; //연 추정 보상률
    private String stakingStatus; //스테이킹/언스테이킹 대기
    private String rewardCycle; //보상주기
    private String rewardRateForThreeMonth; //보상률 (3개월)
    private String rewardRateForSixMonth; //보상률 (6개월)
    private String rewardRateForOneYear; //보상률 (1년)
    private String rewardRateForThreeYear; //보상률 (3년)

    @Column(length = 50000)
    private String rewardRateTrendForThreeMonth; //보상률 변동 추세 (3개월)
    @Column(length = 50000)
    private String rewardRateTrendForSixMonth; //보상률 변동 추세 (6개월)
    @Column(length = 50000)
    private String rewardRateTrendForOneYear; //보상률 변동 추세 (1년)
    @Column(length = 50000)
    private String rewardRateTrendForThreeYear; //보상률 변동 추세 (3년)

    private String minimumOrderQuantity; // 최소신청수량
    private String verificationFee; //검증인 수수료
    @Enumerated(EnumType.STRING)
    private CoinMarketType coinMarketType;

    public StakingInfo(SaveDto saveDto){
        this.coinName = saveDto.getCoinName();
        this.annualRewardRate = saveDto.getAnnualRewardRate();
        this.stakingStatus = saveDto.getStakingStatus();
        this.rewardCycle = saveDto.getRewardCycle();
        this.rewardRateForThreeMonth = saveDto.getRewardRateForThreeMonth();
        this.rewardRateForSixMonth = saveDto.getRewardRateForThreeMonth();
        this.rewardRateForOneYear = saveDto.getRewardRateForOneYear();
        this.rewardRateForThreeYear = saveDto.getRewardRateForThreeYear();
        this.rewardRateTrendForThreeMonth = saveDto.getRewardRateTrendForThreeMonth();
        this.rewardRateTrendForSixMonth = saveDto.getRewardRateTrendForSixMonth();
        this.rewardRateTrendForOneYear = saveDto.getRewardRateTrendForOneYear();
        this.rewardRateTrendForThreeYear = saveDto.getRewardRateTrendForThreeYear();
        this.minimumOrderQuantity = saveDto.getMinimumOrderQuantity();
        this.verificationFee = saveDto.getVerificationFee();
        this.coinMarketType = saveDto.getCoinMarketType();
    }
}
