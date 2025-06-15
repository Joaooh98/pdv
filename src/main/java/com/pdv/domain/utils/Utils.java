package com.pdv.domain.utils;

import java.math.BigDecimal;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.utils.exception.PdvException;

/**
 *
 * @author JoaoCP
 */
public class Utils {

    public static void validateAmountFormat(BigDecimal amount) {
        var expectedPattern = amount.toString().replaceAll("[0-9]\\.[0-9][0-9]", "XXX");

        if (!expectedPattern.contains("XXX")) {
            throw new PdvException(EnumErrorCode.INTERNAL_ERROR);
        }
    }
}
