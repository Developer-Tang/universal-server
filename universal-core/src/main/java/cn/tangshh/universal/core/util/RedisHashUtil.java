package cn.tangshh.universal.core.util;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;

import java.util.*;

/**
 * <p>Redis Hash Util</p>
 * <p>Redis Hash类型工具</p>
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
     * <p>Query hash key size</p>
     * <p>查询Hash键数量</p>
     *
     * @param key key
     * @return {@link Long}
     */
    public static long size(@NotNull String key) {
        return OPERATIONS.size(key);
    }

    /**
     * <p>Is exist hash key</p>
     * <p>是否存在Hash键</p>
     *
     * @param key     key
     * @param hashKey hash key
     * @return boolean
     */
    public static boolean hasKey(@NotNull String key, @NotNull Object hashKey) {
        return OPERATIONS.hasKey(key, JacksonUtil.toJson(hashKey));
    }

    /**
     * <p>Scan key by expression (* match anything)</p>
     * <p>通过表达式查询键（*匹配任意内容）</p>
     * <p>example: user:*,2023*,*09</p>
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
     * <p>Get all hash key</p>
     * <p>获取全部Hash键</p>
     *
     * @param key key
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> keys(@NotNull String key) {
        return OPERATIONS.keys(key);
    }

    /**
     * <p>Get hash value</p>
     * <p>获取Hash值</p>
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
     * <p>Get multiple hash value</p>
     * <p>获取获取多个Hash值</p>
     *
     * @param key      key
     * @param hashKeys hash key
     * @return {@link List}<{@link String}>
     */
    public static List<String> multiGet(@NotNull String key, @NotNull Collection<Object> hashKeys) {
        return OPERATIONS.multiGet(key, JacksonUtil.toJsons(hashKeys));
    }

    /**
     * <p>Get all key-value</p>
     * <p>获取全部键值对</p>
     *
     * @param key key
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> entries(@NotNull String key) {
        return OPERATIONS.entries(key);
    }

    /**
     * <p>Get all hash value</p>
     * <p>获取全部Hash值</p>
     *
     * @param key key
     * @return {@link List}<{@link String}>
     */
    public static List<String> values(@NotNull String key) {
        return OPERATIONS.values(key);
    }

    /**
     * <p>Add hash value</p>
     * <p>添加Hash值</p>
     *
     * @param key       key
     * @param hashKey   hash key
     * @param hashValue hash value
     */
    public static void put(@NotNull String key, @NotNull Object hashKey, Object hashValue) {
        OPERATIONS.put(key, JacksonUtil.toJson(hashKey), JacksonUtil.toJson(hashValue));
    }

    /**
     * <p>Batch add key-value</p>
     * <p>批量添加键值对</p>
     *
     * @param key key
     * @param map key-value
     */
    public static void put(@NotNull String key, @NotNull Map<Object, Object> map) {
        OPERATIONS.putAll(key, JacksonUtil.toJsons(map));
    }

    /**
     * <p>If hash key not exist then add</p>
     * <p>如果Hash值不存在则添加</p>
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
     * <p>Hash value increment</p>
     * <p>Hash值自增</p>
     *
     * @param key     key
     * @param hashKey hash key
     * @return {@link Double}
     */
    public static Long increment(@NotNull String key, @NotNull Object hashKey) {
        return increment(key, JacksonUtil.toJson(hashKey), 1);
    }


    /**
     * <p>Hash value increment</p>
     * <p>Hash值自增</p>
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
     * <p>Hash value increment</p>
     * <p>Hash值自增</p>
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
     * <p>Random get a hash key</p>
     * <p>随机获取一个Hash值</p>
     *
     * @param key key
     * @return {@link String}
     */
    public static String randomKey(@NotNull String key) {
        return OPERATIONS.randomKey(key);
    }


    /**
     * <p>Random get multiple hash key</p>
     * <p>随机获取多个Hash键</p>
     *
     * @param key   key
     * @param count count
     * @return {@link List}<{@link String}>
     */
    public static List<String> randomKeys(@NotNull String key, long count) {
        return OPERATIONS.randomKeys(key, count);
    }

    /**
     * <p>Random get a key-value</p>
     * <p>随机获取一个键值对</p>
     *
     * @param key key
     * @return {@link Map.Entry}<{@link String}, {@link String}>
     */
    public static Map.Entry<String, String> randomEntry(@NotNull String key) {
        return OPERATIONS.randomEntry(key);
    }

    /**
     * <p>Random get multiple key-value</p>
     * <p>随机获取多个键值对</p>
     *
     * @param key   key
     * @param count count
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> randomEntries(@NotNull String key, long count) {
        return OPERATIONS.randomEntries(key, count);
    }

    /**
     * <p>Delete multiple hash value</p>
     * <p>删除多个Hash值</p>
     *
     * @param key      key
     * @param hashKeys hash key
     * @return {@link Long}
     */
    public static Long delete(@NotNull String key, Object... hashKeys) {
        return OPERATIONS.delete(key, (Object[]) JacksonUtil.toJson(hashKeys));
    }

}
