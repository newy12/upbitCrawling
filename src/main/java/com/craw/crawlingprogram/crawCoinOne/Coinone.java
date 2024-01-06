package com.craw.crawlingprogram.crawCoinOne;

import com.craw.crawlingprogram.Entity.CoinMarketType;
import com.craw.crawlingprogram.dto.SaveDto;
import com.craw.crawlingprogram.Entity.StakingInfo;
import com.craw.crawlingprogram.repository.StakingInfoRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class Coinone {

    private final StakingInfoRepository stakingInfoRepository;

    public void craw() throws FileNotFoundException, InterruptedException {
        SaveDto saveDto = new SaveDto();
        String url = "https://coinone.co.kr/plus";

        //크롬드라이브 세팅
        System.setProperty("webdriver.chrome.driver", String.valueOf(ResourceUtils.getFile("/app/project/chromedriver-linux64/chromedriver")));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        //웹 주소 접속하여 페이지 열기
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.get(url);
        //페이지 여는데 1초 텀 두기.
        Thread.sleep(1000);


        //각 요소 추출
        List<WebElement> coinNames = webDriver.findElements(By.className("ProductsBrowseList_coin-name__bSWTj"));
        List<WebElement> years = webDriver.findElements(By.className("ProductsBrowseList_column-reward__mKBuy"));

        for (WebElement cointName: coinNames){
            System.out.println("cointName = " + cointName.getText());
        }
        for (WebElement year : years){
            System.out.println("year.getText() = " + year.getText());
        }
        for (int i = 0; i < coinNames.size(); i++) {
            saveDto.setCoinName(coinNames.get(i).getText());
            saveDto.setAnnualRewardRate(years.get(i).getText());
            saveDto.setCoinMarketType(CoinMarketType.coinone);
            stakingInfoRepository.save(new StakingInfo(saveDto));
        }
        Thread.sleep(3000);
        //웹브라우저 닫기
        webDriver.close();



    }
}
