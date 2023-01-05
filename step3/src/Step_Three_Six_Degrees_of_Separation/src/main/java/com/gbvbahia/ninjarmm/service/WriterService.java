package com.gbvbahia.ninjarmm.service;

public interface WriterService<T> {

  public void process(T t) throws Exception;
}
