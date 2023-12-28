package com.craw.crawlingprogram.crawBithumb;

import com.craw.crawlingprogram.domain.StakingInfo;
import com.craw.crawlingprogram.domain.CoinMarketType;
import com.craw.crawlingprogram.domain.SaveDto;
import com.craw.crawlingprogram.repository.StakingInfoRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class Bithumb {

    private final StakingInfoRepository stakingInfoRepository;

    public void craw() throws FileNotFoundException, InterruptedException {
        SaveDto saveDto = new SaveDto();
        String url = "https://www.bithumb.com/staking/goods";

        //크롬드라이브 세팅
        System.setProperty("webdriver.chrome.driver", String.valueOf(ResourceUtils.getFile("classpath:static/chromedriver")));
        //웹 주소 접속하여 페이지 열기
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(url);
        //페이지 여는데 1초 텀 두기.
        Thread.sleep(1000);


        //더보기 버튼 클릭
        WebElement buttonClick = webDriver.findElement(By.className("staking-good-lego-plus__btn-inner"));
        buttonClick.click();

        //각 요소 추출
        List<WebElement> coinName = webDriver.findElements(By.className("staking-good-lego-coin__text"));
        List<WebElement> years = webDriver.findElements(By.className("staking-good-lego-apy__number"));
        List<WebElement> numbers = webDriver.findElements(By.className("staking-good-lego-item__number"));
        List<WebElement> unit = webDriver.findElements(By.className("staking-good-lego-item__name"));


        for (int i = 0; i < coinName.size(); i++) {
            saveDto.setCoinName(coinName.get(i).getText());
            saveDto.setAnnualRewardRate(years.get(i).getText());
            saveDto.setMinimumOrderQuantity(numbers.get(i).getText() + unit.get(i).getText());
            saveDto.setCoinMarketType(CoinMarketType.bithumb);
            stakingInfoRepository.save(new StakingInfo(saveDto));
        }

        Thread.sleep(3000);
        //웹브라우저 닫기
        webDriver.close();
    }


}


