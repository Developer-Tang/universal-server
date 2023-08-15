package cn.tangshh.universal.core.exception;

/**
 * Forbidden Exception
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
