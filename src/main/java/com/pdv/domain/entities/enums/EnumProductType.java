package com.pdv.domain.entities.enums;

import com.pdv.domain.utils.EnumUtil;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum EnumProductType implements IEnum {

    PRODUCT("PRODUCT", "product"),
    SERVICE("SERVICE", "service");

    private final String key;
    
    private final String value;

    EnumProductType(String key, String value) {
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

    public static EnumProductType parseByKey(String key) {
        return EnumUtil.parseByKey(EnumProductType.class, key);
    }

    public static EnumProductType parseByValue(String value) {
        return EnumUtil.parseByValue(EnumProductType.class, value);
    }
}
