# Redis Cache Management

## Cache Configuration

### Current Settings
- **TTL (Time To Live)**: 2 minutes (120,000ms)
- **Cache Type**: Redis
- **Persistence**: Enabled (appendonly mode in Docker)

### Cache Names
- `users` - Individual user entities
- `subscriptions` - Individual subscription entities
- `userSubscriptions` - List of subscriptions per user
- `plans` - Individual plan entities
- `allPlans` - List of all plans
- `usageRecordsList` - Usage records per subscription

## Automatic Cache Management

The application automatically manages cache invalidation:

### CREATE Operations
- Creating a new **subscription** → clears `userSubscriptions` cache
- Creating a new **plan** → clears `allPlans` cache
- Creating a new **usage record** → clears `usageRecordsList` for that subscription

### UPDATE Operations
- Updating **subscription status** → clears `subscriptions` and `userSubscriptions` caches
- Updating **subscription** → updates individual cache, clears `userSubscriptions`
- Updating **plan** → updates individual cache, clears `allPlans`

### DELETE Operations
- Deleting a **user** → removes from `users` cache
- Deleting a **usage record** → clears `usageRecordsList` for that subscription

## Manual Cache Management

### Clear ALL Cache Data
```bash
docker exec -it subtrack-redis redis-cli FLUSHALL
```

### Clear Specific Cache
Use the REST API endpoint:
```bash
curl -X POST http://localhost:8080/usage/clear-cache
```

### View Cache Keys
```bash
docker exec -it subtrack-redis redis-cli KEYS "*"
```

### View Specific Cache Value
```bash
docker exec -it subtrack-redis redis-cli GET "cache_key_name"
```

## When to Clear Cache Manually

You should manually clear the cache when:
1. **Direct database modifications**: If you modify data directly in the database (e.g., via SQL client)
2. **Development/Testing**: When testing with fresh data
3. **Data migration**: After running database migrations that modify existing data
4. **Cache corruption**: If you suspect cache data is corrupted or stale

## Best Practices

1. **Development**: Clear cache frequently during development
2. **Production**: Rely on automatic cache eviction; manual clearing should be rare
3. **Testing**: Always clear cache before running integration tests
4. **Monitoring**: Watch Redis memory usage; consider reducing TTL if memory is constrained

## Troubleshooting

### Problem: Seeing old data after database changes
**Solution**: Clear Redis cache using `FLUSHALL` command above

### Problem: Cache taking too much memory
**Solution**: Reduce TTL in `RedisCacheConfig.java` and `application.properties`

### Problem: Cache not working
**Solution**: Check if Redis container is running:
```bash
docker ps | grep redis
```

### Problem: Cache persists after application restart
**Reason**: Redis uses `appendonly yes` mode, which persists data to disk
**Solution**: This is intentional for production; manually flush if needed

