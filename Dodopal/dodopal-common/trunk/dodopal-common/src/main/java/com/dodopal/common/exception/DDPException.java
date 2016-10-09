package com.dodopal.common.exception;

public class DDPException extends RuntimeException {
    private static final long serialVersionUID = -2174546271985778838L;
    private String code;

    public DDPException(String code) {
        super(code);
        this.code = code;
    }

    public DDPException(String code, String message) {
        super(message);
        this.code = code;
    }

    public DDPException(String code, String message, Throwable exception) {
        super(message, exception);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}