package com.craw.crawlingprogram.crawKorbit;


import com.craw.crawlingprogram.Entity.CoinMarketType;
import com.craw.crawlingprogram.dto.SaveDto;
import com.craw.crawlingprogram.Entity.StakingInfo;
import com.craw.crawlingprogram.repository.StakingInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class Korbit {
    private final StakingInfoRepository stakingInfoRepository;

    public void craw() throws FileNotFoundException, InterruptedException {
        SaveDto saveDto = new SaveDto();
        String url = "https://lightning.korbit.co.kr/service/staking/list";

        //크롬드라이브 세팅
        System.setProperty("webdriver.chrome.driver", String.valueOf(ResourceUtils.getFile("/app/project/chromedriver-linux64/chromedriver")));
        //System.setProperty("webdriver.chrome.driver", String.valueOf(ResourceUtils.getFile("classpath:static/chromedriver")));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless","no-sandbox","disable-dev-shm-usage");

        //웹 주소 접속하여 페이지 열기
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.get(url);
        //페이지 여는데 1초 텀 두기.
        Thread.sleep(2000);

        List<WebElement> clicks = webDriver.findElements(By.className("gaBYEM"));
        for (int i = 0; i < clicks.size(); i++) {
            List<WebElement> clickList = webDriver.findElements(By.className("gaBYEM"));
            clickList.get(i).click();
            Thread.sleep(2000);

            List<WebElement> elements = webDriver.findElements(By.cssSelector("div.sc-1ro7n4j-0 span"));
            for (int j = 0; j < elements.size(); j++) {
                saveDto.setCoinName(elements.get(0).getText());
                saveDto.setAnnualRewardRate(elements.get(3).getText());
                saveDto.setMinimumOrderQuantity(elements.get(5).getText());
                saveDto.setStakingStatus(elements.get(7).getText());
                saveDto.setCoinMarketType(CoinMarketType.korbit);
            }
            System.out.println("saveDto = " + saveDto);
            //stakingInfoRepository.save(new StakingInfo(saveDto));

            Thread.sleep(3000);

            webDriver.get(url);
            Thread.sleep(2000);

        }
        //웹브라우저 닫기
        webDriver.close();
    }
}
