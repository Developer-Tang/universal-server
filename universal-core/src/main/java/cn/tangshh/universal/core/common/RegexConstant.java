package cn.tangshh.universal.core.common;

/**
 * Regex constant
 *
 * @author Tang
 * @version v1.0
 */
public interface RegexConstant {
    /** 邮箱正则 */
    String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    /** 手机号正则 */
    String MOBILE = "^(?:(?:\\+|00)86)?1(?:(?:3\\d)|(?:4[5-9])|(?:5[0-35-9])|(?:6[56])|(?:7[0-8])|(?:8\\d)|(?:9[1-35-9]))\\d{8}$";

    /** http/https开头正则 */
    String HTTP_STARTS_WITH = "^(https?|HTTPS?):.*$";

    /** 请求URL正则 */
    String REQ_URL = "^(https?|HTTPS?)://[a-zA-Z0-9-.]+(:\\d+)?(/\\S*)?$";
}
