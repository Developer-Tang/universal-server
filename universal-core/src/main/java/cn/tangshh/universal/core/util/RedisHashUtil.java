package cn.tangshh.universal.core.util;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;

import java.util.*;

/**
 * Redis Hash Util
 *
 * @author Tang
 * @version v1.0
 */
public final class RedisHashUtil extends RedisUtil {
    private final static HashOperations<String, String, String> OPERATIONS;

    static {
        OPERATIONS = TEMPLATE.opsForHash();
    }

    private RedisHashUtil() {
    }

    /**
     * query hash key size
     *
     * @param key key
     * @return {@link Long}
     */
    public static long size(@NotNull String key) {
        return OPERATIONS.size(key);
    }

    /**
     * Is exist hash key
     *
     * @param key     key
     * @param hashKey hash key
     * @return boolean
     */
    public static boolean hasKey(@NotNull String key, @NotNull Object hashKey) {
        return OPERATIONS.hasKey(key, JacksonUtil.toJson(hashKey));
    }

    /**
     * Scan key by expression
     *
     * @param key        key
     * @param expression scan expression
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> scan(@NotNull String key, @NotNull String expression) {
        Map<String, String> strMap = new HashMap<>();
        try (Cursor<Map.Entry<String, String>> cursor = OPERATIONS.scan(key, ScanOptions.scanOptions().match(expression).build())) {
            while (cursor.hasNext()) {
                Map.Entry<String, String> next = cursor.next();
                strMap.put(next.getKey(), next.getValue());
            }
        }
        return strMap;
    }

    /**
     * Get all hash key
     *
     * @param key key
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> keys(@NotNull String key) {
        return OPERATIONS.keys(key);
    }

    /**
     * Get hash value
     *
     * @param key     key
     * @param hashKey hash key
     * @return {@link String}
     */
    @Nullable
    public static String get(@NotNull String key, @NotNull Object hashKey) {
        return OPERATIONS.get(key, JacksonUtil.toJson(hashKey));
    }

    /**
     * Get many hash value
     *
     * @param key      key
     * @param hashKeys hash key
     * @return {@link List}<{@link String}>
     */
    public static List<String> multiGet(@NotNull String key, @NotNull Collection<Object> hashKeys) {
        return OPERATIONS.multiGet(key, JacksonUtil.toJsons(hashKeys));
    }

    /**
     * Get all key-value
     *
     * @param key key
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> entries(@NotNull String key) {
        return OPERATIONS.entries(key);
    }

    /**
     * Get all hash value
     *
     * @param key key
     * @return {@link List}<{@link String}>
     */
    public static List<String> values(@NotNull String key) {
        return OPERATIONS.values(key);
    }

    /**
     * Add hash value
     *
     * @param key       key
     * @param hashKey   hash key
     * @param hashValue hash value
     */
    public static void put(@NotNull String key, @NotNull Object hashKey, Object hashValue) {
        OPERATIONS.put(key, JacksonUtil.toJson(hashKey), JacksonUtil.toJson(hashValue));
    }

    /**
     * Batch add
     *
     * @param key key
     * @param map key-value
     */
    public static void put(@NotNull String key, @NotNull Map<Object, Object> map) {
        OPERATIONS.putAll(key, JacksonUtil.toJsons(map));
    }

    /**
     * If hash key not exist then add
     *
     * @param key       key
     * @param hashKey   hash key
     * @param hashValue hash value
     * @return boolean
     */
    public static boolean putNx(@NotNull String key, @NotNull Object hashKey, Object hashValue) {
        return OPERATIONS.putIfAbsent(key, JacksonUtil.toJson(hashKey), JacksonUtil.toJson(hashValue));
    }

    /**
     * hash value increment
     *
     * @param key     key
     * @param hashKey hash key
     * @return {@link Double}
     */
    public static Long increment(@NotNull String key, @NotNull Object hashKey) {
        return increment(key, JacksonUtil.toJson(hashKey), 1);
    }


    /**
     * hash value increment
     *
     * @param key       key
     * @param hashKey   hash key
     * @param increment increment
     * @return {@link Double}
     */
    public static Long increment(@NotNull String key, @NotNull Object hashKey, long increment) {
        return OPERATIONS.increment(key, JacksonUtil.toJson(hashKey), increment);
    }


    /**
     * hash value increment
     *
     * @param key       key
     * @param hashKey   hash key
     * @param increment increment
     * @return {@link Double}
     */
    public static Double increment(@NotNull String key, @NotNull Object hashKey, double increment) {
        return OPERATIONS.increment(key, JacksonUtil.toJson(hashKey), increment);
    }

    /**
     * Random get a hash key
     *
     * @param key key
     * @return {@link String}
     */
    public static String randomKey(@NotNull String key) {
        return OPERATIONS.randomKey(key);
    }


    /**
     * Random get much hash key
     *
     * @param key   key
     * @param count count
     * @return {@link List}<{@link String}>
     */
    public static List<String> randomKeys(@NotNull String key, long count) {
        return OPERATIONS.randomKeys(key, count);
    }

    /**
     * Random get a key-value
     *
     * @param key key
     * @return {@link Map.Entry}<{@link String}, {@link String}>
     */
    public static Map.Entry<String, String> randomEntry(@NotNull String key) {
        return OPERATIONS.randomEntry(key);
    }

    /**
     * Random get much key-value
     *
     * @param key   key
     * @param count count
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> randomEntries(@NotNull String key, long count) {
        return OPERATIONS.randomEntries(key, count);
    }

    /**
     * Delete
     *
     * @param key      key
     * @param hashKeys hash key
     * @return {@link Long}
     */
    public static Long delete(@NotNull String key, Object... hashKeys) {
        return OPERATIONS.delete(key, (Object[]) JacksonUtil.toJson(hashKeys));
    }

}
