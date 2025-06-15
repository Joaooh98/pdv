package com.pdv.infra.data.panache.util;

import java.util.List;
import java.util.function.Consumer;

public class ListUtilModal {

    public static <T> void setEntityInList(List<T> list, Consumer<T> setEntityMethod) {
        list.forEach(item -> setEntityMethod.accept(item));
    }
}
