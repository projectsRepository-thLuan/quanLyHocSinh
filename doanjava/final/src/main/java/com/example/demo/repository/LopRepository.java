package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Lop;

@Repository
public interface LopRepository extends CrudRepository<Lop, Integer>{
	List<Lop> findByTenlopContaining(String term);
	List<Lop> findByKhoilopContaining(Integer kl);
	Lop findByTenlop(String term);
}
