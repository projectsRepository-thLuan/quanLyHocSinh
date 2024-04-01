package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MonHoc;

@Repository
public interface MonHocRepository extends CrudRepository<MonHoc, Integer>{
	List<MonHoc> findByTenmhContaining(String term);
	MonHoc findByTenmh(String term);
}
