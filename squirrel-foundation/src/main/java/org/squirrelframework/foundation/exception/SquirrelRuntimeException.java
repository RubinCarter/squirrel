package org.squirrelframework.foundation.exception;

public class SquirrelRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -4324278329515258777L;

    private int errorCodeId;

//    private Throwable targetException;

//    private String errorMessage;

//    private String localizedErrorMessage;

    public SquirrelRuntimeException(ErrorCodes errorCode) {
        this(null, errorCode, new Object[]{});
    }

    public SquirrelRuntimeException(Throwable targetException, ErrorCodes errorCode) {
        this(targetException, errorCode, new Object[]{});
    }

    public SquirrelRuntimeException(ErrorCodes errorCode, Object...parameters) {
        this(null, errorCode, parameters);
    }

    public SquirrelRuntimeException(Throwable targetException, ErrorCodes errorCode, Object...parameters) {
        super(String.format("%08d : %s.", errorCode.getCode(), String.format(errorCode.getDescription(), parameters)), targetException);
        this.errorCodeId = errorCode.getCode();
    }

    public int getErrorCodeId() {
        return errorCodeId;
    }

    public Throwable getTargetException() {
        return this.getCause();
    }

}
