package com.example.demo.service;

import com.example.demo.entity.Diem;
import com.example.demo.entity.DiemPK;
import com.example.demo.entity.HocSinh;
import com.example.demo.repository.DiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiemServiceImpl implements DiemService{

    @Autowired
    private DiemRepository diemRepository;
    @Override
    public Iterable<Diem> findAll() {
        return diemRepository.findAll();
    }

    @Override
    public List<Diem> search(float diem) {
        return diemRepository.findByDiemContaining(diem);
    }


    @Override
    public Diem findOne(DiemPK id) {
        return diemRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Diem diem) {
        diemRepository.save(diem);
    }

    @Override
    public void delete(DiemPK id) {
        diemRepository.deleteById(id);
    }

    @Override
    public void update(Diem diem) {

    }

    @Override
    public List<Diem> findByHsId(Integer hsId) {
        return diemRepository.findByDiemPKHsId(hsId);
    }

    @Override
    public List<Diem> findByMhId(Integer mhId) {
        return diemRepository.findByDiemPKMhId(mhId);
    }

    @Override
    public List<Diem> findByLoaidiemId(Integer ldId) {
        return diemRepository.findByDiemPKLoaidiemId(ldId);
    }

    @Override
    public List<Diem> findByHockyId(Integer hk) {
        return diemRepository.findByDiemPKHocky(hk);
    }
}
