package cn.tangshh.universal.core.common;

/**
 * <p>Regex constant</p>
 * <p>正则表达式常量</p>
 *
 * @author Tang
 * @version v1.0
 */
public interface RegexConstant {
    /**
     * <p>email regex</p>
     * <p>邮箱正则</p>
     */
    String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    /**
     * <p>china mobile regex</p>
     * <p>手机号正则（中国）</p>
     */
    String MOBILE = "^(?:(?:\\+|00)86)?1(?:(?:3\\d)|(?:4[5-9])|(?:5[0-35-9])|(?:6[56])|(?:7[0-8])|(?:8\\d)|(?:9[1-35-9]))\\d{8}$";

    /**
     * <p>http/https start with regex</p>
     * <p>Http/Https协议开头正则</p>
     */
    String HTTP_STARTS_WITH = "^(https?|HTTPS?):.*$";

    /**
     * <p>request url regex</p>
     * <p>网络链接正则</p>
     */
    String REQ_URL = "^(https?|HTTPS?)://[a-zA-Z0-9-.]+(:\\d+)?(/\\S*)?$";
}
