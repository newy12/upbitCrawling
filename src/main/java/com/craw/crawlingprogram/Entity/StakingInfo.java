package com.craw.crawlingprogram.Entity;

import com.craw.crawlingprogram.common.BaseTime;
import com.craw.crawlingprogram.dto.SaveDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
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
    private String  prevClosingPrice; //전일종가
    private String minAnnualRewardRate;
    private String maxAnnualRewardRate;
    private String stakingStatus; //스테이킹/언스테이킹 대기
    private String rewardCycle; //보상주기
    private String minimumOrderQuantity; // 최소신청수량
    private String verificationFee; //검증인 수수료
    @Enumerated(EnumType.STRING)
    private CoinMarketType coinMarketType;

    public StakingInfo(SaveDto saveDto){
        this.coinName = saveDto.getCoinName();
        this.prevClosingPrice = saveDto.getPrevClosingPrice();
        this.minAnnualRewardRate = saveDto.getMinAnnualRewardRate();
        this.maxAnnualRewardRate = saveDto.getMaxAnnualRewardRate();
        this.stakingStatus = saveDto.getStakingStatus();
        this.rewardCycle = saveDto.getRewardCycle();
        this.minimumOrderQuantity = saveDto.getMinimumOrderQuantity();
        this.verificationFee = saveDto.getVerificationFee();
        this.coinMarketType = saveDto.getCoinMarketType();
    }
}
