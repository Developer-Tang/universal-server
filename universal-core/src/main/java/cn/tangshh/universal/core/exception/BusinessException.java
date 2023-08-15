package cn.tangshh.universal.core.exception;

/**
 * Business Exception
 *
 * @author Tang
 * @version v1.0
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
