package cn.tangshh.universal.core.util;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis String Util
 *
 * @author Tang
 * @version v1.0
 */
public final class RedisStrUtil extends RedisUtil {
    private final static ValueOperations<String, String> OPERATIONS;

    static {
        OPERATIONS = TEMPLATE.opsForValue();
    }

    private RedisStrUtil() {
    }

    /**
     * Append value
     *
     * @param key   key
     * @param value value
     * @return {@link Integer}
     */
    @Nullable
    public static Integer append(@NotNull String key, String value) {
        return OPERATIONS.append(key, value);
    }

    /**
     * value increment
     *
     * @param key       key
     * @param increment increment
     * @return {@link Double}
     */
    @Nullable
    public static Double incr(@NotNull String key, double increment) {
        return OPERATIONS.increment(key, increment);
    }

    /**
     * value increment
     *
     * @param key       key
     * @param increment increment
     * @return {@link Long}
     */
    @Nullable
    public static Long incr(@NotNull String key, long increment) {
        return OPERATIONS.increment(key, increment);
    }

    /**
     * value increment
     *
     * @param key key
     * @return {@link Long} increment
     */
    @Nullable
    public static Long incr(@NotNull String key) {
        return incr(key, 1);
    }

    /**
     * value decrement
     *
     * @param key       key
     * @param decrement 递减步长
     * @return {@link Long}
     */
    @Nullable
    public static Long decr(@NotNull String key, long decrement) {
        return OPERATIONS.decrement(key, decrement);
    }

    /**
     * value decrement
     *
     * @param key key
     */
    @Nullable
    public static Long decr(@NotNull String key) {
        return decr(key, 1);
    }

    /**
     * Set value
     *
     * @param key   key
     * @param value value
     */
    public static void set(@NotNull String key, Object value) {
        OPERATIONS.set(key, JacksonUtil.toJson(value));
    }

    /**
     * Set value and valid time
     *
     * @param key       key
     * @param value     value
     * @param validTime valid time (sec)
     */
    public static void setEx(@NotNull String key, Object value, long validTime) {
        OPERATIONS.set(key, JacksonUtil.toJson(value), validTime);
    }

    /**
     * Set value and valid time
     *
     * @param key       key
     * @param value     value
     * @param validTime valid time
     * @param unit      unit
     */
    public static void setEx(@NotNull String key, Object value, long validTime, @NotNull TimeUnit unit) {
        if (validTime > 0) {
            OPERATIONS.set(key, JacksonUtil.toJson(value), validTime, unit);
        }
    }

    /**
     * Set value and valid time
     *
     * @param key     key
     * @param value   value
     * @param timeout valid time
     */
    public static void setEx(@NotNull String key, Object value, @NotNull Duration timeout) {
        OPERATIONS.set(key, JacksonUtil.toJson(value), timeout);
    }

    /**
     * Set value if not exist key
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    public static boolean setNx(@NotNull String key, Object value) {
        return Boolean.TRUE.equals(OPERATIONS.setIfAbsent(key, JacksonUtil.toJson(value)));
    }

    /**
     * Set value and valid time if not exist key
     *
     * @param key       key
     * @param value     value
     * @param validTime 有效时间/s
     * @return boolean
     */
    public static boolean setNx(@NotNull String key, Object value, long validTime) {
        return setNx(key, value, validTime, TimeUnit.SECONDS);
    }

    /**
     * Set value and valid time if not exist key
     *
     * @param key       key
     * @param value     value
     * @param validTime 有效时间
     * @param unit      单位
     * @return boolean 是否设置成功
     */
    public static boolean setNx(@NotNull String key, Object value, long validTime, @NotNull TimeUnit unit) {
        return Boolean.TRUE.equals(OPERATIONS.setIfAbsent(key, JacksonUtil.toJson(value), validTime, unit));
    }

    /**
     * Set many key-value
     *
     * @param map {key:value,key:value,...}
     */
    public static void batchSet(@NotNull Map<String, Object> map) {
        HashMap<String, String> hashMap = new HashMap<>();
        map.forEach((k, v) -> hashMap.put(k, JacksonUtil.toJson(v)));
        OPERATIONS.multiSet(hashMap);
    }

    /**
     * Set many key-value if all not exist
     *
     * @param map {key:value,key:value,...}
     * @return boolean
     */
    public static boolean batchSetNx(@NotNull Map<String, Object> map) {
        return Boolean.TRUE.equals(OPERATIONS.multiSetIfAbsent(JacksonUtil.toJsons(map)));
    }

    /**
     * Get value
     *
     * @param key key
     * @return {@link String}
     */
    @Nullable
    public static String get(@NotNull String key) {
        return OPERATIONS.get(key);
    }

    /**
     * Get many value
     *
     * @param keys keys
     * @return {@link List}<{@link String}>
     */
    @Nullable
    public static List<String> batchGet(@NotNull Collection<String> keys) {
        return OPERATIONS.multiGet(keys);
    }

    /**
     * Get value and set new value
     *
     * @param key      key
     * @param newValue 新value
     * @return {@link T}
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getAndSet(@NotNull String key, @NotNull T newValue) {
        String oldValue = OPERATIONS.getAndSet(key, JacksonUtil.toJson(newValue));
        return JacksonUtil.parseJson(oldValue, (Class<T>) newValue.getClass());
    }

    /**
     * Get value and delete
     *
     * @param key key
     * @return {@link String}
     */
    @Nullable
    public static String getAndDel(@NotNull String key) {
        return OPERATIONS.getAndDelete(key);
    }
}
