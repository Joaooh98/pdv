package com.pdv.domain.entities.enums;

import com.pdv.domain.utils.EnumUtil;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum EnumOrderStatus implements IEnum {

    PENDING("PENDING", "pendente"),
    CONFIRMED("CONFIRMED", "confirmado"),
    CANCELLED("CANCELLED", "cancelado"),
    ;

    private final String key;

    private final String value;

    EnumOrderStatus(String key, String value) {
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

    public static EnumOrderStatus parseByKey(String key) {
        return EnumUtil.parseByKey(EnumOrderStatus.class, key);
    }

    public static EnumOrderStatus parseByValue(String value) {
        return EnumUtil.parseByValue(EnumOrderStatus.class, value);
    }
}
