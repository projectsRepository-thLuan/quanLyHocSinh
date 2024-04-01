package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GiaoVien;

@Repository
public interface GiaoVienRepository extends CrudRepository<GiaoVien, Integer>{
	List<GiaoVien> findByHotenContaining(String term);
	GiaoVien findByHoten(String term);
	GiaoVien findByUsername(String username);
}
