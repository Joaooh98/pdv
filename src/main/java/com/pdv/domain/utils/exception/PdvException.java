package com.pdv.domain.utils.exception;


import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.utils.StringUtils;

/**
 *
 * @author JoaoCP
 */
public class PdvException extends RuntimeException {

    private String errorCode = "-1";

    public PdvException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public PdvException(EnumErrorCode error) {
        super(error.getValue());
        this.errorCode = error.getKey();
    }

    public PdvException(EnumErrorCode error, Object... args) {
        super(StringUtils.stringPatternFormat(error.getValue(), args));
        this.errorCode = error.getKey();
    }

    public String getErrorCode() {
        return errorCode;
    }

}
