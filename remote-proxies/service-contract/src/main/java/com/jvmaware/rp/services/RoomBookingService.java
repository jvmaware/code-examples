package com.jvmaware.rp.services;

import com.jvmaware.rp.exceptions.BookingException;

import java.time.LocalDate;

/**
 * The interface defines a contract to be implemented by the concrete
 * service provider for the ticket booking system.
 * <p>
 * The same will also be used by the client to create proxy instance
 * matching this interface to delegate requests to remote service provider.
 *
 * @author gaurs
 */
public interface RoomBookingService {

    /**
     * Check if a room is available for the given time window.
     *
     * @param from booking start time.
     * @param to   booking end time.
     * @param count number of rooms requested.
     * @return true if a room is available, false otherwise
     */
    boolean isAvailable(LocalDate from, LocalDate to, int count);

    /**
     * Book room(s) for the given dates.
     *
     * @param from booking start time.
     * @param to   booking end time.
     * @return confirmed booking id .
     * @throws BookingException if the requested rooms are not available or invalid start/end dates.
     */
    long book(LocalDate from, LocalDate to, int count) throws BookingException;

    /**
     * Cancel a booking represented by the bookingId.
     *
     * @param bookingId the bookingId representing the actual booking in the system.
     * @return true if cancelled; false otherwise
     */
    boolean cancel(long bookingId) throws BookingException;

}
