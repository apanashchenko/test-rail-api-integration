package com.test.rail.api;

import okhttp3.Response;

import java.io.IOException;

/**
 * Created by alpa on 10/25/17
 */
public class TestRailException extends IOException {

    private String message;


    public TestRailException(String message) {
        super(message);
        this.message = message;
    }

    public TestRailException(Response response)  {
        try {
            this.message = String.format(
                    "TestRailException: %s \nCode: %s \nDetails: %s", response.message(), response.code(),
                    response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
