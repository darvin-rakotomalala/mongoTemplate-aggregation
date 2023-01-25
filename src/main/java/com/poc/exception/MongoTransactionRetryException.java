package com.poc.exception;

public class MongoTransactionRetryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MongoTransactionRetryException(Exception ex) {
        super(ex);
    }
}
