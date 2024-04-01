package com.example.demo.repository;

import com.example.demo.entity.Diem;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class DiemRepositoryImpl implements DiemRepositoryExtend{
    EntityManagerFactory factory;

    @Override
    public List<Diem> findByIdHS(Integer id) {
        return null;
    }

    @Override
    public List<Diem> findByIdMH(Integer id) {
        return null;
    }

    @Override
    public List<Diem> findByIdHK(Integer id) {
        return null;
    }
}
