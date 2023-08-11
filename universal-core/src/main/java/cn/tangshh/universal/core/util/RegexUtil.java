package cn.tangshh.universal.core.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 *
 * @author Tang
 * @version v1.0
 */
@Slf4j
public final class RegexUtil {
    private RegexUtil() {
    }

    /**
     * 正则替换
     *
     * @param str     原String
     * @param regex   正则表达式
     * @param replace 替换的value
     * @return {@link String} 替换结果
     */
    public static String regexReplace(String str, String regex, String replace) {
        if (StrUtil.isEmpty(str)) {
            return str;
        }
        return str.replaceAll(regex, replace);
    }

    /**
     * 正则校验
     *
     * @param str   校验String
     * @param regex 正则表达式
     * @return boolean 校验结果
     */
    public static boolean regexVerify(String str, String regex) {
        if (StrUtil.isAllNotEmpty(str, regex)) {
            return str.matches(regex);
        }
        return false;
    }

    /**
     * 正则搜索所有
     *
     * @param str   搜索String
     * @param regex 正则表达式
     * @return {@link List}<{@link String}> 搜索结果
     */
    public static List<String> regexSearchAll(String str, String regex) {
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 正则搜索首个
     *
     * @param str   搜索String
     * @param regex 正则表达式
     * @return {@link String} 首个匹配结果
     */
    public static String regexSearchFirst(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 正则搜索最后一个
     *
     * @param str   搜索String
     * @param regex 正则表达式
     * @return {@link String} 最后一个匹配结果
     */
    public static String regexSearchLast(String str, String regex) {
        List<String> result = regexSearchAll(str, regex);
        if (result.isEmpty()) {
            return StrUtil.EMPTY;
        }
        return result.get(result.size() - 1);
    }
}
