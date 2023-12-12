package com.craw.crawlingprogram.crawUpbit;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CrawlingStarter {

    private final Upbit upbit;
    @PostMapping("/craw")
    public void post() throws IOException, InterruptedException {
        upbit.craw();
    }
}
