package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.MonHoc;

@Service
public interface MonHocService {
	Iterable<MonHoc> findAll();
    List<MonHoc> search(String term);
    MonHoc findOne(Integer id);
    void save(MonHoc monhoc);
    void delete(Integer id);
    void update(MonHoc monhoc);
}
