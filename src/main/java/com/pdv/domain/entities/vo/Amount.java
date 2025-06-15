package com.pdv.domain.entities.vo;

import java.math.BigDecimal;

import com.pdv.domain.entities.enums.EnumErrorCode;
import com.pdv.domain.utils.exception.PdvException;

/**
 *
 * @author JoaoCP
 */
public class Amount {

    private BigDecimal amount;

    public Amount(final BigDecimal amount) {
        if (amount == null) {
            this.amount = BigDecimal.ZERO;
        }

        this.amount = amount;
        
        validate();
    }

    public BigDecimal getValue() {
        return this.amount;
    }

    public void validate() {
        if (this.amount == null) {
            throw new PdvException(EnumErrorCode.REQUIRED_FIELD, "amount");
        }

        if(this.amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new PdvException(EnumErrorCode.VALUE_NOT_ALLOWED, this.amount, "value must be greater than or equal to zero");
        }

    }

}
