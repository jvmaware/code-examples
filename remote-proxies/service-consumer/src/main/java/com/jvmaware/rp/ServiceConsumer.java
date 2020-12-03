package com.jvmaware.rp;

import com.jvmaware.rp.provider.ServiceProvider;
import com.jvmaware.rp.services.RoomBookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * The consumer class that executes a method on the local service instance
 * which is actually a proxy mapped to a remote service.
 *
 * @author gaurs
 */
public class ServiceConsumer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        var serviceConsumer = new ServiceConsumer();
        serviceConsumer.triggerInvocations();
    }

    private void triggerInvocations() {
        var roomBookingService = ServiceProvider.roomBookingService();
        var now = LocalDate.now();
        var tomorrow = now.plus(1, ChronoUnit.DAYS);
        int requestedRoomCount = 2;

        // rooms are available; book rooms
        if(roomBookingService.isAvailable(now, tomorrow, requestedRoomCount)){
            logger.info("[{}] rooms are available for: [{}] and [{}]", requestedRoomCount, now, tomorrow);
            roomBookingService.book(now, tomorrow, requestedRoomCount);
        }

        // rooms not available
        if(roomBookingService.isAvailable(now, tomorrow, requestedRoomCount)){
            logger.info("[{}] rooms are available for: [{}] and [{}]", requestedRoomCount, now, tomorrow);
            roomBookingService.book(now, tomorrow, requestedRoomCount);
        }
    }
}
