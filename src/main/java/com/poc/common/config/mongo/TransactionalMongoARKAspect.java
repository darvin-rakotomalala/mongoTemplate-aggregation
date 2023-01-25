package com.poc.common.config.mongo;

import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;
import com.poc.exception.MongoTransactionRetryException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionalMongoARKAspect {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(TransactionalMongoARK)")
    public Object transactionalMongoARK(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (MongoTransactionException | UncategorizedMongoDbException ex) {
            MongoException mongoException = null;
            if (ex.getCause() instanceof MongoException) {
                mongoException = (MongoException) ex.getCause();
            } else if (ex.getCause() instanceof MongoCommandException) {
                mongoException = (MongoCommandException) ex.getCause();
            }
            if (mongoException != null && mongoException.hasErrorLabel(MongoException.TRANSIENT_TRANSACTION_ERROR_LABEL)) {
                LOGGER.debug("TransientTransactionError aborting transaction and retrying ...");
            } else if (mongoException != null && mongoException.hasErrorLabel(MongoException.UNKNOWN_TRANSACTION_COMMIT_RESULT_LABEL)) {
                LOGGER.debug("UnknownTransactionCommitResult, retrying commit operation ...");
            }


            if (ex.getMessage().contains("112") || ex.getMessage().contains("251")) {
                throw new MongoTransactionRetryException(ex);
            }
            throw ex;
        }
    }

}