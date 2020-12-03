package com.jvmaware.rp.services;

import com.jvmaware.rp.exceptions.BookingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Random;

/**
 * The class provides a <strong>DEMO</strong> implementation for the {@link RoomBookingService}
 * and represent a remotely hosted service triggered by a proxy instance.
 *
 * @author gaurs
 */
public class RoomBookingServiceImpl implements RoomBookingService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Random randomNumberGenerator = new Random();

    private final int roomsCount;
    private int availableRooms;

    public RoomBookingServiceImpl(int roomsCount) {
        this.roomsCount = roomsCount;
        this.availableRooms = roomsCount;
    }

    @Override
    public Boolean isAvailable(LocalDate from, LocalDate to, int count) {
        // some implementation to check the room availability here
        // sending a default value for DEMO
        logger.info("room availability checked completed");
        return true;
    }

    @Override
    public Long book(LocalDate from, LocalDate to, int count) throws BookingException {
        // some implementation to book the rooms
        // sending a default booking id for DEMO
        logger.info("booking completed successfully");
        return randomNumberGenerator.nextLong();
    }

    @Override
    public Boolean cancel(long bookingId) throws BookingException {
        // some implementation to cancel the room booking
        // sending a default value for DEMO
        logger.info("booking cancelled successfully");
        return true;
    }
}
