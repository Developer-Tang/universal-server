package cn.tangshh.universal.core.util;

import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis Common Util
 *
 * @author Tang
 * @version v1.0
 */
@Slf4j
public class RedisUtil {
    protected final static StringRedisTemplate TEMPLATE;

    static {
        TEMPLATE = SpringUtil.getBean(StringRedisTemplate.class);
    }

    protected RedisUtil() {
    }

    /**
     * Query key is exist
     *
     * @param key key
     * @return boolean
     */
    public static boolean exists(@NotNull String key) {
        return Boolean.TRUE.equals(TEMPLATE.hasKey(key));
    }

    /**
     * Delete key
     *
     * @param key key
     * @return boolean
     */
    public static boolean del(@NotNull String key) {
        return Boolean.TRUE.equals(TEMPLATE.delete(key));
    }

    /**
     * Delete key
     *
     * @param keys keys
     * @return long
     */
    public static long del(@NotNull Collection<String> keys) {
        Long delNum = TEMPLATE.delete(keys);
        return delNum == null ? 0 : delNum;
    }

    /**
     * Set key valid time
     *
     * @param key    key
     * @param expire valid time
     * @param unit   unit
     * @return boolean
     */
    public static boolean expire(@NotNull String key, long expire, @NotNull TimeUnit unit) {
        if (expire > -1) {
            return Boolean.TRUE.equals(TEMPLATE.expire(key, expire, unit));
        }
        return false;
    }

    /**
     * Set key valid time
     *
     * @param key    key
     * @param expire valid time
     * @return boolean
     */
    public static boolean expire(@NotNull String key, long expire) {
        return expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * Set key valid time
     *
     * @param key  key
     * @param date valid time
     * @return boolean
     */
    public static boolean expire(@NotNull String key, @NotNull Date date) {
        return Boolean.TRUE.equals(TEMPLATE.expireAt(key, date));
    }

    /**
     * Rename key
     *
     * @param oldKey 旧key
     * @param newKey 新key
     */
    public static void rename(@NotNull String oldKey, @NotNull String newKey) {
        TEMPLATE.rename(oldKey, newKey);
    }

    /**
     * Scan key by expression
     *
     * @param keyExpr key expression
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> keys(@NotNull String keyExpr) {
        return TEMPLATE.keys(keyExpr);
    }

    /**
     * Query key valid time
     *
     * @param key key
     * @return {@link Long}
     */
    @Nullable
    public static Long ttl(@NotNull String key) {
        return ttl(key, TimeUnit.SECONDS);
    }

    /**
     * 查询key的有效期
     *
     * @param key  key
     * @param unit 单位
     * @return {@link Long}
     */
    @Nullable
    public static Long ttl(@NotNull String key, @NotNull TimeUnit unit) {
        return TEMPLATE.getExpire(key, unit);
    }

    /**
     * 查询key的数据类型
     *
     * @param key key
     * @return {@link DataType}
     */
    @Nullable
    public static DataType type(@NotNull String key) {
        return TEMPLATE.type(key);
    }
}
