package cn.tangshh.universal.core.util;


import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Redis Se目标类class型 工具类
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
     * 查询集合大小
     *
     * @param key key
     * @return {@link Long}
     */
    public static Long size(String key) {
        return OPERATIONS.size(key);
    }

    /**
     * 是否存在值
     *
     * @param key   key
     * @param value 值
     * @return {@link Boolean}
     */
    public static Boolean isMember(String key, Object value) {
        return OPERATIONS.isMember(key, JacksonUtil.toJson(value));
    }

    /**
     * 查询集合中符合要求的的值
     *
     * @param key     key
     * @param pattern 表达式
     * @return {@link List}<{@link String}>
     */
    public static List<String> scan(String key, String pattern) {
        List<String> result = new ArrayList<>();
        try (Cursor<String> cursor = OPERATIONS.scan(key, ScanOptions.scanOptions().match(pattern).build())) {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        }
        return result;
    }

    /**
     * 查询集合中符合要求的的值
     *
     * @param key     key
     * @param pattern 表达式
     * @param tClass  目标类class
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> scan(String key, String pattern, Class<T> tClass) {
        return JacksonUtil.parseJson(scan(key, pattern), tClass);
    }

    /**
     * 查询集合中符合要求的的值
     *
     * @param key       key
     * @param pattern   表达式
     * @param reference reference
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> scan(String key, String pattern, TypeReference<T> reference) {
        return JacksonUtil.parseJson(scan(key, pattern), reference);
    }

    /**
     * 添加
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public static Long add(String key, Object... values) {
        return OPERATIONS.add(key, JacksonUtil.toJson(values));
    }

    /**
     * 删除
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public static Long remove(String key, Object... values) {
        return OPERATIONS.remove(key, (Object[]) JacksonUtil.toJson(values));
    }

    /**
     * 取差集
     *
     * @param key      key
     * @param otherKey 其他key
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> difference(String key, String otherKey) {
        return OPERATIONS.difference(key, otherKey);
    }

    /**
     * 取差集
     *
     * @param key      key
     * @param otherKey 其他key
     * @param tClass   目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> difference(String key, String otherKey, Class<T> tClass) {
        return JacksonUtil.parseJson(difference(key, otherKey), tClass);
    }

    /**
     * 取差集
     *
     * @param key       key
     * @param otherKey  其他key
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> difference(String key, String otherKey, TypeReference<T> reference) {
        return JacksonUtil.parseJson(difference(key, otherKey), reference);
    }

    /**
     * 取差集
     *
     * @param key       key
     * @param otherKeys 其他key
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> difference(String key, Collection<String> otherKeys) {
        return OPERATIONS.difference(key, otherKeys);
    }

    /**
     * 取差集
     *
     * @param key      key
     * @param otherKey 其他key
     * @param tClass   目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> difference(String key, Collection<String> otherKey, Class<T> tClass) {
        return JacksonUtil.parseJson(difference(key, otherKey), tClass);
    }

    /**
     * 取差集
     *
     * @param key       key
     * @param otherKey  其他key
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> difference(String key, Collection<String> otherKey, TypeReference<T> reference) {
        return JacksonUtil.parseJson(difference(key, otherKey), reference);
    }

    /**
     * 取差集
     *
     * @param keys keys
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> difference(Collection<String> keys) {
        return OPERATIONS.difference(keys);
    }

    /**
     * 取差集
     *
     * @param keys   keys
     * @param tClass 目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> difference(Collection<String> keys, Class<T> tClass) {
        return JacksonUtil.parseJson(difference(keys), tClass);
    }

    /**
     * 取差集
     *
     * @param keys      keys
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> difference(Collection<String> keys, TypeReference<T> reference) {
        return JacksonUtil.parseJson(difference(keys), reference);
    }

    /**
     * 取差集并存储到新key
     *
     * @param key      key
     * @param otherKey 其他key
     * @return {@link Long}
     */
    public static Long differenceAndStore(String key, String otherKey, String storeKey) {
        return OPERATIONS.differenceAndStore(key, otherKey, storeKey);
    }

    /**
     * 取差集并存储到新key
     *
     * @param key       key
     * @param otherKeys 其他key
     * @param storeKey  存储key
     * @return {@link Long}
     */
    public static Long differenceAndStore(String key, Collection<String> otherKeys, String storeKey) {
        return OPERATIONS.differenceAndStore(key, otherKeys, storeKey);
    }

    /**
     * 取差集并存储到新key
     *
     * @param keys     keys
     * @param storeKey 存储key
     * @return {@link Long}
     */
    public static Long differenceAndStore(Collection<String> keys, String storeKey) {
        return OPERATIONS.differenceAndStore(keys, storeKey);
    }

    /**
     * 取交集
     *
     * @param key      key
     * @param otherKey 其他key
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> intersect(String key, String otherKey) {
        return OPERATIONS.intersect(key, otherKey);
    }

    /**
     * 取交集
     *
     * @param key      key
     * @param otherKey 其他key
     * @param tClass   目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> intersect(String key, String otherKey, Class<T> tClass) {
        return JacksonUtil.parseJson(intersect(key, otherKey), tClass);
    }

    /**
     * 取交集
     *
     * @param key       key
     * @param otherKey  其他key
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> intersect(String key, String otherKey, TypeReference<T> reference) {
        return JacksonUtil.parseJson(OPERATIONS.intersect(key, otherKey), reference);
    }

    /**
     * 取交集
     *
     * @param key       key
     * @param otherKeys 其他key
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> intersect(String key, Collection<String> otherKeys) {
        return OPERATIONS.intersect(key, otherKeys);
    }

    /**
     * 取交集
     *
     * @param key      key
     * @param otherKey 其他key
     * @param tClass   目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> intersect(String key, Collection<String> otherKey, Class<T> tClass) {
        return JacksonUtil.parseJson(intersect(key, otherKey), tClass);
    }

    /**
     * 取交集
     *
     * @param key       key
     * @param otherKey  其他key
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> intersect(String key, Collection<String> otherKey, TypeReference<T> reference) {
        return JacksonUtil.parseJson(intersect(key, otherKey), reference);
    }

    /**
     * 取交集
     *
     * @param keys keys
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> intersect(Collection<String> keys) {
        return OPERATIONS.intersect(keys);
    }

    /**
     * 取交集
     *
     * @param keys   keys
     * @param tClass 目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> intersect(Collection<String> keys, Class<T> tClass) {
        return JacksonUtil.parseJson(intersect(keys), tClass);
    }

    /**
     * 取交集
     *
     * @param keys      keys
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> intersect(Collection<String> keys, TypeReference<T> reference) {
        return JacksonUtil.parseJson(intersect(keys), reference);
    }

    /**
     * 取交集并存储到新key
     *
     * @param key      key
     * @param otherKey 其他key
     * @return {@link Long}
     */
    public static Long intersectAndStore(String key, String otherKey, String storeKey) {
        return OPERATIONS.intersectAndStore(key, otherKey, storeKey);
    }

    /**
     * 取交集并存储到新key
     *
     * @param key       key
     * @param otherKeys 其他key
     * @param storeKey  存储key
     * @return {@link Long}
     */
    public static Long intersectAndStore(String key, Collection<String> otherKeys, String storeKey) {
        return OPERATIONS.intersectAndStore(key, otherKeys, storeKey);
    }

    /**
     * 取差集并存储到新key
     *
     * @param keys     keys
     * @param storeKey 存储key
     * @return {@link Long}
     */
    public static Long intersectAndStore(Collection<String> keys, String storeKey) {
        return OPERATIONS.intersectAndStore(keys, storeKey);
    }

    /**
     * 取并级
     *
     * @param key      key
     * @param otherKey 其他key
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> union(String key, String otherKey) {
        return OPERATIONS.union(key, otherKey);
    }

    /**
     * 取并级
     *
     * @param key      key
     * @param otherKey 其他key
     * @param tClass   目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> union(String key, String otherKey, Class<T> tClass) {
        return JacksonUtil.parseJson(union(key, otherKey), tClass);
    }

    /**
     * 取并级
     *
     * @param key       key
     * @param otherKey  其他key
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> union(String key, String otherKey, TypeReference<T> reference) {
        return JacksonUtil.parseJson(union(key, otherKey), reference);
    }

    /**
     * 取并级
     *
     * @param key       key
     * @param otherKeys 其他key
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> union(String key, Collection<String> otherKeys) {
        return OPERATIONS.union(key, otherKeys);
    }

    /**
     * 取并级
     *
     * @param key       key
     * @param otherKeys 其他key
     * @param tClass    目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> union(String key, Collection<String> otherKeys, Class<T> tClass) {
        return JacksonUtil.parseJson(union(key, otherKeys), tClass);
    }

    /**
     * 取并级
     *
     * @param key       key
     * @param otherKeys 其他key
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> union(String key, Collection<String> otherKeys, TypeReference<T> reference) {
        return JacksonUtil.parseJson(union(key, otherKeys), reference);
    }

    /**
     * 取并级
     *
     * @param otherKeys 其他key
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> union(Collection<String> otherKeys) {
        return OPERATIONS.union(otherKeys);
    }

    /**
     * 取并级
     *
     * @param otherKeys 其他key
     * @param tClass    目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> union(Collection<String> otherKeys, Class<T> tClass) {
        return JacksonUtil.parseJson(union(otherKeys), tClass);
    }

    /**
     * 取并级
     *
     * @param otherKeys 其他key
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> union(Collection<String> otherKeys, TypeReference<T> reference) {
        return JacksonUtil.parseJson(union(otherKeys), reference);
    }

    /**
     * 取并级并存储到新key
     *
     * @param key      key
     * @param otherKey 其他key
     * @param storeKey 存储key
     * @return {@link Long}
     */
    public static Long unionAndStore(String key, String otherKey, String storeKey) {
        return OPERATIONS.unionAndStore(key, otherKey, storeKey);
    }

    /**
     * 取并级并存储到新key
     *
     * @param key       key
     * @param otherKeys 其他key
     * @param storeKey  存储key
     * @return {@link Long}
     */
    public static Long unionAndStore(String key, Collection<String> otherKeys, String storeKey) {
        return OPERATIONS.unionAndStore(key, otherKeys, storeKey);
    }

    /**
     * 取并级并存储到新key
     *
     * @param keys     keys
     * @param storeKey 存储key
     * @return {@link Long}
     */
    public static Long unionAndStore(Collection<String> keys, String storeKey) {
        return OPERATIONS.unionAndStore(keys, storeKey);
    }

    /**
     * 弹出一个值
     *
     * @param key key
     * @return {@link String}
     */
    public static String pop(String key) {
        return OPERATIONS.pop(key);
    }

    /**
     * 弹出一个值
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link T}
     */
    public static <T> T pop(String key, Class<T> tClass) {
        return JacksonUtil.parseJson(pop(key), tClass);
    }

    /**
     * 弹出一个值
     *
     * @param key       key
     * @param reference reference
     * @return {@link T}
     */
    public static <T> T pop(String key, TypeReference<T> reference) {
        return JacksonUtil.parseJson(pop(key), reference);
    }

    /**
     * 弹出多个值
     *
     * @param key   key
     * @param count 计数
     * @return {@link List}<{@link String}>
     */
    public static List<String> pop(String key, long count) {
        return OPERATIONS.pop(key, count);
    }

    /**
     * 弹出多个值
     *
     * @param key    key
     * @param count  计数
     * @param tClass 目标类class
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> pop(String key, long count, Class<T> tClass) {
        return JacksonUtil.parseJson(pop(key, count), tClass);
    }

    /**
     * 弹出多个值
     *
     * @param key       key
     * @param count     计数
     * @param reference reference
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> pop(String key, long count, TypeReference<T> reference) {
        return JacksonUtil.parseJson(pop(key, count), reference);
    }

    /**
     * 随机获取一个值
     *
     * @param key key
     * @return {@link String}
     */
    public static String randomMember(String key) {
        return OPERATIONS.randomMember(key);
    }

    /**
     * 随机获取一个值
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link T}
     */
    public static <T> T randomMember(String key, Class<T> tClass) {
        return JacksonUtil.parseJson(randomMember(key), tClass);
    }


    /**
     * 随机获取一个值
     *
     * @param key       key
     * @param reference reference
     * @return {@link T}
     */
    public static <T> T randomMember(String key, TypeReference<T> reference) {
        return JacksonUtil.parseJson(randomMember(key), reference);
    }

    /**
     * 随机获取多个值
     *
     * @param key   key
     * @param count 计数
     * @return {@link List}<{@link String}>
     */
    public static List<String> randomMembers(String key, long count) {
        return OPERATIONS.randomMembers(key, count);
    }

    /**
     * 随机获取多个值
     *
     * @param key    key
     * @param tClass 目标类class
     * @param count  计数
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> randomMembers(String key, long count, Class<T> tClass) {
        return JacksonUtil.parseJson(randomMembers(key, count), tClass);
    }


    /**
     * 随机获取多个值
     *
     * @param key       key
     * @param reference reference
     * @param count     计数
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> randomMembers(String key, long count, TypeReference<T> reference) {
        return JacksonUtil.parseJson(randomMembers(key, count), reference);
    }

    /**
     * 随机获取多个不同的值
     *
     * @param key   key
     * @param count 计数
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> distinctRandomMembers(String key, long count) {
        return OPERATIONS.distinctRandomMembers(key, count);
    }

    /**
     * 随机获取多个不同的值
     *
     * @param key    key
     * @param count  计数
     * @param tClass 目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> distinctRandomMembers(String key, long count, Class<T> tClass) {
        return JacksonUtil.parseJson(OPERATIONS.distinctRandomMembers(key, count), tClass);
    }

    /**
     * 随机获取多个不同的值
     *
     * @param key       key
     * @param count     计数
     * @param reference reference
     * @return {@link Set}<{@link String}>
     */
    public static <T> Set<T> distinctRandomMembers(String key, long count, TypeReference<T> reference) {
        return JacksonUtil.parseJson(OPERATIONS.distinctRandomMembers(key, count), reference);
    }

    /**
     * 获取所有值
     *
     * @param key key
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> members(String key) {
        return OPERATIONS.members(key);
    }

    /**
     * 获取所有值
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> members(String key, Class<T> tClass) {
        return JacksonUtil.parseJson(members(key), tClass);
    }

    /**
     * 获取所有值
     *
     * @param key       key
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> members(String key, TypeReference<T> reference) {
        return JacksonUtil.parseJson(members(key), reference);
    }

    /**
     * 移动值到新集合
     *
     * @param key   key
     * @param value 值
     * @return {@link Boolean}
     */
    public static <T> Boolean move(String key, T value, String newKey) {
        return OPERATIONS.move(key, JacksonUtil.toJson(value), newKey);
    }
}