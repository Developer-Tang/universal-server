package cn.tangshh.universal.core.exception;

/**
 * Unauthorized Exception
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
