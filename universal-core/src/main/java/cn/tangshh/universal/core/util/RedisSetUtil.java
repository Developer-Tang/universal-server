package cn.tangshh.universal.core.util;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>Redis Set Util</p>
 * <p>Redis Set类型工具</p>
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
     * <p>Query set size</p>
     * <p>查询集合元素数量</p>
     *
     * @param key key
     * @return {@link Long}
     */
    public static long size(@NotNull String key) {
        Long size = OPERATIONS.size(key);
        return size == null ? 0 : size;
    }

    /**
     * <p>Is exist value</p>
     * <p>是否存在值</p>
     *
     * @param key   key
     * @param value value
     * @return {@link Boolean}
     */
    public static boolean exist(@NotNull String key, @NotNull Object value) {
        return Boolean.TRUE.equals(OPERATIONS.isMember(key, JacksonUtil.toJson(value)));
    }

    /**
     * <p>Scan key by expression (* match anything)</p>
     * <p>通过表达式查询键（*匹配任意内容）</p>
     * <p>example: user:*,2023*,*09</p>
     *
     * @param key        key
     * @param expression expression
     * @return {@link List}<{@link String}>
     */
    public static List<String> scan(@NotNull String key, @NotNull String expression) {
        List<String> result = new ArrayList<>();
        try (Cursor<String> cursor = OPERATIONS.scan(key, ScanOptions.scanOptions().match(expression).build())) {
            while (cursor.hasNext()) {
                result.add(cursor.next());
            }
        }
        return result;
    }

    /**
     * <p>Add multiple value</p>
     * <p>添加多个值</p>
     *
     * @param key    key
     * @param values value
     * @return {@link Long}
     */
    @Nullable
    public static Long add(@NotNull String key, @NotNull Object... values) {
        return OPERATIONS.add(key, JacksonUtil.toJson(values));
    }

    /**
     * <p>Remove multiple value</p>
     * <p>删除多个值</p>
     *
     * @param key    key
     * @param values value
     * @return {@link Long}
     */
    @Nullable
    public static Long remove(@NotNull String key, @NotNull Object... values) {
        return OPERATIONS.remove(key, (Object[]) JacksonUtil.toJson(values));
    }

    /**
     * <p>Get the difference with another set of values</p>
     * <p>获取与另一组值的差集</p>
     *
     * @param key      key
     * @param otherKey contrast key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> difference(@NotNull String key, @NotNull String otherKey) {
        return OPERATIONS.difference(key, otherKey);
    }

    /**
     * <p>Get the difference with other multiple sets of values</p>
     * <p>获取与其他多组值的差集</p>
     *
     * @param key       key
     * @param otherKeys other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> difference(@NotNull String key, @NotNull Collection<String> otherKeys) {
        return OPERATIONS.difference(key, otherKeys);
    }

    /**
     * <p>Get the difference set of multiple sets of values</p>
     * <p>获取多组值的差集</p>
     *
     * @param keys keys
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> difference(@NotNull Collection<String> keys) {
        return OPERATIONS.difference(keys);
    }

    /**
     * <p>Get the difference with another set of values and store it in a new key</p>
     * <p>获取与另一组值的差集，并存储到新键</p>
     *
     * @param key      key
     * @param otherKey other key
     * @return {@link Long}
     */
    @Nullable
    public static Long differenceAndStore(@NotNull String key, @NotNull String otherKey, @NotNull String storeKey) {
        return OPERATIONS.differenceAndStore(key, otherKey, storeKey);
    }

    /**
     * <p>Get the difference with other multiple sets of values and store it in a new key</p>
     * <p>获取与其他多组值的差集，并存储到新键</p>
     *
     * @param key       key
     * @param otherKeys other key
     * @param storeKey  new key
     * @return {@link Long}
     */
    @Nullable
    public static Long differenceAndStore(@NotNull String key, @NotNull Collection<String> otherKeys, @NotNull String storeKey) {
        return OPERATIONS.differenceAndStore(key, otherKeys, storeKey);
    }

    /**
     * <p>Get the difference set of multiple sets of values and store it in a new key</p>
     * <p>获取多组值的差集，并存储到新键</p>
     *
     * @param keys     keys
     * @param storeKey new key
     * @return {@link Long}
     */
    @Nullable
    public static Long differenceAndStore(@NotNull Collection<String> keys, @NotNull String storeKey) {
        return OPERATIONS.differenceAndStore(keys, storeKey);
    }

    /**
     * <p>Get the intersect with another set of values</p>
     * <p>获取与另一组值的交集</p>
     *
     * @param key      key
     * @param otherKey other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> intersect(@NotNull String key, @NotNull String otherKey) {
        return OPERATIONS.intersect(key, otherKey);
    }

    /**
     * <p>Get the intersect with other multiple sets of values</p>
     * <p>获取与其他多组值的交集</p>
     *
     * @param key       key
     * @param otherKeys other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> intersect(@NotNull String key, @NotNull Collection<String> otherKeys) {
        return OPERATIONS.intersect(key, otherKeys);
    }

    /**
     * <p>Get the intersect set of multiple sets of values</p>
     * <p>获取多组值的交集</p>
     *
     * @param keys keys
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> intersect(@NotNull Collection<String> keys) {
        return OPERATIONS.intersect(keys);
    }

    /**
     * <p>Get the intersect with another set of values and store it in a new key</p>
     * <p>获取与另一组值的交集，并存储到新键</p>
     *
     * @param key      key
     * @param otherKey other key
     * @return {@link Long}
     */
    @Nullable
    public static Long intersectAndStore(@NotNull String key, @NotNull String otherKey, @NotNull String storeKey) {
        return OPERATIONS.intersectAndStore(key, otherKey, storeKey);
    }

    /**
     * <p>Get the intersect with other multiple sets of values and store it in a new key</p>
     * <p>获取与其他多组值的交集，并存储到新键</p>
     *
     * @param key       key
     * @param otherKeys other key
     * @param storeKey  new key
     * @return {@link Long}
     */
    @Nullable
    public static Long intersectAndStore(@NotNull String key, @NotNull Collection<String> otherKeys, @NotNull String storeKey) {
        return OPERATIONS.intersectAndStore(key, otherKeys, storeKey);
    }

    /**
     * <p>Get the intersect set of multiple sets of values and store it in a new key</p>
     * <p>获取多组值的交集，并存储到新键</p>
     *
     * @param keys     keys
     * @param storeKey new key
     * @return {@link Long}
     */
    @Nullable
    public static Long intersectAndStore(@NotNull Collection<String> keys, @NotNull String storeKey) {
        return OPERATIONS.intersectAndStore(keys, storeKey);
    }

    /**
     * <p>Get the union with another set of values</p>
     * <p>获取与另一组值的并集</p>
     *
     * @param key      key
     * @param otherKey other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> union(@NotNull String key, @NotNull String otherKey) {
        return OPERATIONS.union(key, otherKey);
    }

    /**
     * <p>Get the union with other multiple sets of values</p>
     * <p>获取与其他多组值的并集</p>
     *
     * @param key       key
     * @param otherKeys other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> union(@NotNull String key, @NotNull Collection<String> otherKeys) {
        return OPERATIONS.union(key, otherKeys);
    }

    /**
     * <p>Get the union set of multiple sets of values</p>
     * <p>获取多组值的并集</p>
     *
     * @param otherKeys other key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> union(@NotNull Collection<String> otherKeys) {
        return OPERATIONS.union(otherKeys);
    }

    /**
     * <p>Get the union with another set of values and store it in a new key</p>
     * <p>获取与另一组值的并集，并存储到新键</p>
     *
     * @param key      key
     * @param otherKey other key
     * @param storeKey new key
     * @return {@link Long}
     */
    @Nullable
    public static Long unionAndStore(@NotNull String key, @NotNull String otherKey, @NotNull String storeKey) {
        return OPERATIONS.unionAndStore(key, otherKey, storeKey);
    }

    /**
     * <p>Get the union with other multiple sets of values and store it in a new key</p>
     * <p>获取与其他多组值的并集，并存储到新键</p>
     *
     * @param key       key
     * @param otherKeys other key
     * @param storeKey  new key
     * @return {@link Long}
     */
    @Nullable
    public static Long unionAndStore(@NotNull String key, @NotNull Collection<String> otherKeys, @NotNull String storeKey) {
        return OPERATIONS.unionAndStore(key, otherKeys, storeKey);
    }

    /**
     * <p>Get the union set of multiple sets of values and store it in a new key</p>
     * <p>获取多组值的并集，并存储到新键</p>
     *
     * @param keys     key
     * @param storeKey new key
     * @return {@link Long}
     */
    @Nullable
    public static Long unionAndStore(@NotNull Collection<String> keys, @NotNull String storeKey) {
        return OPERATIONS.unionAndStore(keys, storeKey);
    }

    /**
     * <p>Random pop a value </p>
     * <p>随机弹出一个值</p>
     *
     * @param key key
     * @return {@link String}
     */
    @Nullable
    public static String pop(@NotNull String key) {
        return OPERATIONS.pop(key);
    }

    /**
     * <p>Random pop multiple value (Random)</p>
     * <p>随机弹出多个值</p>
     *
     * @param key   key
     * @param count count
     * @return {@link List}<{@link String}>
     */
    @Nullable
    public static List<String> pop(@NotNull String key, long count) {
        return OPERATIONS.pop(key, count);
    }

    /**
     * <p>Random get a value</p>
     * <p>随机获取一个值</p>
     *
     * @param key key
     * @return {@link String}
     */
    public static String randomMember(@NotNull String key) {
        return OPERATIONS.randomMember(key);
    }

    /**
     * <p>Random get multiple value</p>
     * <p>随机获取多个值</p>
     *
     * @param key   key
     * @param count count
     * @return {@link List}<{@link String}>
     */
    @Nullable
    public static List<String> randomMembers(@NotNull String key, long count) {
        return OPERATIONS.randomMembers(key, count);
    }

    /**
     * <p>Random get multiple different value</p>
     * <p>随机获取多个不同的值</p>
     *
     * @param key   key
     * @param count count
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> distinctRandomMembers(@NotNull String key, long count) {
        return OPERATIONS.distinctRandomMembers(key, count);
    }


    /**
     * <p>获取所有值</p>
     * <p>Get all values</p>
     *
     * @param key key
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> members(@NotNull String key) {
        return OPERATIONS.members(key);
    }

    /**
     * <p>Move all value to new key</p>
     * <p>移动全部值到新键</p>
     *
     * @param key   key
     * @param value value
     * @return {@link Boolean}
     */
    public static boolean move(@NotNull String key, Object value, @NotNull String newKey) {
        return Boolean.TRUE.equals(OPERATIONS.move(key, JacksonUtil.toJson(value), newKey));
    }
}
