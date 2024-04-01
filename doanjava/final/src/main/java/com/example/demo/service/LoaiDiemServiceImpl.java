package com.example.demo.service;

import com.example.demo.entity.LoaiDiem;
import com.example.demo.repository.LoaiDiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiDiemServiceImpl implements LoaiDiemService{
    @Autowired
    private LoaiDiemRepository loaidiemRepository;
    @Override
    public Iterable<LoaiDiem> findAll() {
        return loaidiemRepository.findAll();
    }

    @Override
    public List<LoaiDiem> search(String term) {
        return loaidiemRepository.findByTenloaiContaining(term);
    }

    @Override
    public LoaiDiem findOne(Integer id) {
        return loaidiemRepository.findById(id).orElse(null);
    }

    @Override
    public void save(LoaiDiem loaidiem) {
        loaidiemRepository.save(loaidiem);
    }

    @Override
    public void delete(Integer id) {
        loaidiemRepository.deleteById(id);
    }

    @Override
    public void update(LoaiDiem loaidiem) {
        loaidiemRepository.save(loaidiem);
    }
}
