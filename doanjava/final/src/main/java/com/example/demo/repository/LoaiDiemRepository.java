package com.example.demo.repository;

import com.example.demo.entity.LoaiDiem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiDiemRepository extends CrudRepository<LoaiDiem, Integer> {
    List<LoaiDiem> findByTenloaiContaining(String term);
    LoaiDiem findByTenloai(String term);
}
