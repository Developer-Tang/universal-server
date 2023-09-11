package cn.tangshh.universal.core.exception;

/**
 * <p>Exception thrown due to unauthorized</p>
 * <p>由于未经授权而抛出的异常</p>
 *
 * @author Tang
 * @version v1.0
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
