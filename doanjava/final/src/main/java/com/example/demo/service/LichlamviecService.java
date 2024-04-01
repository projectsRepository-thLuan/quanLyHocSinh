package com.example.demo.service;

import com.example.demo.entity.Lichlamviec;
import com.example.demo.entity.LichlamviecPK;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LichlamviecService {
    Iterable<Lichlamviec> findAll();
    Lichlamviec findOne(LichlamviecPK id);
    void save(Lichlamviec lichlamviec);
    void delete(LichlamviecPK id);
    void update(Lichlamviec lichlamviec);
    List<Lichlamviec> findByGvId(Integer gvId);
    List<Lichlamviec> findByLopId(Integer lopId);
    List<Lichlamviec> findByThu(Integer thu);
    List<Lichlamviec> findByTiet(Integer tiet);
}
