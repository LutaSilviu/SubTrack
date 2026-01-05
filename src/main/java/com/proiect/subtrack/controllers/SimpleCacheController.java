package com.proiect.subtrack.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cache")
@RequiredArgsConstructor
@Tag(name = "Cache", description = "View and manage cache")
public class SimpleCacheController {

    private final CacheManager cacheManager;

    @GetMapping("/stats")
    @Operation(summary = "Get cache statistics")
    public Map<String, Object> getCacheStats() {
        Map<String, Object> stats = new HashMap<>();

        for (String cacheName : cacheManager.getCacheNames()) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache instanceof ConcurrentMapCache concurrentMapCache) {
                Map<String, Object> info = new HashMap<>();
                info.put("size", concurrentMapCache.getNativeCache().size());
                info.put("keys", concurrentMapCache.getNativeCache().keySet());
                stats.put(cacheName, info);
            }
        }

        return stats;
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Clear all caches", description = "Removes all cached data from all caches")
    public Map<String, String> clearAllCaches() {
        int clearedCount = 0;
        for (String cacheName : cacheManager.getCacheNames()) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
                clearedCount++;
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "All caches cleared successfully");
        response.put("cachesCleared", String.valueOf(clearedCount));
        return response;
    }
}

