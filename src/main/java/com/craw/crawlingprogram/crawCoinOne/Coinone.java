package com.craw.crawlingprogram.crawCoinOne;

import com.craw.crawlingprogram.domain.SaveDto;
import com.craw.crawlingprogram.repository.StakingInfoRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@RequiredArgsConstructor
@Service
public class Coinone {

    private final StakingInfoRepository stakingInfoRepository;

    public void craw() throws FileNotFoundException, InterruptedException {
        SaveDto saveDto = new SaveDto();
        String url = "https://coinone.co.kr/plus";

        //크롬드라이브 세팅
        System.setProperty("webdriver.chrome.driver", String.valueOf(ResourceUtils.getFile("classpath:static/chromedriver")));
        //웹 주소 접속하여 페이지 열기
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(url);
        //페이지 여는데 1초 텀 두기.
        Thread.sleep(1000);





    }
}
