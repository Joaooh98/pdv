package com.pdv.infra.restclient.jlpay.dto;

import java.math.BigDecimal;

public class BalanceJLPayDTO {

    private BigDecimal balanceNow;

    private BigDecimal balancePrevious;

    public BigDecimal getBalanceNow() {
        return balanceNow;
    }

    public BigDecimal getBalancePrevious() {
        return balancePrevious;
    }

}
