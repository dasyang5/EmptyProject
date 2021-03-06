package com.demo.config.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Alex
 * @date 5/11/2021 2:57 PM
 */
@Configuration
//@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(basePackages = {"com.demo.repository"},
        repositoryBaseClass = BaseRepositoryImpl.class
)
@EnableTransactionManagement
public class PersistenceContext {
}
