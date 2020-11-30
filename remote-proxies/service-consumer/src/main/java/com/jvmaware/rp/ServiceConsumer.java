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
        ServiceConsumer serviceConsumer = new ServiceConsumer();
        serviceConsumer.triggerInvocations();
    }

    private void triggerInvocations() {
        RoomBookingService roomBookingService = ServiceProvider.roomBookingService();

        LocalDate now = LocalDate.now();
        LocalDate tommorow = now.plus(1, ChronoUnit.DAYS);
        int requestedRoomCount = 2;

        // rooms are available; book rooms
        if(roomBookingService.isAvailable(now, tommorow, requestedRoomCount)){
            logger.info("[{}] rooms are available for: [{}] and [{}]", requestedRoomCount, now, tommorow);
            roomBookingService.book(now, tommorow, requestedRoomCount);
        }

        // rooms not available
        if(roomBookingService.isAvailable(now, tommorow, requestedRoomCount)){
            logger.info("[{}] rooms are available for: [{}] and [{}]", requestedRoomCount, now, tommorow);
            roomBookingService.book(now, tommorow, requestedRoomCount);
        }
    }
}
