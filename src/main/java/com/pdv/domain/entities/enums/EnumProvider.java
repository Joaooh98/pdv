package com.pdv.domain.entities.enums;

import com.pdv.domain.utils.EnumUtil;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum EnumProvider implements IEnum {
    VENDUS("VENDUS", "vendus"),
    STRIPE("STRIPE", "stripe");

    private final String key;
    private final String value;

    EnumProvider(String key, String value) {
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

    public static EnumProvider parseByKey(String key) {
        return EnumUtil.parseByKey(EnumProvider.class, key);
    }

    public static EnumProvider parseByValue(String value) {
        return EnumUtil.parseByValue(EnumProvider.class, value);
    }
}