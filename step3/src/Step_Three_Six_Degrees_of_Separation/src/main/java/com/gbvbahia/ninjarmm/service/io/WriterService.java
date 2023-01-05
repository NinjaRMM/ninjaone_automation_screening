package com.gbvbahia.ninjarmm.service.io;

public interface WriterService<T> {

  public void process(T t) throws Exception;
}
