package cn.tangshh.universal.core.util;


import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Redis ZSet类型 工具类
 *
 * @author Tang
 * @version v1.0
 */
public final class RedisZSetUtil extends RedisUtil {
    private final static ZSetOperations<String, String> OPERATIONS;

    static {
        OPERATIONS = TEMPLATE.opsForZSet();
    }

    private RedisZSetUtil() {
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
     * 获取排序集合大小
     *
     * @param key key
     * @return {@link Long}
     */
    public static Long zCard(String key) {
        return OPERATIONS.zCard(key);
    }

    /**
     * 移除值
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public static Long remove(String key, Object... values) {
        return OPERATIONS.remove(key, (Object[]) JacksonUtil.toJson(values));
    }

    /**
     * 范围统计
     *
     * @param key      key
     * @param minScore 最小分数
     * @param maxScore 最大分数
     * @return {@link Long}
     */
    public static Long count(String key, double minScore, double maxScore) {
        return OPERATIONS.count(key, minScore, maxScore);
    }

    /**
     * 范围统计
     *
     * @param key   key
     * @param range 范围
     * @return {@link Long}
     */
    public static Long lexCount(String key, Range<String> range) {
        return OPERATIONS.lexCount(key, range);
    }

    /**
     * 范围查询
     *
     * @param key   key
     * @param start 开始
     * @param end   结束
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> range(String key, long start, long end) {
        return OPERATIONS.range(key, start, end);
    }

    /**
     * 范围查询
     *
     * @param key    key
     * @param start  开始
     * @param end    结束
     * @param tClass 目标类class
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> range(String key, Class<T> tClass, long start, long end) {
        return JacksonUtil.parseJson(OPERATIONS.range(key, start, end), tClass);
    }

    /**
     * 范围查询
     *
     * @param key       key
     * @param start     开始
     * @param end       结束
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> range(String key, TypeReference<T> reference, long start, long end) {
        return JacksonUtil.parseJson(OPERATIONS.range(key, start, end), reference);
    }

    /**
     * 排名
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public static Long rank(String key, Object value) {
        return OPERATIONS.rank(key, JacksonUtil.toJson(value));
    }

    /**
     * 反向排名
     *
     * @param key   key
     * @param value 值
     * @return {@link Long}
     */
    public static Long reverseRank(String key, Object value) {
        return OPERATIONS.reverseRank(key, JacksonUtil.toJson(value));
    }

    /**
     * 添加值
     *
     * @param key   key
     * @param value 值
     * @return boolean
     */
    public static boolean add(String key, Object value) {
        return add(key, value, 0);
    }

    /**
     * 添加值并设置分数
     *
     * @param key   key
     * @param value 值
     * @return boolean
     */
    public static boolean add(String key, Object value, double score) {
        return Boolean.TRUE.equals(OPERATIONS.add(key, JacksonUtil.toJson(value), score));
    }

    /**
     * 批量添加值并设置分数
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    @SafeVarargs
    public static Long add(String key, ZSetOperations.TypedTuple<Object>... values) {
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>(values.length);
        for (ZSetOperations.TypedTuple<Object> tuple : values) {
            set.add(ZSetOperations.TypedTuple.of(JacksonUtil.toJson(tuple.getValue()), tuple.getScore()));
        }
        return OPERATIONS.add(key, set);
    }

    /**
     * 批量添加值并设置分数
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public static Long add(String key, Set<ZSetOperations.TypedTuple<Object>> values) {
        Set<ZSetOperations.TypedTuple<String>> set = values.parallelStream()
                .map(e -> ZSetOperations.TypedTuple.of(JacksonUtil.toJson(e.getValue()), e.getScore()))
                .collect(Collectors.toSet());
        return OPERATIONS.add(key, set);
    }


    /**
     * 如果值不存在添加值
     *
     * @param key   key
     * @param value 值
     * @return boolean
     */
    public static boolean addNx(String key, Object value) {
        return addNx(key, value, 0);
    }

    /**
     * 如果值不存在添加值并设置分数
     *
     * @param key   key
     * @param value 值
     * @return boolean
     */
    public static boolean addNx(String key, Object value, double score) {
        return Boolean.TRUE.equals(OPERATIONS.addIfAbsent(key, JacksonUtil.toJson(value), score));
    }

    /**
     * 批量如果值不存在添加值并设置分数
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    @SafeVarargs
    public static Long addNx(String key, ZSetOperations.TypedTuple<Object>... values) {
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>(values.length);
        for (ZSetOperations.TypedTuple<Object> tuple : values) {
            set.add(ZSetOperations.TypedTuple.of(JacksonUtil.toJson(tuple.getValue()), tuple.getScore()));
        }
        return OPERATIONS.addIfAbsent(key, set);
    }

    /**
     * 批量如果值不存在添加值并设置分数
     *
     * @param key    key
     * @param values 值
     * @return {@link Long}
     */
    public static Long addNx(String key, Set<ZSetOperations.TypedTuple<Object>> values) {
        Set<ZSetOperations.TypedTuple<String>> set = values.parallelStream()
                .map(e -> ZSetOperations.TypedTuple.of(JacksonUtil.toJson(e.getValue()), e.getScore()))
                .collect(Collectors.toSet());
        return OPERATIONS.addIfAbsent(key, set);
    }

}
