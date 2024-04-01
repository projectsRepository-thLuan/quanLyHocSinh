package com.example.demo.service;

import com.example.demo.entity.ChucVu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChucVuService {
    Iterable<ChucVu> findAll();
    List<ChucVu> search(String term);
    ChucVu findOne(Integer id);
    void save(ChucVu chucvu);
    void delete(Integer id);
    void update(ChucVu chucvu);
}
