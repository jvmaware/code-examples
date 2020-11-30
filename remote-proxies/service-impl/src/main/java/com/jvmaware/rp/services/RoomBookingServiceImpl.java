package com.jvmaware.rp.services;

import com.jvmaware.rp.exceptions.BookingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * The class provides concrete implementation for the {@link RoomBookingService}
 * and represent a remotely hosted service triggered by a proxy instance.
 *
 * @author gaurs
 */
public class RoomBookingServiceImpl implements RoomBookingService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean isAvailable(LocalDate from, LocalDate to, int count) {
        return false;
    }

    @Override
    public long book(LocalDate from, LocalDate to, int count) throws BookingException {
        return 0;
    }

    @Override
    public boolean cancel(long bookingId) throws BookingException {
        return false;
    }
}
