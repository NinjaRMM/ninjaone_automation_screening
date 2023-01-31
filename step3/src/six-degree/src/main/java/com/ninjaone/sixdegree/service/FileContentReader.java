package com.ninjaone.sixdegree.service;

import java.util.List;

public interface FileContentReader<T> {
    List<T> readAll(String path);
}
