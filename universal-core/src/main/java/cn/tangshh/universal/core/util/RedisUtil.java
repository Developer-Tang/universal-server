package cn.tangshh.universal.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 通用操作 工具类
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
     * 查询是否存在
     *
     * @param key key
     * @return boolean
     */
    public static boolean exists(String key) {
        return Boolean.TRUE.equals(TEMPLATE.hasKey(key));
    }

    /**
     * 删除
     *
     * @param key key
     * @return boolean
     */
    public static boolean del(String key) {
        return Boolean.TRUE.equals(TEMPLATE.delete(key));
    }

    /**
     * 删除
     *
     * @param keys keys
     * @return long 删除个数
     */
    public static long del(Collection<String> keys) {
        Long delNum = TEMPLATE.delete(keys);
        return delNum == null ? 0 : delNum;
    }

    /**
     * 设置过期时间
     *
     * @param key    key
     * @param expire 过期时间
     * @param unit   单位
     * @return boolean
     */
    public static boolean expire(String key, long expire, TimeUnit unit) {
        if (expire > 0) {
            return Boolean.TRUE.equals(TEMPLATE.expire(key, expire, unit));
        }
        return false;
    }

    /**
     * 设置过期时间
     *
     * @param key    key
     * @param expire 过期时间
     * @return boolean
     */
    public static boolean expire(String key, long expire) {
        return expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 设置过期时间
     *
     * @param key  key
     * @param date 过期时间
     * @return boolean
     */
    public static boolean expire(String key, Date date) {
        return Boolean.TRUE.equals(TEMPLATE.expireAt(key, date));
    }

    /**
     * 重命名key
     *
     * @param oldKey 旧key
     * @param newKey 新key
     */
    public static void rename(String oldKey, String newKey) {
        TEMPLATE.rename(oldKey, newKey);
    }

    /**
     * 通过表达式搜索key
     *
     * @param keyExpr key表达式
     * @return {@link Set}<{@link String}> 符合要求key的集合
     */
    public static Set<String> keys(String keyExpr) {
        return TEMPLATE.keys(keyExpr);
    }

    /**
     * 查询key的有效期
     *
     * @param key key
     * @return {@link Long}
     */
    public static Long ttl(String key) {
        return ttl(key, TimeUnit.SECONDS);
    }

    /**
     * 查询key的有效期
     *
     * @param key  key
     * @param unit 单位
     * @return {@link Long}
     */
    public static Long ttl(String key, TimeUnit unit) {
        return TEMPLATE.getExpire(key, unit);
    }

    /**
     * 查询key的数据类型
     *
     * @param key key
     * @return {@link DataType}
     */
    public static DataType type(String key) {
        return TEMPLATE.type(key);
    }
}
