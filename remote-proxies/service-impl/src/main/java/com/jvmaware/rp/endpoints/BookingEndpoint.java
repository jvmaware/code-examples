package com.jvmaware.rp.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping(value = "/new/{count}")
    public long book(@PathVariable int count,
                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        logger.info("booking request received from: [{}] to: [{}] for [{}] rooms", from, to, count);
        return -1;
    }

    @GetMapping(value = "/check/{count}")
    public boolean checkAvailability(@PathVariable int count,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate to) {
        logger.info("checking room [{}] availability from [{}] to [{}]", count, from, to);
        return true;
    }

    @PostMapping(value = "/cancel/{bookingId}")
    public boolean book(@PathVariable int bookingId) {
        logger.info("cancel request received for id: [{}]", bookingId);
        return true;
    }

}
