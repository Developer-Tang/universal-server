package cn.tangshh.universal.core.util;


import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;

import java.util.*;

/**
 * Redis Hash类型 工具类
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
     * 查询集合大小
     *
     * @param key key
     * @return {@link Long}
     */
    public static Long size(String key) {
        return OPERATIONS.size(key);
    }

    /**
     * 是否存在 hashKey
     *
     * @param key     key
     * @param hashKey hashKey
     * @return boolean
     */
    public static boolean hasKey(String key, Object hashKey) {
        return Boolean.TRUE.equals(OPERATIONS.hasKey(key, JacksonUtil.toJson(hashKey)));
    }

    /**
     * 指定hashKey的值+指定值
     *
     * @param key key
     * @return {@link Double} 增量后结果
     */
    public static Map<String, String> scan(String key, String pattern) {
        Map<String, String> strMap = new HashMap<>();
        try (Cursor<Map.Entry<String, String>> cursor = OPERATIONS.scan(key, ScanOptions.scanOptions().match(pattern).build())) {
            while (cursor.hasNext()) {
                Map.Entry<String, String> next = cursor.next();
                strMap.put(next.getKey(), next.getValue());
            }
        }
        return strMap;
    }

    /**
     * 获取所有 hashKey
     *
     * @param key key
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> keys(String key) {
        return OPERATIONS.keys(key);
    }

    /**
     * 获取所有 hashKey
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> keys(String key, Class<T> tClass) {
        return JacksonUtil.parseJson(keys(key), tClass);
    }

    /**
     * 获取所有 hashKey
     *
     * @param key       key
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> keys(String key, TypeReference<T> reference) {
        return JacksonUtil.parseJson(keys(key), reference);
    }

    /**
     * 获取 hashValue
     *
     * @param key     key
     * @param hashKey hashKey
     * @return {@link String}
     */
    public static String get(String key, Object hashKey) {
        return OPERATIONS.get(key, JacksonUtil.toJson(hashKey));
    }

    /**
     * 获取
     * 获取 hashValue
     *
     * @param key     key
     * @param hashKey hashKey
     * @param tClass  目标类class
     * @return {@link T}
     */
    public static <T> T get(String key, Object hashKey, Class<T> tClass) {
        return JacksonUtil.parseJson(get(key, JacksonUtil.toJson(hashKey)), tClass);
    }

    /**
     * 获取 hashValue
     *
     * @param key       key
     * @param hashKey   hashKey
     * @param reference reference
     * @return {@link T}
     */
    public static <T> T get(String key, Object hashKey, TypeReference<T> reference) {
        return JacksonUtil.parseJson(get(key, JacksonUtil.toJson(hashKey)), reference);
    }

    /**
     * 获取多个 hashValue
     *
     * @param key      key
     * @param hashKeys hashKey
     * @return {@link List}<{@link String}>
     */
    public static List<String> multiGet(String key, Collection<Object> hashKeys) {
        return OPERATIONS.multiGet(key, JacksonUtil.toJsons(hashKeys));
    }

    /**
     * 获取多个 hashValue
     *
     * @param key      key
     * @param hashKeys hashKey
     * @param tClass   目标类class
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> multiGet(String key, Collection<Object> hashKeys, Class<T> tClass) {
        return JacksonUtil.parseJson(multiGet(key, hashKeys), tClass);
    }

    /**
     * 获取多个 hashValue
     *
     * @param key       key
     * @param hashKeys  hashKey
     * @param reference reference
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> multiGet(String key, Collection<Object> hashKeys, TypeReference<T> reference) {
        return JacksonUtil.parseJson(multiGet(key, hashKeys), reference);
    }

    /**
     * 获取全部key-value
     *
     * @param key key
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> entries(String key) {
        return OPERATIONS.entries(key);
    }

    /**
     * 获取全部hashValue
     *
     * @param key key
     * @return {@link List}<{@link String}>
     */
    public static List<String> values(String key) {
        return OPERATIONS.values(key);
    }

    /**
     * 获取全部hashValue
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> values(String key, Class<T> tClass) {
        return JacksonUtil.parseJson(values(key), tClass);
    }

    /**
     * 获取全部hashValue
     *
     * @param key       key
     * @param reference reference
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> values(String key, TypeReference<T> reference) {
        return JacksonUtil.parseJson(values(key), reference);
    }

    /**
     * 添加
     *
     * @param key       key
     * @param hashKey   hashKey
     * @param hashValue hashValue
     */
    public static void put(String key, Object hashKey, Object hashValue) {
        OPERATIONS.put(key, JacksonUtil.toJson(hashKey), JacksonUtil.toJson(hashValue));
    }

    /**
     * 批量添加
     *
     * @param key key
     * @param map key-value
     */
    public static void put(String key, Map<Object, Object> map) {
        OPERATIONS.putAll(key, JacksonUtil.toJsons(map));
    }

    /**
     * 如果不存在添加
     *
     * @param key       key
     * @param hashKey   hashKey
     * @param hashValue hashValue
     * @return boolean
     */
    public static boolean putNx(String key, Object hashKey, Object hashValue) {
        return Boolean.TRUE.equals(OPERATIONS.putIfAbsent(key, JacksonUtil.toJson(hashKey), JacksonUtil.toJson(hashValue)));
    }

    /**
     * 指定hashKey的值自增1
     *
     * @param key     key
     * @param hashKey hashKey
     * @return {@link Double} 增量后结果
     */
    public static Long increment(String key, Object hashKey) {
        return increment(key, JacksonUtil.toJson(hashKey), 1);
    }


    /**
     * 指定hashKey的值+指定值
     *
     * @param key       key
     * @param hashKey   hashKey
     * @param increment 增量步长
     * @return {@link Double} 增量后结果
     */
    public static Long increment(String key, Object hashKey, long increment) {
        return OPERATIONS.increment(key, JacksonUtil.toJson(hashKey), increment);
    }


    /**
     * 指定hashKey的值+指定值
     *
     * @param key       key
     * @param hashKey   hashKey
     * @param increment 增量步长
     * @return {@link Double} 增量后结果
     */
    public static Double increment(String key, Object hashKey, double increment) {
        return OPERATIONS.increment(key, JacksonUtil.toJson(hashKey), increment);
    }

    /**
     * 随机获取一个hashKey
     *
     * @param key key
     * @return {@link String}
     */
    public static String randomKey(String key) {
        return OPERATIONS.randomKey(key);
    }

    /**
     * 随机获取一个hashKey
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link T}
     */
    public static <T> T randomKey(String key, Class<T> tClass) {
        return JacksonUtil.parseJson(randomKey(key), tClass);
    }

    /**
     * 随机获取一个hashKey
     *
     * @param key       key
     * @param reference reference
     * @return {@link T}
     */
    public static <T> T randomKey(String key, TypeReference<T> reference) {
        return JacksonUtil.parseJson(randomKey(key), reference);
    }

    /**
     * 随机获取多个hashKey
     *
     * @param key   key
     * @param count 计数
     * @return {@link List}<{@link String}>
     */
    public static List<String> randomKeys(String key, long count) {
        return OPERATIONS.randomKeys(key, count);
    }


    /**
     * 随机获取多个hashKey
     *
     * @param key    key
     * @param tClass 目标类class
     * @param count  计数
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> randomKeys(String key, Class<T> tClass, long count) {
        return JacksonUtil.parseJson(randomKeys(key, count), tClass);
    }

    /**
     * 随机键
     * 随机获取多个hashKey
     *
     * @param key       key
     * @param reference reference
     * @param count     计数
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> randomKeys(String key, TypeReference<T> reference, long count) {
        return JacksonUtil.parseJson(randomKeys(key, count), reference);
    }

    /**
     * 随机获取一个key-value
     *
     * @param key key
     * @return {@link Map.Entry}<{@link String}, {@link String}>
     */
    public static Map.Entry<String, String> randomEntry(String key) {
        return OPERATIONS.randomEntry(key);
    }

    /**
     * 随机获取指定数量的key-value
     *
     * @param key   key
     * @param count 数量
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> randomEntries(String key, long count) {
        return OPERATIONS.randomEntries(key, count);
    }

    /**
     * 删除
     *
     * @param key      key
     * @param hashKeys hashKey
     * @return {@link Long}
     */
    public static Long delete(String key, Object... hashKeys) {
        return OPERATIONS.delete(key, (Object[]) JacksonUtil.toJson(hashKeys));
    }

}
