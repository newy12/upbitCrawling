package com.craw.crawlingprogram;

import com.craw.crawlingprogram.crawUpbit.Upbit;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class CrawlingProgramApplication {

    public static void main(String[] args){
        SpringApplication.run(CrawlingProgramApplication.class, args);
    }

}