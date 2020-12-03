package com.jvmaware.rp.services;

import com.jvmaware.rp.exceptions.BookingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * The class provides concrete implementation for the {@link RoomBookingService}
 * and represent a remotely hosted service triggered by a proxy instance.
 *
 * <strong>Assumptions</strong>
 * <ul>
 *     <li>No partial bookings are allowed - room count wise</li>
 *     <li>Bookings are done for the entire day - no hourly bookings supported</li>
 * </ul>
 *
 *
 * @author gaurs
 */
public class RoomBookingServiceImpl implements RoomBookingService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final int roomsCount;
    private int availableRooms;

    public RoomBookingServiceImpl(int roomsCount) {
        this.roomsCount = roomsCount;
        this.availableRooms = roomsCount;
    }

    @Override
    public Boolean isAvailable(LocalDate from, LocalDate to, int count) {
        return false;
    }

    @Override
    public Long book(LocalDate from, LocalDate to, int count) throws BookingException {
        return 0L;
    }

    @Override
    public Boolean cancel(long bookingId) throws BookingException {
        return false;
    }
}
