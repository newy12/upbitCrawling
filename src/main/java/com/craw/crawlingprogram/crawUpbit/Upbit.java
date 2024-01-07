package com.craw.crawlingprogram.crawUpbit;


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

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class Upbit {
    private final StakingInfoRepository upbitStakingInfoRepository;

    public  void craw() throws IOException, InterruptedException {
        SaveDto saveDto = new SaveDto();
        //크롤링할 주소
        String url = "https://upbit.com/staking/items";
        //크롬드라이브 세팅
        System.setProperty("webdriver.chrome.driver", String.valueOf(ResourceUtils.getFile("/app/project/chromedriver-linux64/chromedriver")));
        //System.setProperty("webdriver.chrome.driver", String.valueOf(ResourceUtils.getFile("classpath:static/chromedriver")));
        ChromeOptions options = new ChromeOptions();
        //배포할때 주석풀기.
        options.addArguments("headless","no-sandbox","disable-dev-shm-usage");
        //웹 주소 접속하여 페이지 열기
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.get(url);
        //페이지 여는데 1초 텀 두기.
        Thread.sleep(1000);

        List<WebElement> addTexts = webDriver.findElements(By.className("css-1j71w0l"));
        for (int i =0; i<addTexts.size(); i++) {
            List<WebElement> addTextList = webDriver.findElements(By.className("css-1j71w0l"));
            System.out.println("addTexts = " + addTextList);
            addTextList.get(i).click();
            Thread.sleep(3000);
            //코인이름
            WebElement coinName = webDriver.findElement(By.className("ListDetailView__condition__title__text"));
            saveDto.setCoinName(removeNonKorean(coinName.getText()));

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
            //upbitStakingInfoRepository.save(new StakingInfo(saveDto));
            System.out.println("result !!!!!!!!!!!!!" + saveDto);

            Thread.sleep(3000);

            //스테이킹 목록으로 다시들어가기
            webDriver.get(url);
            Thread.sleep(3000);
        }
        //웹브라우저 닫기
        webDriver.close();

        }
    public static String removeNonKorean(String input) {
        // 정규표현식을 사용하여 한글을 제외한 모든 문자를 제거
        String regex = "[가-힣]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 매칭된 부분을 제외한 나머지 부분을 합침
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(matcher.group());
        }

        return result.toString();
    }

}

