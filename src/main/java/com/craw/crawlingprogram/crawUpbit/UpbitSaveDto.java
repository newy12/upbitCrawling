package com.craw.crawlingprogram.crawUpbit;


import lombok.Data;

@Data
public class UpbitSaveDto {
    private String coinName; //코인이름
    private String annualRewardRate; //연 추정 보상률
    private String stakingStatus; //스테이킹/언스테이킹 대기
    private String rewardCycle; //보상주기
    private String rewardRateForThreeMonth; //보상률 (3개월)
    private String rewardRateForSixMonth; //보상률 (6개월)
    private String rewardRateForOneYear; //보상률 (1년)
    private String rewardRateForThreeYear; //보상률 (3년)
    private String rewardRateTrendForThreeMonth; //보상률 변동 추세 (3개월)
    private String rewardRateTrendForSixMonth; //보상률 변동 추세 (6개월)
    private String rewardRateTrendForOneYear; //보상률 변동 추세 (1년)
    private String rewardRateTrendForThreeYear; //보상률 변동 추세 (3년)

}
