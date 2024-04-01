package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.GiaoVien;
import com.example.demo.repository.GiaoVienRepository;

@Service
public class GiaoVienServiceImpl implements GiaoVienService{
	@Autowired
	private GiaoVienRepository giaovienRepository;
	@Override
	public Iterable<GiaoVien> findAll() {
		// TODO Auto-generated method stub
		return giaovienRepository.findAll();
	}

	@Override
	public List<GiaoVien> search(String term) {
		// TODO Auto-generated method stub
		return giaovienRepository.findByHotenContaining(term);
	}

	@Override
	public GiaoVien findOne(Integer id) {
		// TODO Auto-generated method stub
		return giaovienRepository.findById(id).orElse(null);
	}

	@Override
	public GiaoVien findByUsername(String username) {
		return giaovienRepository.findByUsername(username);
	}

	@Override
	public void save(GiaoVien giaovien) {
		// TODO Auto-generated method stub
		giaovienRepository.save(giaovien);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		giaovienRepository.deleteById(id);
	}

	@Override
	public void update(GiaoVien giaovien) {
		// TODO Auto-generated method stub
		giaovienRepository.save(giaovien);
	}

}
