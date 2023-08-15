package cn.tangshh.universal.core.util;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.Map;

/**
 * Request Util
 *
 * @author Tang
 * @version v1.0
 */
@Slf4j
public final class RequestUtil {
    private RequestUtil() {
    }

    /**
     * Get current thread request bean
     *
     * @return {@link HttpServletRequest}
     */
    @Nullable
    public static HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return ((ServletRequestAttributes) attributes).getRequest();
        }
        return null;
    }

    /**
     * Get request origin ip
     *
     * @return {@link String} ip
     */
    public static String getRequestIp() {
        return getRequestIp(getRequest());
    }

    /**
     * Get request origin ip
     *
     * @param request request
     * @return {@link String} ip
     */
    public static String getRequestIp(@Nullable HttpServletRequest request) {
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
     * Get request uri
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
     * Get request param in uri path
     *
     * @return {@link Map}<{@link String}, {@link String[]}>
     */
    public static Map<String, String[]> getRequestParam() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return Collections.emptyMap();
        }
        return request.getParameterMap();
    }
}
