package com.copper.coppertest.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration to enable the Spring Cache functionality using Caffeine as the cache of choice
 */
@Configuration
@EnableCaching
public class CachingConfiguration {
}
