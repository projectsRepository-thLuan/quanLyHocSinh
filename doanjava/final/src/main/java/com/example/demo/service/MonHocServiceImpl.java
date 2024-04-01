package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MonHoc;
import com.example.demo.repository.MonHocRepository;

@Service
public class MonHocServiceImpl implements MonHocService{
	@Autowired
	private MonHocRepository monhocRepository;
	@Override
	public Iterable<MonHoc> findAll() {
		// TODO Auto-generated method stub
		return monhocRepository.findAll();
	}

	@Override
	public List<MonHoc> search(String term) {
		// TODO Auto-generated method stub
		return monhocRepository.findByTenmhContaining(term);
	}

	@Override
	public MonHoc findOne(Integer id) {
		// TODO Auto-generated method stub
		return monhocRepository.findById(id).orElse(null);
	}

	@Override
	public void save(MonHoc monhoc) {
		// TODO Auto-generated method stub
		monhocRepository.save(monhoc);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		monhocRepository.deleteById(id);
	}

	@Override
	public void update(MonHoc monhoc) {
		// TODO Auto-generated method stub
		monhocRepository.save(monhoc);
	}

}
