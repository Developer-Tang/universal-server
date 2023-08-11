package cn.tangshh.universal.core.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Jackson 序列化反序列化工具
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
     * 转JSON字符传
     *
     * @param value 值
     * @return {@link String}
     */
    public static String toJson(Object value) {
        try {
            return value instanceof String ? (String) value : MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 序列化
     *
     * @param values 值
     * @return {@link String}
     */
    public static String[] toJson(Object... values) {
        if (values == null) {
            return null;
        }
        String[] vs = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            vs[i] = toJson(values[i]);
        }
        return vs;
    }

    /**
     * 序列化
     *
     * @param values 值
     * @return {@link List}<{@link String}>
     */
    public static Collection<String> toJsons(Collection<?> values) {
        if (values == null) {
            return null;
        }
        return values.parallelStream()
                .map(JacksonUtil::toJson)
                .collect(Collectors.toList());
    }

    /**
     * 序列化
     *
     * @param map map
     * @return {@link Map}<{@link String}, {@link String}>
     */
    public static Map<String, String> toJsons(Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        Map<String, String> strMap = new HashMap<>();
        map.forEach((k, v) -> strMap.put(toJson(k), toJson(v)));
        return strMap;
    }

    /**
     * 反序列化
     *
     * @param value  值
     * @param tClass 目标类class
     * @return {@link T}
     */
    public static <T> T parseJson(String value, Class<T> tClass) {
        if (StrUtil.isBlank(value)) {
            return null;
        }
        try {
            return MAPPER.readValue(value, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化
     *
     * @param value     值
     * @param reference reference
     * @return {@link T}
     */
    public static <T> T parseJson(String value, TypeReference<T> reference) {
        if (StrUtil.isBlank(value)) {
            return null;
        }
        try {
            return MAPPER.readValue(value, reference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化
     *
     * @param values 值
     * @param tClass 目标类class
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> parseJson(List<String> values, Class<T> tClass) {
        if (values == null) {
            return null;
        }
        return values.parallelStream()
                .map(e -> parseJson(e, tClass))
                .collect(Collectors.toList());
    }

    /**
     * 反序列化
     *
     * @param values    值
     * @param reference reference
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> parseJson(List<String> values, TypeReference<T> reference) {
        if (values == null) {
            return null;
        }
        return values.parallelStream()
                .map(e -> parseJson(e, reference))
                .collect(Collectors.toList());
    }

    /**
     * 反序列化
     *
     * @param values 值
     * @param tClass 目标类class
     * @return {@link List}<{@link T}>
     */
    public static <T> Set<T> parseJson(Set<String> values, Class<T> tClass) {
        if (values == null) {
            return null;
        }
        return values.parallelStream()
                .map(e -> parseJson(e, tClass))
                .collect(Collectors.toSet());
    }

    /**
     * 反序列化
     *
     * @param values    值
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> parseJson(Set<String> values, TypeReference<T> reference) {
        if (values == null) {
            return null;
        }
        return values.parallelStream()
                .map(e -> parseJson(e, reference))
                .collect(Collectors.toSet());
    }
}

