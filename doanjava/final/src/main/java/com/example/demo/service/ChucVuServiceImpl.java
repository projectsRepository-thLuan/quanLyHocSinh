package com.example.demo.service;

import com.example.demo.entity.ChucVu;
import com.example.demo.repository.ChucVuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChucVuServiceImpl implements ChucVuService{
    @Autowired
    private ChucVuRepository chucvuRepository;
    @Override
    public Iterable<ChucVu> findAll() {
        return chucvuRepository.findAll();
    }

    @Override
    public List<ChucVu> search(String term) {
        return chucvuRepository.findByTencvContaining(term);
    }

    @Override
    public ChucVu findOne(Integer id) {
        return chucvuRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ChucVu chucvu) {
        chucvuRepository.save(chucvu);
    }

    @Override
    public void delete(Integer id) {
        chucvuRepository.deleteById(id);
    }

    @Override
    public void update(ChucVu chucvu) {
        chucvuRepository.save(chucvu);
    }
}
