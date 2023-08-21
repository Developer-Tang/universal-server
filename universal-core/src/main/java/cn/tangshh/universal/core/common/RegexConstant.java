package cn.tangshh.universal.core.common;

/**
 * Regex constant
 *
 * @author Tang
 * @version v1.0
 */
public interface RegexConstant {
    /** Email regex */
    String EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    /** China mobile regex */
    String MOBILE = "^(?:(?:\\+|00)86)?1(?:(?:3\\d)|(?:4[5-9])|(?:5[0-35-9])|(?:6[56])|(?:7[0-8])|(?:8\\d)|(?:9[1-35-9]))\\d{8}$";

    /** http/https start with regex */
    String HTTP_STARTS_WITH = "^(https?|HTTPS?):.*$";

    /** Request url regex */
    String REQ_URL = "^(https?|HTTPS?)://[a-zA-Z0-9-.]+(:\\d+)?(/\\S*)?$";
}
