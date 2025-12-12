package com.proiect.subtrack.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Slf4j
@Configuration
@RequiredArgsConstructor
@Profile("!test")   // 👈 NU executa în teste
public class CacheClearConfig {

    @Bean
    public CommandLineRunner clearOldCache(CacheManager cacheManager) {
        return args -> {
            try {
                // Clear the old corrupted cache
                var oldCache = cacheManager.getCache("usageRecords");
                if (oldCache != null) {
                    oldCache.clear();
                    log.info("Cleared old 'usageRecords' cache");
                }

                // Also clear the new cache to be safe
                var newCache = cacheManager.getCache("usageRecordsList");
                if (newCache != null) {
                    newCache.clear();
                    log.info("Cleared 'usageRecordsList' cache");
                }
            } catch (Exception e) {
                log.warn("Could not clear cache on startup: {}", e.getMessage());
            }
        };
    }
}

