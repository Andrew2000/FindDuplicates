package com.andrew.findduplicates;

import java.util.function.Function;

public class FindDuplicatesColumn<T> {
    private final Function<T, String> getter;

    private FindDuplicatesColumn(Class<T> implementation, Function<T, String> getter) {
        this.getter = getter;
    }

    public static <S> FindDuplicatesColumn<S> forClassField(Class<S> implementation, Function<S, String> getter) {
        return new FindDuplicatesColumn<>(implementation, getter);
    }

    public String get(T t) {
        return getter.apply(t);
    }
}
