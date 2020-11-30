package com.jvmaware.rp.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/booking")
public class BookingEndpoint {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping(value = "/new/{from}/{to}")
    public long book(@PathVariable LocalDate from, @PathVariable LocalDate to, @RequestParam int count) {
        logger.info("booking request received from: [{}] to: [{}] for [{}] rooms", from, to, count);
        return -1;
    }

}
