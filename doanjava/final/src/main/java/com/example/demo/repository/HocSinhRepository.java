package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.HocSinh;

@Repository
public interface HocSinhRepository extends CrudRepository<HocSinh, Integer>{
	List<HocSinh> findByTenContaining(String term);
	HocSinh findByTen(String term);
}
