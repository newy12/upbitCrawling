package com.craw.crawlingprogram.crawBithumb;

import com.craw.crawlingprogram.Entity.StakingInfo;
import com.craw.crawlingprogram.Entity.CoinMarketType;
import com.craw.crawlingprogram.crawKorbit.KorbitResponseDto;
import com.craw.crawlingprogram.dto.SaveDto;
import com.craw.crawlingprogram.repository.StakingInfoRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class Bithumb {

    private final StakingInfoRepository stakingInfoRepository;

    public static String BithumbApi(int i) {
        //이더리움
        String eth = "ETH_KRW";
        //폴리곤
        String matic = "MATIC_KRW";
        //클레이튼
        String klay = "KLAY_KRW";
        //쎄타퓨엘
        String tfuel = "TFUEL_KRW";
        //퀀텀
        String qtum = "QTUM_KRW";
        //에이다
        String ada = "ADA_KRW";
        String sol = "SOL_KRW";
        String dot = "DOT_KRW";
        String waxp = "WAXP_KRW";
        String eos = "EOS_KRW";
        String cro = "CRO_KRW";
        String orbs = "ORBS_KRW";
        String icx = "ICX_KRW";
        String iost = "IOST_KRW";

        String[] markets = {eth,matic,klay,tfuel,qtum,ada,sol,dot,waxp,eos,cro,orbs,icx,iost};
        //upbit api 요청으로 전일종가 추출

        RestTemplate restTemplate = new RestTemplate();

        BithumbResponseDto response = restTemplate.getForObject("https://api.bithumb.com/public/ticker/"+markets[i], BithumbResponseDto.class);
        System.out.println("response = " + response);
        return response.getData().getClosing_price();
    }

    public void craw() throws FileNotFoundException, InterruptedException {
        SaveDto saveDto = new SaveDto();
        String url = "https://www.bithumb.com/staking/goods";

        //크롬드라이브 세팅
        //System.setProperty("webdriver.chrome.driver", String.valueOf(ResourceUtils.getFile("/app/project/chromedriver-linux64/chromedriver")));
        System.setProperty("webdriver.chrome.driver", String.valueOf(ResourceUtils.getFile("classpath:static/chromedriver")));
        ChromeOptions options = new ChromeOptions();
        //배포할때 주석풀기.
        //options.addArguments("headless","no-sandbox","disable-dev-shm-usage");
        //웹 주소 접속하여 페이지 열기
        WebDriver webDriver = new ChromeDriver(options);
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
            saveDto.setPrevClosingPrice(BithumbApi(i));
            Thread.sleep(10000);
            saveDto.setCoinName(coinName.get(i).getText());
            String[] values = extractNumbers(years.get(i).getText());
            saveDto.setMinAnnualRewardRate(values[0]);
            saveDto.setMaxAnnualRewardRate(values[1]);
            saveDto.setMinimumOrderQuantity(numbers.get(i).getText() + unit.get(i).getText());
            saveDto.setCoinMarketType(CoinMarketType.bithumb);
            stakingInfoRepository.save(new StakingInfo(saveDto));
            System.out.println("saveDto = " + saveDto);
        }

        Thread.sleep(3000);
        //웹브라우저 닫기
        webDriver.close();
    }
    private static String[] extractNumbers(String input) {
        // 숫자와 소수점을 포함하는 정규표현식
        String regex = "[0-9.]+%";

        // 정규표현식에 매칭되는 부분 추출
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 매칭된 부분 저장
        String[] extractedNumbers = new String[2];
        int index = 0;
        while (matcher.find() && index < 2) {
            extractedNumbers[index++] = matcher.group();
        }

        return extractedNumbers;
    }

}


