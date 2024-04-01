package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.GiaoVien;

@Service
public interface GiaoVienService {
	Iterable<GiaoVien> findAll();
    List<GiaoVien> search(String term);
    GiaoVien findOne(Integer id);
    GiaoVien findByUsername(String username);
    void save(GiaoVien giaovien);
    void delete(Integer id);
    void update(GiaoVien giaovien);
}
