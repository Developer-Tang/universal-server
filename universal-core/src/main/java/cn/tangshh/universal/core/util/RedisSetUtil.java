package cn.tangshh.universal.core.util;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Redis Set Util
 *
 * @author Tang
 * @version v1.0
 */
public final class RedisSetUtil extends RedisUtil {
    private final static SetOperations<String, String> OPERATIONS;

    static {
        OPERATIONS = TEMPLATE.opsForSet();
    }

    private RedisSetUtil() {
    }

    /**
     * query set size
     *
     * @param key key
     * @return {@link Long}
     */
    public static long size(@NotNull String key) {
        Long size = OPERATIONS.size(key);
        return size == null ? 0 : size;
    }

    /**
     * Is exist value
     *
     * @param key   key
     * @param value value
     * @return {@link Boolean}
     */
    public static boolean exist(@NotNull String key, @NotNull Object value) {
        return Boolean.TRUE.equals(OPERATIONS.isMember(key, JacksonUtil.toJson(value)));
    }

    /**
     * Scan key by expression
     *
     * @param key        key
     * @param expression expression
     * @return {@link List}<{@link String}>
     */
    public static List<String> scan(@NotNull String key, @NotNull String expression) {
        List<String> result = new ArrayList<>();
        try (Cursor<String> cursor = OPERATIONS.scan(key, ScanOptions.scanOptions().match(expression).build())) {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        }
        return result;
    }

    /**
     * Add values
     *
     * @param key    key
     * @param values value
     * @return {@link Long}
     */
    @Nullable
    public static Long add(@NotNull String key, @NotNull Object... values) {
        return OPERATIONS.add(key, JacksonUtil.toJson(values));
    }

    /**
     * Remove values
     *
     * @param key    key
     * @param values value
     * @return {@link Long}
     */
    @Nullable
    public static Long remove(@NotNull String key, @NotNull Object... values) {
        return OPERATIONS.remove(key, (Object[]) JacksonUtil.toJson(values));
    }

    /**
     * Get value difference collection
     *
     * @param key      key
     * @param otherKey contrast key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> difference(@NotNull String key, @NotNull String otherKey) {
        return OPERATIONS.difference(key, otherKey);
    }

    /**
     * Get value difference collection
     *
     * @param key       key
     * @param otherKeys other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> difference(@NotNull String key, @NotNull Collection<String> otherKeys) {
        return OPERATIONS.difference(key, otherKeys);
    }

    /**
     * Get value difference collection
     *
     * @param keys keys
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> difference(@NotNull Collection<String> keys) {
        return OPERATIONS.difference(keys);
    }

    /**
     * Get value difference count
     *
     * @param key      key
     * @param otherKey other key
     * @return {@link Long}
     */
    @Nullable
    public static Long differenceAndStore(@NotNull String key, @NotNull String otherKey, @NotNull String storeKey) {
        return OPERATIONS.differenceAndStore(key, otherKey, storeKey);
    }

    /**
     * Get value difference and save to new key
     *
     * @param key       key
     * @param otherKeys other key
     * @param storeKey  new key
     * @return {@link Long}
     */
    @Nullable
    public static Long differenceAndStore(@NotNull String key, @NotNull Collection<String> otherKeys, @NotNull String storeKey) {
        return OPERATIONS.differenceAndStore(key, otherKeys, storeKey);
    }

    /**
     * Get value difference and save to new key
     *
     * @param keys     keys
     * @param storeKey new key
     * @return {@link Long}
     */
    @Nullable
    public static Long differenceAndStore(@NotNull Collection<String> keys, @NotNull String storeKey) {
        return OPERATIONS.differenceAndStore(keys, storeKey);
    }

    /**
     * Get value intersect collection
     *
     * @param key      key
     * @param otherKey other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> intersect(@NotNull String key, @NotNull String otherKey) {
        return OPERATIONS.intersect(key, otherKey);
    }

    /**
     * Get value intersect collection
     *
     * @param key       key
     * @param otherKeys other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> intersect(@NotNull String key, @NotNull Collection<String> otherKeys) {
        return OPERATIONS.intersect(key, otherKeys);
    }

    /**
     * Get value intersect collection
     *
     * @param keys keys
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> intersect(@NotNull Collection<String> keys) {
        return OPERATIONS.intersect(keys);
    }

    /**
     * Get value intersect and save to new key
     *
     * @param key      key
     * @param otherKey other key
     * @return {@link Long}
     */
    @Nullable
    public static Long intersectAndStore(@NotNull String key, @NotNull String otherKey, @NotNull String storeKey) {
        return OPERATIONS.intersectAndStore(key, otherKey, storeKey);
    }

