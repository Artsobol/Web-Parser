package com.example.test.service;

import java.util.List;

public interface IService<T> {
    T create(T dto);
    T getById(Long id);
    List<T> getAll();
    T update(Long Id, T updated);
    void delete(Long id);
}
