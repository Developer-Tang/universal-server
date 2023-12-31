package cn.tangshh.universal.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p> Jackson's serialization and deserialization tools</p>
 * <p>基于Jackson封装的序列化与反序列化工具</p>
 *
 * @author Tang
 * @version v1.0
 */
public final class JacksonUtil {
    private final static ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JacksonUtil() {
    }

    /**
     * <p>Serialize to json string</p>
     * <p>序列化为json字符串</p>
     *
     * @param value value
     * @return {@link String}
     */
    public static String toJson(@NotNull Object value) {
        try {
            return value instanceof String ? (String) value : MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Batch serialize to json string</p>
     * <p>批量序列化为json字符串</p>
     *
     * @param values value
     * @return {@link String}
     */
    public static String[] toJson(@NotNull Object... values) {
        String[] vs = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            vs[i] = toJson(values[i]);
        }
        return vs;
    }

    /**
     * <p>Batch serialize to json string</p>
     * <p>批量序列化为json字符串</p>
     *
     * @param values value
     * @return {@link List}<{@link String}>
     */
    public static Collection<String> toJsons(@NotNull Collection<Object> values) {
        return values.parallelStream()
                .map(JacksonUtil::toJson)
                .collect(Collectors.toList());
    }

    /**
     * <p>Batch serialize to json string</p>
     * <p>批量序列化为json字符串</p>
     *
     * @param map map
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> toJsons(@NotNull Map<?, ?> map) {
        Map<String, String> strMap = new HashMap<>();
        map.forEach((k, v) -> strMap.put(toJson(k), toJson(v)));
        return strMap;
    }

    /**
     * <p>Deserialize json string to pojo</p>
     * <p>将json字符串反序列化为pojo</p>
     *
     * @param value  value
     * @param tClass target type class
     * @return {@link T}
     */
    @Nullable
    public static <T> T parseJson(String value, Class<T> tClass) {
        if (value == null) {
            return null;
        }
        try {
            return MAPPER.readValue(value, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Deserialize json string to pojo</p>
     * <p>将json字符串反序列化为pojo</p>
     *
     * @param value     value
     * @param reference reference
     * @return {@link T}
     */
    @Nullable
    public static <T> T parseJson(@NotNull String value, TypeReference<T> reference) {
        if (value == null) {
            return null;
        }
        try {
            return MAPPER.readValue(value, reference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Deserialize json string to pojo</p>
     * <p>将json字符串反序列化为pojo</p>
     *
     * @param values value
     * @param tClass target type class
     * @return {@link List}<{@link T}>
     */
    @Nullable
    public static <T> List<T> parseJson(List<String> values, Class<T> tClass) {
        if (values == null) {
            return null;
        }
        return values.parallelStream()
                .map(e -> parseJson(e, tClass))
                .collect(Collectors.toList());
    }

    /**
     * <p>Batch deserialize json string to pojo</p>
     * <p>批量反序列化json字符串为pojo</p>
     *
     * @param values    value
     * @param reference reference
     * @return {@link List}<{@link T}>
     */
    @Nullable
    public static <T> List<T> parseJson(List<String> values, TypeReference<T> reference) {
        if (values == null) {
            return null;
        }
        return values.parallelStream()
                .map(e -> parseJson(e, reference))
                .collect(Collectors.toList());
    }

    /**
     * <p>Batch deserialize json string to pojo</p>
     * <p>批量反序列化json字符串为pojo</p>
     *
     * @param values value
     * @param tClass target type class
     * @return {@link List}<{@link T}>
     */
    @Nullable
    public static <T> Set<T> parseJson(Set<String> values, Class<T> tClass) {
        if (values == null) {
            return null;
        }
        return values.parallelStream()
                .map(e -> parseJson(e, tClass))
                .collect(Collectors.toSet());
    }

    /**
     * <p>Batch deserialize json string to pojo</p>
     * <p>批量反序列化json字符串为pojo</p>
     *
     * @param values    value
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    @Nullable
    public static <T> Set<T> parseJson(Set<String> values, TypeReference<T> reference) {
        if (values == null) {
            return null;
        }
        return values.parallelStream()
                .map(e -> parseJson(e, reference))
                .collect(Collectors.toSet());
    }
}

