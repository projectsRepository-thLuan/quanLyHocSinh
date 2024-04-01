package com.example.demo.repository;

import com.example.demo.entity.ChucVu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChucVuRepository extends CrudRepository<ChucVu, Integer> {
    List<ChucVu> findByTencvContaining(String term);
    ChucVu findByTencv(String term);
}
