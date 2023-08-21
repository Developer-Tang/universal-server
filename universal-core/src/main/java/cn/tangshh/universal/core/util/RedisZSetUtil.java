package cn.tangshh.universal.core.util;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Redis ZSet Util
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
     * Query set size
     *
     * @param key key
     * @return {@link Long}
     */
    @Nullable
    public static Long size(@NotNull String key) {
        return OPERATIONS.size(key);
    }

    /**
     * Query set size
     *
     * @param key key
     * @return {@link Long}
     */
    @Nullable
    public static Long zCard(@NotNull String key) {
        return OPERATIONS.zCard(key);
    }

    /**
     * Remove values
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
     * Range statistics
     *
     * @param key      key
     * @param minScore min score
     * @param maxScore max score
     * @return {@link Long}
     */
    @Nullable
    public static Long count(@NotNull String key, double minScore, double maxScore) {
        return OPERATIONS.count(key, minScore, maxScore);
    }

    /**
     * Range statistics
     *
     * @param key   key
     * @param range range
     * @return {@link Long}
     */
    @Nullable
    public static Long lexCount(@NotNull String key, @NotNull Range<String> range) {
        return OPERATIONS.lexCount(key, range);
    }

    /**
     * Range query
     *
     * @param key   key
     * @param start start index
     * @param end   end index
     * @return {@link Set}<{@link String}>
     */
    @Nullable
    public static Set<String> range(@NotNull String key, long start, long end) {
        return OPERATIONS.range(key, start, end);
    }

    /**
     * Get ranking
     *
     * @param key   key
     * @param value value
     * @return {@link Long}
     */
    @Nullable
    public static Long rank(@NotNull String key, Object value) {
        return OPERATIONS.rank(key, JacksonUtil.toJson(value));
    }

    /**
     * Gte reverse ranking
     *
     * @param key   key
     * @param value value
     * @return {@link Long}
     */
    @Nullable
    public static Long reverseRank(@NotNull String key, Object value) {
        return OPERATIONS.reverseRank(key, JacksonUtil.toJson(value));
    }

    /**
     * Add value
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    public static boolean add(@NotNull String key, Object value) {
        return add(key, value, 0);
    }

    /**
     * Add value and set scope
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    public static boolean add(@NotNull String key, Object value, double score) {
        return Boolean.TRUE.equals(OPERATIONS.add(key, JacksonUtil.toJson(value), score));
    }

    /**
     * Batch add value and set scope
     *
     * @param key    key
     * @param values value
     * @return {@link Long}
     */
    @Nullable
    @SafeVarargs
    public static Long add(@NotNull String key, @NotNull ZSetOperations.TypedTuple<Object>... values) {
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>(values.length);
        for (ZSetOperations.TypedTuple<Object> tuple : values) {
            set.add(ZSetOperations.TypedTuple.of(JacksonUtil.toJson(tuple.getValue()), tuple.getScore()));
        }
        return OPERATIONS.add(key, set);
    }

    /**
     * Batch add value and set scope
     *
     * @param key    key
     * @param values value
     * @return {@link Long}
     */
    @Nullable
    public static Long add(@NotNull String key, @NotNull Set<ZSetOperations.TypedTuple<Object>> values) {
        Set<ZSetOperations.TypedTuple<String>> set = values.parallelStream()
                .map(e -> ZSetOperations.TypedTuple.of(JacksonUtil.toJson(e.getValue()), e.getScore()))
                .collect(Collectors.toSet());
        return OPERATIONS.add(key, set);
    }


    /**
     * Add value if not exist
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    public static boolean addNx(@NotNull String key, Object value) {
        return addNx(key, value, 0);
    }

    /**
     * Add value and scope if not exist
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    public static boolean addNx(@NotNull String key, Object value, double score) {
        return Boolean.TRUE.equals(OPERATIONS.addIfAbsent(key, JacksonUtil.toJson(value), score));
    }

    /**
     * Batch add value and scope if not exist
     *
     * @param key    key
     * @param values value
     * @return {@link Long}
     */
    @Nullable
    @SafeVarargs
    public static Long addNx(@NotNull String key, @NotNull ZSetOperations.TypedTuple<Object>... values) {
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>(values.length);
        for (ZSetOperations.TypedTuple<Object> tuple : values) {
            set.add(ZSetOperations.TypedTuple.of(JacksonUtil.toJson(tuple.getValue()), tuple.getScore()));
        }
        return OPERATIONS.addIfAbsent(key, set);
    }

    /**
     * Batch add value and scope if not exist
     *
     * @param key    key
     * @param values value
     * @return {@link Long}
     */
    @Nullable
    public static Long addNx(@NotNull String key, @NotNull Set<ZSetOperations.TypedTuple<Object>> values) {
        Set<ZSetOperations.TypedTuple<String>> set = values.parallelStream()
                .map(e -> ZSetOperations.TypedTuple.of(JacksonUtil.toJson(e.getValue()), e.getScore()))
                .collect(Collectors.toSet());
        return OPERATIONS.addIfAbsent(key, set);
    }

}
