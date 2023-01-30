package com.ninjaone.filtermovies.service;

import java.util.List;

public interface FileContentReader<T> {
    List<T> readAll(String path);
}
