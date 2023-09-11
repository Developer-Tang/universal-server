package cn.tangshh.universal.core.util;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.core.ListOperations;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>Redis List Util</p>
 * <p>Redis List类型工具</p>
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
     * <p>Query list size</p>
     * <p>查询列表元素数量</p>
     *
     * @param key key
     * @return {@link Long}
     */
    public static long size(@NotNull String key) {
        Long size = OPERATIONS.size(key);
        return size == null ? 0 : size;
    }

    /**
     * <p>Query value by index</p>
     * <p>通过索引查询值</p>
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
     * <p>The index position of the first occurrence of the query value</p>
     * <p>查询值第一次出现的索引位置</p>
     *
     * @param key   key
     * @param value value
     * @return {@link Long}
     */
    public static Long indexOf(@NotNull String key, @NotNull Object value) {
        return OPERATIONS.indexOf(key, JacksonUtil.toJson(value));
    }

    /**
     * <p>The index position of the last occurrence of the query value</p>
     * <p>查询值最后一次出现的索引位置</p>
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
     * <p>Query all value</p>
     * <p>查询全部值</p>
     *
     * @param key key
     * @return {@link List}<{@link String}>
     */
    public static List<String> rangeAll(@NotNull String key) {
        return range(key, 0, -1);
    }

    /**
     * <p>Query values within range</p>
     * <p>查询范围内的值</p>
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
     * <p>Remove values outside of range</p>
     * <p>移除超出范围的值</p>
     * <p>ps: end > start,remove all(移除全部)</p>
     *
     * @param key   key
     * @param start start index
     * @param end   end index
     */
    public static void trim(@NotNull String key, long start, long end) {
        OPERATIONS.trim(key, start, end);
    }

    /**
     * <p>Remove a value</p>
     * <p>删除一个值</p>
     *
     * @param key   key
     * @param value value
     * @return {@link Long}
     */
    public static Long remove(@NotNull String key, Object value) {
        return remove(key, JacksonUtil.toJson(value), 1);
    }

    /**
     * <p>Remove multiple value</p>
     * <p>删除多个值</p>
     *
     * @param key   key
     * @param value value
     * @param count count
     * @return {@link Long}
     */
    @Nullable
    public static Long remove(@NotNull String key, Object value, long count) {
        return OPERATIONS.remove(key, count, JacksonUtil.toJson(value));
    }


    /**
     * <p>Add value to index</p>
     * <p>添加值到索引</p>
     *
     * @param key   key
     * @param value value
     * @param index index
     */
    public static void set(@NotNull String key, long index, Object value) {
        OPERATIONS.set(key, index, JacksonUtil.toJson(value));
    }

    /**
     * <p>Left push a value</p>
     * <p>左侧推入一个值</p>
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
     * <p>Left push multiple value</p>
     * <p>左侧推入多个值</p>
     *
     * @param key    key
     * @param values values
     * @return {@link Long}
     */
    @Nullable
    public static Long lPush(@NotNull String key, @NotNull Object... values) {
        return OPERATIONS.leftPushAll(key, JacksonUtil.toJson(values));
    }

    /**
     * <p>Left push multiple value</p>
     * <p>左侧推入多个值</p>
     *
     * @param key    key
     * @param values values
     * @return {@link Long}
     */
    @Nullable
    public static Long lPush(@NotNull String key, @NotNull Collection<Object> values) {
        return OPERATIONS.leftPushAll(key, JacksonUtil.toJsons(values));
    }

    /**
     * <p>If key exist then left push a value</p>
     * <p>如果键存在则左侧推入一个值</p>
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
     * <p>Left pop a value</p>
     * <p>左侧弹出一个值</p>
     *
     * @param key key
     * @return {@link String}
     */
    @Nullable
    public static String lPop(@NotNull String key) {
        return OPERATIONS.leftPop(key);
    }

    /**
     * <p>Left pop a value</p>
     * <p>左侧弹出一个值</p>
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
     * <p>Left pop a value</p>
     * <p>左侧弹出一个值</p>
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
     * <p>Left pop multiple value</p>
     * <p>左侧弹出多个值</p>
     *
     * @param key   key
     * @param count 计数
     * @return {@link List}<{@link String}>
     */
    public static List<String> lPop(@NotNull String key, long count) {
        return OPERATIONS.leftPop(key, count);
    }

    /**
     * <p>Right push a value</p>
     * <p>右侧推入一个值</p>
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
     * <p>Right push multiple value</p>
     * <p>右侧推入多个值</p>
     *
     * @param key    key
     * @param values values
     * @return {@link Long}
     */
    @Nullable
    public static Long rPush(@NotNull String key, @NotNull Collection<Object> values) {
        return OPERATIONS.rightPushAll(key, JacksonUtil.toJsons(values));
    }

    /**
     * <p>If key exist then right push a value</p>
     * <p>如果键存在则右侧推入一个值</p>
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
     * <p>Right pop a value</p>
     * <p>右侧弹出一个值</p>
     *
     * @param key key
     * @return {@link String}
     */
    @Nullable
    public static String rPop(@NotNull String key) {
        return OPERATIONS.rightPop(key);
    }

    /**
     * <p>Right pop a value</p>
     * <p>右侧弹出一个值</p>
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
     * <p>Right pop a value</p>
     * <p>右侧弹出一个值</p>
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
     * <p>Right pop multiple value</p>
     * <p>右侧弹出多个值</p>
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
     * <p>Right pop a value and left push a value</p>
     * <p>右侧弹出一个值并左侧推一个值</p>
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
     * <p>Right pop a value and left push a value</p>
     * <p>右侧弹出一个值并左侧推一个值</p>
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
     * <p>Right pop a value and left push a value</p>
     * <p>右侧弹出一个值并左侧推一个值</p>
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
