package cn.tangshh.universal.core.util;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.core.ListOperations;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis List Util
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
     * query list size
     *
     * @param key key
     * @return {@link Long}
     */
    public static long size(@NotNull String key) {
        Long size = OPERATIONS.size(key);
        return size == null ? 0 : size;
    }

    /**
     * query value by index
     *
     * @param key   key
     * @param index 索引
     * @return {@link String}
     */
    @Nullable
    public static String index(@NotNull String key, long index) {
        return OPERATIONS.index(key, index);
    }

    /**
     * query value first index
     *
     * @param key   key
     * @param value value
     * @return {@link Long}
     */
    public static Long indexOf(@NotNull String key, @NotNull Object value) {
        return OPERATIONS.indexOf(key, JacksonUtil.toJson(value));
    }

    /**
     * query value last index
     *
     * @param key   key
     * @param value value
     * @return {@link Long}
     */
    @Nullable
    public static Long lastIndexOf(@NotNull String key, @NotNull Object value) {
        return OPERATIONS.lastIndexOf(key, JacksonUtil.toJson(value));
    }

    /**
     * query all value
     *
     * @param key key
     * @return {@link List}<{@link String}>
     */
    public static List<String> rangeAll(@NotNull String key) {
        return range(key, 0, -1);
    }

    /**
     * query in range value
     *
     * @param key   key
     * @param start start index
     * @param end   end index
     * @return {@link List}<{@link String}>
     */
    @Nullable
    public static List<String> range(@NotNull String key, long start, long end) {
        return OPERATIONS.range(key, start, end);
    }


    /**
     * @param key   key
     * @param start start index
     * @param end   end index
     */
    public static void trim(@NotNull String key, long start, long end) {
        OPERATIONS.trim(key, start, end);
    }

    /**
     * Remove a value
     *
     * @param key   key
     * @param value value
     * @return {@link Long}
     */
    public static Long remove(@NotNull String key, Object value) {
        return remove(key, JacksonUtil.toJson(value), 1);
    }

    /**
     * Remove many value
     *
     * @param key   key
     * @param value value
     * @param count 计数
     * @return {@link Long}
     */
    @Nullable
    public static Long remove(@NotNull String key, Object value, long count) {
        return OPERATIONS.remove(key, count, JacksonUtil.toJson(value));
    }


    /**
     * Add value to index
     *
     * @param key   key
     * @param value value
     * @param index 索引
     */
    public static void set(@NotNull String key, long index, Object value) {
        OPERATIONS.set(key, index, JacksonUtil.toJson(value));
    }

    /**
     * Left push  value
     *
     * @param key   key
     * @param value value
     * @return {@link Long}
     */
    @Nullable
    public static Long lPush(@NotNull String key, Object value) {
        return OPERATIONS.leftPush(key, JacksonUtil.toJson(value));
    }

    /**
     * Left push value
     *
     * @param key    key
     * @param values value
     * @return {@link Long}
     */
    @Nullable
    public static Long lPush(@NotNull String key, @NotNull Object... values) {
        return OPERATIONS.leftPushAll(key, JacksonUtil.toJson(values));
    }

    /**
     * Left push value
     *
     * @param key    key
     * @param values value
     * @return {@link Long}
     */
    @Nullable
    public static Long lPush(@NotNull String key, @NotNull Collection<Object> values) {
        return OPERATIONS.leftPushAll(key, JacksonUtil.toJsons(values));
    }

    /**
     * If exist key then left push value
     *
     * @param key   key
     * @param value value
     * @return {@link Long}
     */
    @Nullable
    public static Long lPushNx(@NotNull String key, Object value) {
        return OPERATIONS.leftPushIfPresent(key, JacksonUtil.toJson(value));
    }

    /**
     * Left pop a value
     *
     * @param key key
     * @return {@link String}
     */
    @Nullable
    public static String lPop(@NotNull String key) {
        return OPERATIONS.leftPop(key);
    }

    /**
     * Left pop a value
     *
     * @param key     key
     * @param maxWait max wait time
     * @param unit    time unit
     * @return {@link String}
     */
    @Nullable
    public static String lPop(@NotNull String key, long maxWait, @NotNull TimeUnit unit) {
        return OPERATIONS.leftPop(key, maxWait, unit);
    }

    /**
     * Left pop a value
     *
     * @param key      key
     * @param duration time
     * @return {@link String}
     */
    @Nullable
    public static String lPop(@NotNull String key, @NotNull Duration duration) {
        return OPERATIONS.leftPop(key, duration);
    }


    /**
     * Left pop many value
     *
     * @param key   key
     * @param count 计数
     * @return {@link List}<{@link String}>
     */
    public static List<String> lPop(@NotNull String key, long count) {
        return OPERATIONS.leftPop(key, count);
    }

    /**
     * Right push value
     *
     * @param key   key
     * @param value value
     * @return {@link Long}
     */
    @Nullable
    public static Long rPush(@NotNull String key, Object value) {
        return OPERATIONS.rightPush(key, JacksonUtil.toJson(value));
    }

    /**
     * Right push value
     *
     * @param key    key
     * @param values value
     * @return {@link Long}
     */
    @Nullable
    public static Long rPush(@NotNull String key, @NotNull Collection<Object> values) {
        return OPERATIONS.rightPushAll(key, JacksonUtil.toJsons(values));
    }

    /**
     * If key exist then right push value
     *
     * @param key   key
     * @param value value
     * @return {@link Long}
     */
    @Nullable
    public static Long rPushNx(@NotNull String key, Object value) {
        return OPERATIONS.rightPushIfPresent(key, JacksonUtil.toJson(value));
    }

    /**
     * Right pop a value
     *
     * @param key key
     * @return {@link String}
     */
    @Nullable
    public static String rPop(@NotNull String key) {
        return OPERATIONS.rightPop(key);
    }

    /**
     * Right pop a value
     *
     * @param key     key
     * @param maxWait max wait time
     * @param unit    time unit
     * @return {@link String}
     */
    public static String rPop(@NotNull String key, long maxWait, @NotNull TimeUnit unit) {
        return OPERATIONS.rightPop(key, maxWait, unit);
    }

    /**
     * Right pop a value
     *
     * @param key      key
     * @param duration time
     * @return {@link String}
     */
    @Nullable
    public static String rPop(@NotNull String key, @NotNull Duration duration) {
        return OPERATIONS.rightPop(key, duration);
    }

    /**
     * Right pop many value
     *
     * @param key   key
     * @param count 计数
     * @return {@link List}<{@link String}>
     */
    @Nullable
    public static List<String> rPop(@NotNull String key, long count) {
        return OPERATIONS.rightPop(key, count);
    }

    /**
     * Right pop value and left push value
     *
     * @param key   key
     * @param value value
     * @return {@link String}
     */
    @Nullable
    public static String rPopLeftPush(@NotNull String key, @NotNull Object value) {
        return OPERATIONS.rightPopAndLeftPush(key, JacksonUtil.toJson(value));
    }

    /**
     * Right pop value and left push value
     *
     * @param key     key
     * @param value   value
     * @param maxWait max wait time
     * @param unit    time unit
     */
    @Nullable
    public static String rPopLeftPush(@NotNull String key, @NotNull Object value, long maxWait, @NotNull TimeUnit unit) {
        return OPERATIONS.rightPopAndLeftPush(key, JacksonUtil.toJson(value), maxWait, unit);
    }

    /**
     * Right pop value and left push value
     *
     * @param key      key
     * @param value    value
     * @param duration time
     * @return {@link String}
     */
    @Nullable
    public static String rPopLeftPush(@NotNull String key, Object value, @NotNull Duration duration) {
        return OPERATIONS.rightPopAndLeftPush(key, JacksonUtil.toJson(value), duration);
    }
}
