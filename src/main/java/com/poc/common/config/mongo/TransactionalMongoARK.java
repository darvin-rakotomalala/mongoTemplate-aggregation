package com.poc.common.config.mongo;

import com.poc.exception.MongoTransactionRetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Transactional(value = "mongoTransactionManager", propagation = Propagation.REQUIRED)
//@Retryable(value = {MongoCommandException.class, MongoException.class}, exclude = {MongoTransactionException.class, UncategorizedMongoDbException.class},
//        backoff = @Backoff(delay = 100, multiplier = 2, maxDelay = 1000, random = true), maxAttempts = 10)
@Retryable(value = {MongoTransactionRetryException.class},
        backoff = @Backoff(delay = 100, multiplier = 2, maxDelay = 1000, random = true), maxAttempts = 10)
public @interface TransactionalMongoARK {

}