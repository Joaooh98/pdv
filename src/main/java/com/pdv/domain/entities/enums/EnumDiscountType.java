package com.pdv.domain.entities.enums;

import com.pdv.domain.utils.EnumUtil;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum EnumDiscountType implements IEnum {

    AMOUNT("AMOUNT", "VALOR"),
    PORCENTAGE("PORCENTAGE", "PERCENTUAL");

    private final String key;
    
    private final String value;

    EnumDiscountType(String key, String value) {
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

    public static EnumDiscountType parseByKey(String key) {
        return EnumUtil.parseByKey(EnumDiscountType.class, key);
    }

    public static EnumDiscountType parseByValue(String value) {
        return EnumUtil.parseByValue(EnumDiscountType.class, value);
    }
}
