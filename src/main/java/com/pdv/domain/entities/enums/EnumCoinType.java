package com.pdv.domain.entities.enums;

import com.pdv.domain.utils.EnumUtil;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum EnumCoinType implements IEnum {

    EUR("EUR", "Euro (European Union)"),
    GBP("GBP", "Pound Sterling (United Kingdom)"),
    USD("USD", "US Dollar (United States)"),
    CHF("CHF", "Swiss Franc (Switzerland)"),
    BRL("BRL", "Brazilian Real (Brazil)");

    private final String key;

    private final String value;

    EnumCoinType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean containsInEnum(String key) {
        return parseByKey(key) != null;
    }

    public static EnumCoinType parseByKey(String key) {
        return EnumUtil.parseByKey(EnumCoinType.class, key);
    }

    public static EnumCoinType parseByValue(String value) {
        return EnumUtil.parseByValue(EnumCoinType.class, value);
    }
}
