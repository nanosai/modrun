package com.jenkov.modrun;

/**
 * Created by jjenkov on 26-10-2016.
 */
public class ModRunException extends RuntimeException {

    public ModRunException() {
    }

    public ModRunException(String message) {
        super(message);
    }

    public ModRunException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModRunException(Throwable cause) {
        super(cause);
    }

    public ModRunException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
