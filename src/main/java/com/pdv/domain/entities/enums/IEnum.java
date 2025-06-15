package com.pdv.domain.entities.enums;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public interface IEnum {

    public String getKey();

    public String getValue();

    public boolean containsInEnum(String key);

}
