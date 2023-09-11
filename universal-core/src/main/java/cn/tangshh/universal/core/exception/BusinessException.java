package cn.tangshh.universal.core.exception;

/**
 * <p>Exception thrown due to business processing failure</p>
 * <p>由于业务处理失败抛出的异常</p>
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
