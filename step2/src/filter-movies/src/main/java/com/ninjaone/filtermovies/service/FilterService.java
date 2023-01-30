package com.ninjaone.filtermovies.service;

import java.util.List;

public interface FilterService <T, V> {
    List<T> filter(V value, List<T> list);
}
