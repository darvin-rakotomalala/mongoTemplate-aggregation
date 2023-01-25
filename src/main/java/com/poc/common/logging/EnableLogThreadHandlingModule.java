package com.poc.common.logging;

import com.poc.common.logging.config.SpringThreadConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(SpringThreadConfig.class)
@Configuration
public @interface EnableLogThreadHandlingModule {
}