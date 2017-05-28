package com.explod.api;

public class OfflineException extends Exception {

    public OfflineException() {
        super("Not connected to the internet");
    }

}
