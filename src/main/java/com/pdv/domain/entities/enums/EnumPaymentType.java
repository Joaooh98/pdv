package com.pdv.domain.entities.enums;

import com.pdv.domain.utils.EnumUtil;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum EnumPaymentType implements IEnum {

    CARD("CARD", "Multibanco"),
    CASH("CASH", "Dinheiro");

    private final String key;

    private final String value;

    EnumPaymentType(String key, String value) {
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

    public static EnumPaymentType parseByKey(String key) {
        EnumPaymentType byKey = EnumUtil.parseByKey(EnumPaymentType.class, key);
        if (byKey != null) {
            return byKey;
        }

        return parseByValue(key);
    }

    public static EnumPaymentType parseByValue(String value) {
        return EnumUtil.parseByValue(EnumPaymentType.class, value);
    }
}
