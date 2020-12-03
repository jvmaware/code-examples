package com.jvmaware.rp;

import com.jvmaware.rp.services.RoomBookingService;
import com.jvmaware.rp.services.RoomBookingServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

    @Bean
    RoomBookingService roomBookingService(@Value("${rooms.count}") int roomsCount) {
        return new RoomBookingServiceImpl(roomsCount);
    }
}
