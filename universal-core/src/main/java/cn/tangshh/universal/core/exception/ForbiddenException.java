package cn.tangshh.universal.core.exception;

/**
 * <p>exception thrown due to insufficient privileges</p>
 * <p>由于权限不足而抛出的异常</p>
 *
 * @author Tang
 * @version v1.0
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }

}
