package cn.tangshh.universal.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Jackson's serialization and deserialization tools
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
     * Serialize to json string
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
     * Serialize to json string
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
     * Serialize to json string
     *
     * @param values value
     * @return {@link List}<{@link String}>
     */
    public static Collection<String> toJsons(@NotNull Collection<?> values) {
        return values.parallelStream()
                .map(JacksonUtil::toJson)
                .collect(Collectors.toList());
    }

    /**
     * Serialize to json string
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
     * Deserialize json string to type
     *
     * @param value  value
     * @param tClass target type class
     * @return {@link T}
     */
    public static <T> T parseJson(@NotNull String value, Class<T> tClass) {
        try {
            return MAPPER.readValue(value, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserialize json string to type
     *
     * @param value     value
     * @param reference reference
     * @return {@link T}
     */
    public static <T> T parseJson(@NotNull String value, TypeReference<T> reference) {
        try {
            return MAPPER.readValue(value, reference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserialize json string to type
     *
     * @param values value
     * @param tClass target type class
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> parseJson(@NotNull List<String> values, Class<T> tClass) {
        return values.parallelStream()
                .map(e -> parseJson(e, tClass))
                .collect(Collectors.toList());
    }

    /**
     * Deserialize json string to type
     *
     * @param values    value
     * @param reference reference
     * @return {@link List}<{@link T}>
     */
    public static <T> List<T> parseJson(@NotNull List<String> values, TypeReference<T> reference) {
        return values.parallelStream()
                .map(e -> parseJson(e, reference))
                .collect(Collectors.toList());
    }

    /**
     * Deserialize json string to type
     *
     * @param values value
     * @param tClass target type class
     * @return {@link List}<{@link T}>
     */
    public static <T> Set<T> parseJson(@NotNull Set<String> values, Class<T> tClass) {
        return values.parallelStream()
                .map(e -> parseJson(e, tClass))
                .collect(Collectors.toSet());
    }

    /**
     * Deserialize json string to type
     *
     * @param values    value
     * @param reference reference
     * @return {@link Set}<{@link T}>
     */
    public static <T> Set<T> parseJson(@NotNull Set<String> values, TypeReference<T> reference) {
        return values.parallelStream()
                .map(e -> parseJson(e, reference))
                .collect(Collectors.toSet());
    }
}

