package cn.tangshh.universal.core.util;


import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.ListOperations;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis List类型 工具类
 *
 * @author Tang
 * @version v1.0
 */
public final class RedisListUtil extends RedisUtil {
    private final static ListOperations<String, String> OPERATIONS;

    static {
        OPERATIONS = TEMPLATE.opsForList();
    }

    private RedisListUtil() {
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
     * 索引查询
     *
     * @param key   key
     * @param index 索引
     * @return {@link String}
     */
    public static String index(String key, long index) {
        return OPERATIONS.index(key, index);
    }

    /**
     * 索引查询
     *
     * @param key    key
     * @param index  索引
     * @param tClass 目标类class
     * @return {@link T}
     */
    public static <T> T index(String key, long index, Class<T> tClass) {
        return JacksonUtil.parseJson(index(key, index), tClass);
    }

    /**
     * 索引查询
     *
     * @param key       key
     * @param index     索引
     * @param reference reference
     * @return {@link T}
     */
    public static <T> T index(String key, long index, TypeReference<T> reference) {
        return JacksonUtil.parseJson(index(key, index), reference);
    }

    /**
     * 查询第一次出现的索引位置
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public static <T> Long indexOf(String key, T value) {
        return OPERATIONS.indexOf(key, JacksonUtil.toJson(value));
    }

    /**
     * 查询最后一次出现的索引位置
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public static <T> Long lastIndexOf(String key, T value) {
        return OPERATIONS.lastIndexOf(key, JacksonUtil.toJson(value));
    }

    /**
     * 范围查询
     *
     * @param key key
     * @return {@link List}<{@link String}>
     */
    public static List<String> range(String key) {
        return range(key, 0, -1);
    }

    /**
     * 范围查询
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> range(String key, Class<T> tClass) {
        return JacksonUtil.parseJson(range(key), tClass);
    }

    /**
     * 范围查询
     *
     * @param key       key
     * @param reference reference
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> range(String key, TypeReference<T> reference) {
        return JacksonUtil.parseJson(range(key), reference);
    }

    /**
     * 范围查询
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @return {@link List}<{@link String}>
     */
    public static List<String> range(String key, long start, long end) {
        return OPERATIONS.range(key, start, end);
    }

    /**
     * 范围查询
     *
     * @param key    key
     * @param tClass 目标类class
     * @param start  开始
     * @param end    结束
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> range(String key, Class<T> tClass, long start, long end) {
        return JacksonUtil.parseJson(range(key, start, end), tClass);
    }

    /**
     * 范围查询
     *
     * @param key       key
     * @param reference reference
     * @param start     开始
     * @param end       结束
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> range(String key, TypeReference<T> reference, long start, long end) {
        return JacksonUtil.parseJson(range(key, start, end), reference);
    }


    /**
     * @param key   key
     * @param start 开始
     * @param end   结束
     */
    public static void trim(String key, long start, long end) {
        OPERATIONS.trim(key, start, end);
    }

    /**
     * 移除值
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public static Long remove(String key, Object value) {
        return remove(key, JacksonUtil.toJson(value), 1);
    }

    /**
     * 移除值
     *
     * @param key   key
     * @param value 值
     * @param count 计数
     * @return {@link Long}
     */
    public static Long remove(String key, Object value, long count) {
        return OPERATIONS.remove(key, count, JacksonUtil.toJson(value));
    }


    /**
     * 添加值到指定位置
     *
     * @param key   key
     * @param value 值
     * @param index 索引
     */
    public static void set(String key, long index, Object value) {
        OPERATIONS.set(key, index, JacksonUtil.toJson(value));
    }

    /**
     * 左侧添加值
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public static Long lPush(String key, Object value) {
        return OPERATIONS.leftPush(key, JacksonUtil.toJson(value));
    }

    /**
     * 左侧添加值
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public static Long lPush(String key, Object... values) {
        return OPERATIONS.leftPushAll(key, JacksonUtil.toJson(values));
    }

    /**
     * 左侧添加值
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public static Long lPush(String key, Collection<Object> values) {
        return OPERATIONS.leftPushAll(key, JacksonUtil.toJsons(values));
    }

    /**
     * 当key存在时左侧添加值
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public static Long lPushNx(String key, Object value) {
        return OPERATIONS.leftPushIfPresent(key, JacksonUtil.toJson(value));
    }

    /**
     * 左侧弹出一个值
     *
     * @param key key
     * @return {@link String}
     */
    public static String lPop(String key) {
        return OPERATIONS.leftPop(key);
    }

    /**
     * 左侧弹出一个值
     *
     * @param key     key
     * @param maxWait 最大等待
     * @param unit    单位
     * @return {@link String}
     */
    public static String lPop(String key, long maxWait, TimeUnit unit) {
        return OPERATIONS.leftPop(key, maxWait, unit);
    }

    /**
     * 左侧弹出一个值
     *
     * @param key      key
     * @param duration 持续时间
     * @return {@link String}
     */
    public static String lPop(String key, Duration duration) {
        return OPERATIONS.leftPop(key, duration);
    }

    /**
     * 左侧弹出一个值
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link String}
     */
    public static <T> T lPop(String key, Class<T> tClass) {
        return JacksonUtil.parseJson(lPop(key), tClass);
    }

    /**
     * 左侧弹出一个值
     *
     * @param key     key
     * @param tClass  目标类class
     * @param maxWait 最大等待
     * @param unit    单位
     * @return {@link T}
     */
    public static <T> T lPop(String key, Class<T> tClass, long maxWait, TimeUnit unit) {
        return JacksonUtil.parseJson(lPop(key, maxWait, unit), tClass);
    }

    /**
     * 左侧弹出一个值
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link String}
     */
    public static <T> T lPop(String key, Class<T> tClass, Duration duration) {
        return JacksonUtil.parseJson(lPop(key, duration), tClass);
    }

    /**
     * 左侧弹出一个值
     *
     * @param key       key
     * @param reference reference
     * @return {@link T}
     */
    public static <T> T lPop(String key, TypeReference<T> reference) {
        return JacksonUtil.parseJson(lPop(key), reference);
    }

    /**
     * 左侧弹出一个值
     *
     * @param key       key
     * @param reference reference
     * @param maxWait   最大等待
     * @param unit      单位
     * @return {@link T}
     */
    public static <T> T lPop(String key, TypeReference<T> reference, long maxWait, TimeUnit unit) {
        return JacksonUtil.parseJson(lPop(key, maxWait, unit), reference);
    }

    /**
     * 左侧弹出一个值
     *
     * @param key       key
     * @param reference reference
     * @param duration  持续时间
     * @return {@link T}
     */
    public static <T> T lPop(String key, TypeReference<T> reference, Duration duration) {
        return JacksonUtil.parseJson(lPop(key, duration), reference);
    }

    /**
     * 左侧弹出多个值
     *
     * @param key   key
     * @param count 计数
     * @return {@link List}<{@link String}>
     */
    public static List<String> lPop(String key, long count) {
        return OPERATIONS.leftPop(key, count);
    }

    /**
     * 左侧弹出多个值
     *
     * @param key    key
     * @param count  计数
     * @param tClass 目标类class
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> lPop(String key, long count, Class<T> tClass) {
        return JacksonUtil.parseJson(lPop(key, count), tClass);
    }

    /**
     * 左侧弹出多个值
     *
     * @param key       key
     * @param count     计数
     * @param reference reference
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> lPop(String key, long count, TypeReference<T> reference) {
        return JacksonUtil.parseJson(lPop(key, count), reference);
    }

    /**
     * 右侧添加值
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public static Long rPush(String key, Object value) {
        return OPERATIONS.rightPush(key, JacksonUtil.toJson(value));
    }

    /**
     * 右侧添加值
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public static Long rPush(String key, Collection<Object> values) {
        if (values != null) {
            return OPERATIONS.rightPushAll(key, JacksonUtil.toJsons(values));
        }
        return null;
    }

    /**
     * 右侧添加值
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public static Long rPushNx(String key, Object value) {
        return OPERATIONS.rightPushIfPresent(key, JacksonUtil.toJson(value));
    }

    /**
     * 右侧弹出一个值
     *
     * @param key key
     * @return {@link String}
     */
    public static String rPop(String key) {
        return OPERATIONS.rightPop(key);
    }

    /**
     * 右侧弹出一个值
     *
     * @param key     key
     * @param maxWait 最大等待
     * @param unit    单位
     * @return {@link String}
     */
    public static String rPop(String key, long maxWait, TimeUnit unit) {
        return OPERATIONS.rightPop(key, maxWait, unit);
    }

    /**
     * 右侧弹出一个值
     *
     * @param key      key
     * @param duration 持续时间
     * @return {@link String}
     */
    public static String rPop(String key, Duration duration) {
        return OPERATIONS.rightPop(key, duration);
    }

    /**
     * 右侧弹出一个值
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link T}
     */
    public static <T> T rPop(String key, Class<T> tClass) {
        return JacksonUtil.parseJson(rPop(key), tClass);
    }

    /**
     * 右侧弹出一个值
     *
     * @param key     key
     * @param tClass  目标类class
     * @param maxWait 最大等待
     * @param unit    单位
     * @return {@link T}
     */
    public static <T> T rPop(String key, Class<T> tClass, long maxWait, TimeUnit unit) {
        return JacksonUtil.parseJson(rPop(key, maxWait, unit), tClass);
    }

    /**
     * 右侧弹出一个值
     *
     * @param key      key
     * @param duration 持续时间
     * @param tClass   目标类class
     * @return {@link T}
     */
    public static <T> T rPop(String key, Class<T> tClass, Duration duration) {
        return JacksonUtil.parseJson(rPop(key, duration), tClass);
    }

    /**
     * 右侧弹出一个值
     *
     * @param key       key
     * @param reference reference
     * @return {@link String}
     */
    public static <T> T rPop(String key, TypeReference<T> reference) {
        return JacksonUtil.parseJson(rPop(key), reference);
    }

    /**
     * 右侧弹出一个值
     *
     * @param key       key
     * @param reference reference
     * @param maxWait   最大等待
     * @param unit      单位
     * @return {@link T}
     */
    public static <T> T rPop(String key, TypeReference<T> reference, long maxWait, TimeUnit unit) {
        return JacksonUtil.parseJson(rPop(key, maxWait, unit), reference);
    }

    /**
     * 右侧弹出一个值
     *
     * @param key       key
     * @param reference reference
     * @param duration  持续时间
     * @return {@link T}
     */
    public static <T> T rPop(String key, TypeReference<T> reference, Duration duration) {
        return JacksonUtil.parseJson(rPop(key, duration), reference);
    }

    /**
     * 右侧弹出多个值
     *
     * @param key   key
     * @param count 计数
     * @return {@link List}<{@link String}>
     */
    public static List<String> rPop(String key, long count) {
        return OPERATIONS.rightPop(key, count);
    }

    /**
     * 右侧弹出左侧添加（队列）
     *
     * @param key   key
     * @param value 值
     * @return {@link String}
     */
    @SuppressWarnings("unchecked")
    public static <T> T rPopLeftPush(String key, T value) {
        String popValue = OPERATIONS.rightPopAndLeftPush(key, JacksonUtil.toJson(value));
        return JacksonUtil.parseJson(popValue, (Class<T>) value.getClass());
    }

    /**
     * 右侧弹出左侧添加（队列）
     *
     * @param key     key
     * @param value   值
     * @param maxWait 最大等待时间
     * @param unit    单位
     * @return {@link T}
     */
    @SuppressWarnings("unchecked")
    public static <T> T rPopLeftPush(String key, T value, long maxWait, TimeUnit unit) {
        String popValue = OPERATIONS.rightPopAndLeftPush(key, JacksonUtil.toJson(value), maxWait, unit);
        return JacksonUtil.parseJson(popValue, (Class<T>) value.getClass());
    }

    /**
     * 右侧弹出左侧添加（队列）
     *
     * @param key      key
     * @param value    值
     * @param duration 持续时间
     * @return {@link T}
     */
    @SuppressWarnings("unchecked")
    public static <T> T rPopLeftPush(String key, T value, Duration duration) {
        String popValue = OPERATIONS.rightPopAndLeftPush(key, JacksonUtil.toJson(value), duration);
        return JacksonUtil.parseJson(popValue, (Class<T>) value.getClass());
    }
}
