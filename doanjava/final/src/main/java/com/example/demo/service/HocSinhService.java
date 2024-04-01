package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import com.example.demo.entity.HocSinh;
import org.springframework.stereotype.Service;

@Service
public interface HocSinhService {
	Iterable<HocSinh> findAll();
    List<HocSinh> search(String term);
    HocSinh findOne(Integer id);
    void save(HocSinh hocsinh);
    void delete(Integer id);
    void update(HocSinh hocsinh);
    HocSinh findByTen(String ten);
    void saveAll(Iterable<HocSinh> hocsinhs);
    int create_random(int min, int max);
    HashMap<String, HocSinh> convertListToHashMap(List<HocSinh> l_hs);
}
