package com.jvmaware.se;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A demo class to demonstrate the use and significance of <strong>SuppressedExceptions</strong>
 * in java exception handling.
 */
public class SuppressedExceptionsDemo {

    public void exceptionBearingAction(String path) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path)); // FileNotFoundException
        } finally {
            reader.close(); // NullPointerException
        }
    }

    public void suppressedExceptionBearingAction(String path) throws IOException {
        BufferedReader reader = null;
        Exception optionalException = null;
        try {
            reader = new BufferedReader(new FileReader(path)); // FileNotFoundException
        } catch (IOException ioException) {
            optionalException = ioException;
        } finally {
            try {
                reader.close(); // NullPointerException
            } catch (NullPointerException npe) {
                if (null != optionalException) {
                    npe.addSuppressed(optionalException);
                }
                throw npe;
            }
        }
    }

}
