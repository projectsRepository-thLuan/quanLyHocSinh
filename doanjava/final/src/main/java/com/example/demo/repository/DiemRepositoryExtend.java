package com.example.demo.repository;

import com.example.demo.entity.Diem;

import java.util.List;

public interface DiemRepositoryExtend {
    List<Diem> findByIdHS(Integer id);
    List<Diem> findByIdMH(Integer id);
    List<Diem> findByIdHK(Integer id);
}
