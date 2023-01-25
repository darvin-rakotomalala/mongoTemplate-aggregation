package com.poc.common.logging.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadConfig {

    @Autowired
    private BeanFactory beanFactory;

    @Bean(name = "geocodeExecutor")
    @Scope("prototype")
    public ExecutorService executor() {

        ExecutorService executor = Executors.newCachedThreadPool();

        return new TraceableExecutorService(beanFactory, executor);
    }
}
