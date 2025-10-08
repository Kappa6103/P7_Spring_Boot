package com.nnk.springboot.services.interfaces;

import java.util.List;
import java.util.Optional;

public interface ServiceCRUDAble<T> {

    public List<T> fetchAll();

    public void createAndSave(T t);

    public Optional<T> fetchById(Integer id);

    public void updateAndSave(T t);

    public boolean existsById(Integer id);

    public void deleteById(Integer id);

}
