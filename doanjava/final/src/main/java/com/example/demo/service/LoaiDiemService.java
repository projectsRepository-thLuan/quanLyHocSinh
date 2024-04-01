package com.example.demo.service;

import com.example.demo.entity.LoaiDiem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoaiDiemService {
    Iterable<LoaiDiem> findAll();
    List<LoaiDiem> search(String term);
    LoaiDiem findOne(Integer id);
    void save(LoaiDiem loaidiem);
    void delete(Integer id);
    void update(LoaiDiem loaidiem);
}
