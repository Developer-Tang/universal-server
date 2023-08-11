package cn.tangshh.universal.core.util;


import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis String类型 工具类
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
     * 拼接值
     *
     * @param key   key
     * @param value 值
     * @return {@link Integer} 长度
     */
    public static Integer append(String key, String value) {
        return OPERATIONS.append(key, value);
    }

    /**
     * 指定key的值+指定值
     *
     * @param key       key
     * @param increment 增量步长
     * @return {@link Double} 增量后结果
     */
    public static Double incr(String key, double increment) {
        return OPERATIONS.increment(key, increment);
    }

    /**
     * 指定key的值+指定值
     *
     * @param key       key
     * @param increment 增量步长
     * @return {@link Long} 增量后结果
     */
    public static Long incr(String key, long increment) {
        return OPERATIONS.increment(key, increment);
    }

    /**
     * 指定key的值+1
     *
     * @param key key
     * @return {@link Long} 增量后结果
     */
    public static Long incr(String key) {
        return incr(key, 1);
    }

    /**
     * 指定key的值-指定值
     *
     * @param key       key
     * @param decrement 递减步长
     * @return {@link Long}
     */
    public static Long decr(String key, long decrement) {
        return OPERATIONS.decrement(key, decrement);
    }

    /**
     * 指定key的值+1
     *
     * @param key key
     */
    public static Long decr(String key) {
        return decr(key, 1);
    }

    /**
     * 设置值
     *
     * @param key   key
     * @param value 值
     */
    public static void set(String key, Object value) {
        OPERATIONS.set(key, JacksonUtil.toJson(value));
    }

    /**
     * 设置值并设置有效期
     *
     * @param key       key
     * @param value     值
     * @param validTime 有效时间/s
     */
    public static void setEx(String key, Object value, long validTime) {
        setEx(key, value, validTime, TimeUnit.SECONDS);
    }

    /**
     * 设置值并设置有效期
     *
     * @param key       key
     * @param value     值
     * @param validTime 有效时间
     * @param unit      单位
     */
    public static void setEx(String key, Object value, long validTime, TimeUnit unit) {
        if (validTime > 0) {
            OPERATIONS.set(key, JacksonUtil.toJson(value), validTime, unit);
        }
    }

    /**
     * 设置值并设置有效期
     *
     * @param key     key
     * @param value   值
     * @param timeout 有效时间
     */
    public static void setEx(String key, Object value, Duration timeout) {
        OPERATIONS.set(key, JacksonUtil.toJson(value), timeout);
    }

    /**
     * 当key不存在时设置值
     *
     * @param key   key
     * @param value 值
     * @return boolean 是否设置成功
     */
    public static boolean setNx(String key, Object value) {
        return Boolean.TRUE.equals(OPERATIONS.setIfAbsent(key, JacksonUtil.toJson(value)));
    }

    /**
     * 当key不存在时设置值
     *
     * @param key       key
     * @param value     值
     * @param validTime 有效时间/s
     * @return boolean 是否设置成功
     */
    public static boolean setNx(String key, Object value, long validTime) {
        return setNx(key, value, validTime, TimeUnit.SECONDS);
    }

    /**
     * 当key不存在时设置值
     *
     * @param key       key
     * @param value     值
     * @param validTime 有效时间
     * @param unit      单位
     * @return boolean 是否设置成功
     */
    public static boolean setNx(String key, Object value, long validTime, TimeUnit unit) {
        return Boolean.TRUE.equals(OPERATIONS.setIfAbsent(key, JacksonUtil.toJson(value), validTime, unit));
    }

    /**
     * 设置多个String类型的值
     *
     * @param map {key:value,key:value,...}
     */
    public static void batchSet(Map<String, Object> map) {
        HashMap<String, String> hashMap = new HashMap<>();
        map.forEach((k, v) -> hashMap.put(k, JacksonUtil.toJson(v)));
        OPERATIONS.multiSet(hashMap);
    }

    /**
     * 设置多个String类型的值且都不存在时
     *
     * @param map {key:value,key:value,...}
     * @return boolean
     */
    public static boolean batchSetNx(Map<String, Object> map) {
        return Boolean.TRUE.equals(OPERATIONS.multiSetIfAbsent(JacksonUtil.toJsons(map)));
    }

    /**
     * 获取值
     *
     * @param key key
     * @return {@link String}
     */
    public static String get(String key) {
        return OPERATIONS.get(key);
    }

    /**
     * 获取值
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link T}
     */
    public static <T> T get(String key, Class<T> tClass) {
        return JacksonUtil.parseJson(get(key), tClass);
    }

    /**
     * 获取值
     *
     * @param key       key
     * @param reference reference
     * @return {@link T}
     */
    public static <T> T get(String key, TypeReference<T> reference) {
        return JacksonUtil.parseJson(get(key), reference);
    }

    /**
     * 获取多个值
     *
     * @param keys keys
     * @return {@link List}<{@link String}>
     */
    public static List<String> batchGet(Collection<String> keys) {
        return OPERATIONS.multiGet(keys);
    }

    /**
     * 获取多个值
     *
     * @param keys   keys
     * @param tClass 目标类class
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> batchGet(Collection<String> keys, Class<T> tClass) {
        List<String> result = batchGet(keys);
        return JacksonUtil.parseJson(result, tClass);
    }

    /**
     * 获取多个值
     *
     * @param keys      keys
     * @param reference reference
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> batchGet(Collection<String> keys, TypeReference<T> reference) {
        List<String> result = batchGet(keys);
        return JacksonUtil.parseJson(result, reference);
    }

    /**
     * 获取旧值并设置新值
     *
     * @param key      key
     * @param newValue 新值
     * @return {@link T}
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAndSet(String key, T newValue) {
        String oldValue = OPERATIONS.getAndSet(key, JacksonUtil.toJson(newValue));
        return JacksonUtil.parseJson(oldValue, (Class<T>) newValue.getClass());
    }

    /**
     * 获取值并删除
     *
     * @param key key
     * @return {@link String}
     */
    public static String getAndDel(String key) {
        return OPERATIONS.getAndDelete(key);
    }

    /**
     * 获取值并删除
     *
     * @param key    key
     * @param tClass 目标类class
     * @return {@link T}
     */
    public static <T> T getAndDel(String key, Class<T> tClass) {
        return JacksonUtil.parseJson(getAndDel(key), tClass);
    }

    /**
     * 获取值并删除
     *
     * @param key       key
     * @param reference reference
     * @return {@link T}
     */
    public static <T> T getAndDel(String key, TypeReference<T> reference) {
        return JacksonUtil.parseJson(getAndDel(key), reference);
    }
}
