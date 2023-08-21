package cn.tangshh.universal.core.util;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex Util
 *
 * @author Tang
 * @version v1.0
 */
@Slf4j
public final class RegexUtil {
    private RegexUtil() {
    }

    /**
     * Regex replace content
     *
     * @param content source data
     * @param regex   regex expression
     * @param replace replace expression
     * @return {@link String}
     */
    public static String replace(@NotNull String content, @NotNull String regex, @NotNull String replace) {
        return content.replaceAll(regex, replace);
    }

    /**
     * Regex verify content
     *
     * @param content source data
     * @param regex   regex expression
     * @return boolean
     */
    public static boolean verify(String content, String regex) {
        if (StrUtil.isAllNotEmpty(content, regex)) {
            return content.matches(regex);
        }
        return false;
    }

    /**
     * Regex search content return all
     *
     * @param content source data
     * @param regex   regex expression
     * @return {@link List}<{@link String}> 搜索结果
     */
    public static List<String> searchAll(String content, String regex) {
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * Regex search content return first
     *
     * @param content source data
     * @param regex   regex expression
     * @return {@link String}
     */
    public static String searchFirst(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        return StrUtil.EMPTY;
    }

    /**
     * Regex search content return last
     *
     * @param content source data
     * @param regex   regex expression
     * @return {@link String}
     */
    public static String searchLast(String content, String regex) {
        List<String> result = searchAll(content, regex);
        if (result.isEmpty()) {
            return StrUtil.EMPTY;
        }
        return result.get(result.size() - 1);
    }
}
