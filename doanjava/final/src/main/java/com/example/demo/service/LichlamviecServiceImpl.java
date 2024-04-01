package com.example.demo.service;

import com.example.demo.entity.Lichlamviec;
import com.example.demo.entity.LichlamviecPK;
import com.example.demo.repository.LichlamviecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichlamviecServiceImpl implements LichlamviecService{
    @Autowired
    private LichlamviecRepository lichlamviecRepository;
    @Override
    public Iterable<Lichlamviec> findAll() {
        return lichlamviecRepository.findAll();
    }

    @Override
    public Lichlamviec findOne(LichlamviecPK id) {
        return lichlamviecRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Lichlamviec lichlamviec) {
        lichlamviecRepository.save(lichlamviec);
    }

    @Override
    public void delete(LichlamviecPK id) {
        lichlamviecRepository.deleteById(id);
    }

    @Override
    public void update(Lichlamviec lichlamviec) {
        lichlamviecRepository.save(lichlamviec);
    }

    @Override
    public List<Lichlamviec> findByGvId(Integer gvId) {

        return lichlamviecRepository.findByLichlamviecPKGvId(gvId);
    }

    @Override
    public List<Lichlamviec> findByLopId(Integer lopId)
    {
        return lichlamviecRepository.findByLichlamviecPKLopId(lopId);
    }

    @Override
    public List<Lichlamviec> findByThu(Integer thu)
    {
        return lichlamviecRepository.findByLichlamviecPKThu(thu);
    }

    @Override
    public List<Lichlamviec> findByTiet(Integer tiet) {

        return lichlamviecRepository.findByLichlamviecPKTiet(tiet);
    }
}
