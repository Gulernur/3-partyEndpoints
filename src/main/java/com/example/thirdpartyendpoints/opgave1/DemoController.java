package com.example.thirdpartyendpoints.opgave1;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private final int SLEEP_TIME = 1000*3;

    @GetMapping(value = "/random-string-slow")
    public String slowEndpoint() throws InterruptedException {
        Thread.sleep(SLEEP_TIME); //3 sekunder vent med at eksekvere det nedester kode.
        return RandomStringUtils.randomAlphanumeric(10);
    }

}
