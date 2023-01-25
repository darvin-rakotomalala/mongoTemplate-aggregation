package com.poc.common.config;

import com.poc.exception.ErrorsEnum;
import com.poc.exception.FunctionalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PropertiesConditionReadWrite {
    @Value("${store.mongo}")
    private String mongo;

    @Value("${store.cassandra}")
    private String cassandra;

    private static final String WRITE = "WRITE";
    private static final String READ = "READ";

    @PostConstruct
    public void ensureApplicationActive() {
        if ((WRITE.equals(mongo) && WRITE.equals(cassandra)) ||
                (READ.equals(mongo) && READ.equals(cassandra))) {
            throw new FunctionalException(ErrorsEnum.ERR_READ_WRITE_CONFIG.getErrorMessage());
        }
    }
}
