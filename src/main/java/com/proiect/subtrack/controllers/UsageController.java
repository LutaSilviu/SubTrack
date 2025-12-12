package com.proiect.subtrack.controllers;

import com.proiect.subtrack.domain.dto.AddUsageRequestBody;
import com.proiect.subtrack.domain.dto.UsageRecordDto;
import com.proiect.subtrack.domain.entities.UsageRecordEntity;
import com.proiect.subtrack.mappers.impl.UsageRecordMapperImpl;
import com.proiect.subtrack.services.UsageRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/usage")
@Tag(name = "Usage", description = "Usage per subscription")
public class UsageController {
    private final UsageRecordService usageRecordService;
    private final UsageRecordMapperImpl usageRecordMapper;

    @Autowired(required = false)
    private CacheManager cacheManager;

    public UsageController(UsageRecordService usageRecordService, UsageRecordMapperImpl usageRecordMapper) {
        this.usageRecordService = usageRecordService;
        this.usageRecordMapper = usageRecordMapper;
    }

    @GetMapping("/get_usage_for_subscription/{id}")
    public ResponseEntity<@NonNull List<UsageRecordDto>> getUsageRecordForSubscription(@PathVariable Long id){
        log.debug("Fetching usage records for subscription ID: {}", id);

        List<UsageRecordEntity> usageRecordsForSubscriptionInRange = usageRecordService
                .getUsageRecordsForSubscriptionInRange(
                        id);

        List<UsageRecordDto> list = usageRecordsForSubscriptionInRange.stream().map(
                usageRecordMapper::mapTo
        ).toList();

        log.info("Retrieved {} usage records for subscription ID: {}", list.size(), id);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/add_usage")
    public ResponseEntity<@NonNull UsageRecordDto> addUsage(@RequestBody AddUsageRequestBody addUsageRequestBody){
        log.info("Adding usage record for subscription ID: {}, GB: {}",
            addUsageRequestBody.getId(), addUsageRequestBody.getUsageGb());

        UsageRecordEntity usageRecord = usageRecordService
                .addUsage(addUsageRequestBody.getId(), addUsageRequestBody.getUsageGb());

        UsageRecordDto usageRecordDto = usageRecordMapper.mapTo(usageRecord);
        log.debug("Usage record added successfully");
        return ResponseEntity.ok(usageRecordDto);
    }

    @PostMapping("/clear-cache")
    public ResponseEntity<@NonNull String> clearCache() {
        log.info("Clearing usage records cache");

        if (cacheManager == null) {
            log.warn("CacheManager is not available - cache clearing skipped");
            return ResponseEntity.ok("Cache manager not configured - no cache to clear");
        }

        try {
            var oldCache = cacheManager.getCache("usageRecords");
            if (oldCache != null) {
                oldCache.clear();
                log.info("Cleared old 'usageRecords' cache");
            }

            var newCache = cacheManager.getCache("usageRecordsList");
            if (newCache != null) {
                newCache.clear();
                log.info("Cleared 'usageRecordsList' cache");
            }

            return ResponseEntity.ok("Cache cleared successfully");
        } catch (Exception e) {
            log.error("Error clearing cache", e);
            return ResponseEntity.internalServerError().body("Error clearing cache: " + e.getMessage());
        }
    }
}
