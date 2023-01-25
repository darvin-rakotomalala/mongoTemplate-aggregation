package com.poc.common.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * This class is used to fix the bug with spring boot 2.2 which lead to have no attributes in the "models" in swagger-ui
 * when spring.main.lazy-initialization" is set to true (i.e when lazy bean initialization is activated)
 * cf https://github.com/springfox/springfox/issues/3182
 */
@Configuration
public class SpringFoxConfig implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

        BeanDefinition definition = beanFactory.getBeanDefinition("requestMappingHandlerAdapter");
        definition.setLazyInit(false);
        
    }

}