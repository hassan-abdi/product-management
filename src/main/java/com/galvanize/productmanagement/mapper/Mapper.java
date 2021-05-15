package com.galvanize.productmanagement.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Mapper<T, R> extends Function<T, R> {
    default List<R> map(List<T> t) {
        return t.stream().map(this)
                .collect(Collectors.toList());
    }
}