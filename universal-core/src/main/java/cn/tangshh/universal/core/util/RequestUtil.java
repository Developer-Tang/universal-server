package cn.tangshh.universal.core.util;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求工具类
 *
 * @author Tang
 * @version v1.0
 */
@Slf4j
public final class RequestUtil {
    private RequestUtil() {
    }

    /**
     * 获取请求对象
     *
     * @return {@link HttpServletRequest} 请求对象
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return ((ServletRequestAttributes) attributes).getRequest();
        }
        return null;
    }

    /**
     * 获取请求ip
     *
     * @return {@link String} ip
     */
    public static String getRequestIp() {
        return getRequestIp(getRequest());
    }

    /**
     * 获取请求ip
     *
     * @param request 请求对象
     * @return {@link String} ip
     */
    public static String getRequestIp(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取请求路径
     *
     * @return {@link String} 请求路径
     */
    public static String getReqUri() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return StrUtil.EMPTY;
        }
        return request.getRequestURI();
    }

    /**
     * 获取请求参数
     *
     * @return {@link Map}<{@link String}, {@link String[]}> 参数
     */
    public static Map<String, String[]> getRequestParam() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return new HashMap<>();
        }
        return request.getParameterMap();
    }
}
