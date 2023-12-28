package com.craw.crawlingprogram.crawUpbit;


import com.craw.crawlingprogram.domain.CoinMarketType;
import com.craw.crawlingprogram.domain.SaveDto;
import com.craw.crawlingprogram.domain.StakingInfo;
import com.craw.crawlingprogram.repository.StakingInfoRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class Upbit {
    private final StakingInfoRepository upbitStakingInfoRepository;

    public  void craw() throws IOException, InterruptedException {
        SaveDto saveDto = new SaveDto();

        //크롤링할 주소
        //String url = "https://upbit.com/staking/detail/ETH-SETH";
        //String url = "https://upbit.com/staking/detail/ATOM-SATOM";
        //String url = "https://upbit.com/staking/detail/ADA-SADA";
        //String url = "https://upbit.com/staking/detail/SOL-SSOL";
        String url = "https://upbit.com/staking/detail/MATIC-SMATIC";
        //크롬드라이브 세팅
        System.setProperty("webdriver.chrome.driver", String.valueOf(ResourceUtils.getFile("classpath:static/chromedriver")));

        //웹 주소 접속하여 페이지 열기
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(url);
        //페이지 여는데 1초 텀 두기.
        Thread.sleep(1000);

        //코인이름
        WebElement coinName = webDriver.findElement(By.className("ListDetailView__condition__title__text"));
        System.out.println("coinTitle = " + coinName.getText());
        saveDto.setCoinName(coinName.getText());

        //연 추정 보상률, 스테이킹/언스테이킹 대기, 보상주기
        List<WebElement> values = webDriver.findElements(By.className("infoItem__value"));
        int valueIndex = 0;
        for (WebElement value : values) {
            if(valueIndex == 0){
                System.out.println("index 0= " + value.getText());
                saveDto.setAnnualRewardRate(value.getText());
            }
            if(valueIndex == 1){
                System.out.println("index 1 = " + value.getText());
                saveDto.setStakingStatus(value.getText());
            }
            if(valueIndex == 2){
                System.out.println("index 2 = " + value.getText());
                saveDto.setRewardCycle(value.getText());
            }
            valueIndex++;
        }
        //보상률 변동 추세
        WebElement rewardRateGraphParent = webDriver.findElement(By.className("css-y4qzle"));
        List<WebElement> rewardRateGraphChildren = rewardRateGraphParent.findElements(By.className("css-1s1bpji"));
        int childIndex = 0;
        for (WebElement child : rewardRateGraphChildren) {
            if(childIndex == 0){
                child.click();
                Thread.sleep(2000);
                //보상률 정보
                WebElement rewardRateInfo = webDriver.findElement(By.className("css-4yiwd3"));
                System.out.println("rewardRate = " + rewardRateInfo.getText());
                saveDto.setRewardRateForThreeMonth(rewardRateInfo.getText());
                System.out.println("childIndex 0= " + child.getText());
                WebElement button = webDriver.findElement(By.className("highcharts-tracker-line"));
                saveDto.setRewardRateTrendForThreeMonth(button.getAttribute("d"));
            }
            if(childIndex == 1){
                child.click();
                Thread.sleep(2000);
                WebElement rewardRateInfo = webDriver.findElement(By.className("css-4yiwd3"));
                System.out.println("rewardRate = " + rewardRateInfo.getText());
                saveDto.setRewardRateForSixMonth(rewardRateInfo.getText());
                System.out.println("childIndex 1= " + child.getText());
                WebElement button = webDriver.findElement(By.className("highcharts-tracker-line"));
                saveDto.setRewardRateTrendForSixMonth(button.getAttribute("d"));

            }
            if(childIndex == 2){
                child.click();
                Thread.sleep(2000);
                WebElement rewardRateInfo = webDriver.findElement(By.className("css-4yiwd3"));
                System.out.println("rewardRate = " + rewardRateInfo.getText());
                saveDto.setRewardRateForOneYear(rewardRateInfo.getText());
                System.out.println("childIndex 1= " + child.getText());
                WebElement button = webDriver.findElement(By.className("highcharts-tracker-line"));
                saveDto.setRewardRateTrendForOneYear(button.getAttribute("d"));
            }
            if(childIndex == 3){
                child.click();
                Thread.sleep(2000);
                WebElement rewardRateInfo = webDriver.findElement(By.className("css-4yiwd3"));
                System.out.println("rewardRate = " + rewardRateInfo.getText());
                saveDto.setRewardRateForThreeYear(rewardRateInfo.getText());
                System.out.println("childIndex 1= " + child.getText());
                WebElement button = webDriver.findElement(By.className("highcharts-tracker-line"));
                saveDto.setRewardRateTrendForThreeYear(button.getAttribute("d"));
            }
            childIndex++;
        }
        System.out.println("upbitSaveDto = " + saveDto);

        //최소신청수량, 검증인 수수료
        List<WebElement> values2 = webDriver.findElements(By.className("conditionInfo__data__value--Num"));
        int miniAndFeeIndex = 0;
        for (WebElement value:values2){
            if(miniAndFeeIndex == 0){
                saveDto.setMinimumOrderQuantity(value.getText());
                System.out.println("value = " + value.getText());
            }
            if(miniAndFeeIndex == 1){
                saveDto.setVerificationFee(value.getText());
                System.out.println("value = " + value.getText());
            }
            miniAndFeeIndex++;
        }
        //거래소 저장
        saveDto.setCoinMarketType(CoinMarketType.upbit);
        upbitStakingInfoRepository.save(new StakingInfo(saveDto));

        Thread.sleep(3000);
        //웹브라우저 닫기
        webDriver.close();
    }


}

