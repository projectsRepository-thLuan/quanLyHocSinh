package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Lop;
import org.springframework.stereotype.Service;

@Service
public interface LopService {
	Iterable<Lop> findAll();
    List<Lop> search(String term);
    Lop findOne(Integer id);
    void save(Lop lop);
    void delete(Integer id);
    void update(Lop lop);
    boolean compare_lop(Lop lop, List<Lop> ls);
    List<Lop> search_khoilop(Integer kl);
}
