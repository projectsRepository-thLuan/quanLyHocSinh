package com.example.demo.repository;

import com.example.demo.entity.Diem;
import com.example.demo.entity.DiemPK;
import com.example.demo.entity.HocSinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiemRepository extends JpaRepository<Diem, DiemPK> {
    List<Diem> findByDiemContaining(float diem);
    List<Diem> findByDiemPKHsId(Integer hsId);
    List<Diem> findByDiemPKMhId(Integer mhId);
    List<Diem> findByDiemPKLoaidiemId(Integer ldId);
    List<Diem> findByDiemPKHocky(Integer hkId);
}
