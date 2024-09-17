package com.example.LibraryManagementSystem.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CahceConfig {
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("books","patrons");
    }

}
