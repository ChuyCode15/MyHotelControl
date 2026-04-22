package com.myhotelcontrol.infra.helpers.exceptions;

public class NotFoundResorceException extends RuntimeException {
    public NotFoundResorceException(String message) {
        super(message);
    }
}
