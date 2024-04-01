package com.example.demo.service;

import com.example.demo.entity.Diem;
import com.example.demo.entity.DiemPK;
import com.example.demo.entity.HocSinh;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiemService{
    Iterable<Diem> findAll();
    List<Diem> search(float diem);
    Diem findOne(DiemPK id);
    void save(Diem diem);
    void delete(DiemPK id);
    void update(Diem diem);
    List<Diem> findByHsId(Integer hsId);
    List<Diem> findByMhId(Integer mhId);
    List<Diem> findByLoaidiemId(Integer ldId);
    List<Diem> findByHockyId(Integer hk);
}
