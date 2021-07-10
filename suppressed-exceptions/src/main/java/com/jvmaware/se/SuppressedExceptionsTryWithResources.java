package com.jvmaware.se;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SuppressedExceptionsTryWithResources {
    public void tryReadingFile(String path) throws Exception {
        try (SomeAutoCloseableClass someAutoCloseableClass = new SomeAutoCloseableClass()) {
            someAutoCloseableClass.exceptionBearingAction("some-random-path");
        }
    }
}

class SomeAutoCloseableClass implements AutoCloseable {

    public void exceptionBearingAction(String path) throws IOException {
        BufferedReader reader = reader = new BufferedReader(new FileReader(path)); // FileNotFoundException
        reader.readLine();
    }

    @Override
    public void close() throws Exception {
        throw new RuntimeException("SomeAutoCloseableClass: Exception thrown from Implicit finally block while closing");
    }
}