    /**
     * Get value intersect and save to new key
     *
     * @param key       key
     * @param otherKeys other key
     * @param storeKey  new key
     * @return {@link Long}
     */
    @Nullable
    public static Long intersectAndStore(@NotNull String key, @NotNull Collection<String> otherKeys, @NotNull String storeKey) {
        return OPERATIONS.intersectAndStore(key, otherKeys, storeKey);
    }

    /**
     * Get value intersect and save to new key
     *
     * @param keys     keys
     * @param storeKey new key
     * @return {@link Long}
     */
    @Nullable
    public static Long intersectAndStore(@NotNull Collection<String> keys, @NotNull String storeKey) {
        return OPERATIONS.intersectAndStore(keys, storeKey);
    }

    /**
     * Get value union collection
     *
     * @param key      key
     * @param otherKey other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> union(@NotNull String key, @NotNull String otherKey) {
        return OPERATIONS.union(key, otherKey);
    }

    /**
     * Get value union collection
     *
     * @param key       key
     * @param otherKeys other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> union(@NotNull String key, @NotNull Collection<String> otherKeys) {
        return OPERATIONS.union(key, otherKeys);
    }

    /**
     * Get value union collection
     *
     * @param otherKeys other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> union(@NotNull Collection<String> otherKeys) {
        return OPERATIONS.union(otherKeys);
    }

    /**
     * Get value union and save to new key
     *
     * @param key      key
     * @param otherKey other key
     * @param storeKey new key
     * @return {@link Long}
     */
    @Nullable
    public static Long unionAndStore(@NotNull String key, @NotNull String otherKey, @NotNull String storeKey) {
        return OPERATIONS.unionAndStore(key, otherKey, storeKey);
    }

    /**
     * Get value union and save to new key
     *
     * @param key       key
     * @param otherKeys other key
     * @param storeKey  new key
     * @return {@link Long}
     */
    @Nullable
    public static Long unionAndStore(@NotNull String key, @NotNull Collection<String> otherKeys, @NotNull String storeKey) {
        return OPERATIONS.unionAndStore(key, otherKeys, storeKey);
    }

    /**
     * Get value union and save to new key
     *
     * @param keys     key
     * @param storeKey new key
     * @return {@link Long}
     */
    @Nullable
    public static Long unionAndStore(@NotNull Collection<String> keys, @NotNull String storeKey) {
        return OPERATIONS.unionAndStore(keys, storeKey);
    }

    /**
     * Pop a value
     *
     * @param key key
     * @return {@link String}
     */
    @Nullable
    public static String pop(@NotNull String key) {
        return OPERATIONS.pop(key);
    }

    /**
     * Pop many value
     *
     * @param key   key
     * @param count count
     * @return {@link List}<{@link String}>
     */
    @Nullable
    public static List<String> pop(@NotNull String key, long count) {
        return OPERATIONS.pop(key, count);
    }

    /**
     * Random get a value
     *
     * @param key key
     * @return {@link String}
     */
    public static String randomMember(@NotNull String key) {
        return OPERATIONS.randomMember(key);
    }

    /**
     * Random get many value
     *
     * @param key   key
     * @param count count
     * @return {@link List}<{@link String}>
     */
    @Nullable
    public static List<String> randomMembers(@NotNull String key, long count) {
        return OPERATIONS.randomMembers(key, count);
    }

    /**
     * Randomly get many different values
     *
     * @param key   key
     * @param count count
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> distinctRandomMembers(@NotNull String key, long count) {
        return OPERATIONS.distinctRandomMembers(key, count);
    }


    /**
     * Get all value
     *
     * @param key key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> members(@NotNull String key) {
        return OPERATIONS.members(key);
    }

    /**
     * Move all value to new key
     *
     * @param key   key
     * @param value value
     * @return {@link Boolean}
     */
    public static boolean move(@NotNull String key, Object value, @NotNull String newKey) {
        return Boolean.TRUE.equals(OPERATIONS.move(key, JacksonUtil.toJson(value), newKey));
    }
}
