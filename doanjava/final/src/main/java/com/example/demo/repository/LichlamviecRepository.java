package com.example.demo.repository;

import com.example.demo.entity.Lichlamviec;
import com.example.demo.entity.LichlamviecPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichlamviecRepository extends CrudRepository<Lichlamviec, LichlamviecPK> {
    List<Lichlamviec> findByLichlamviecPKGvId(Integer gvId);
    List<Lichlamviec> findByLichlamviecPKLopId(Integer lopId);
    List<Lichlamviec> findByLichlamviecPKThu(Integer thu);
    List<Lichlamviec> findByLichlamviecPKTiet(Integer tiet);
}
