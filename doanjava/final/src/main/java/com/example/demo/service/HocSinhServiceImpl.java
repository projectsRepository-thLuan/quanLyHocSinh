package com.example.demo.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.HocSinh;
import com.example.demo.repository.HocSinhRepository;

@Service
public class HocSinhServiceImpl implements HocSinhService{
	@Autowired
	private HocSinhRepository hocsinhRepository;

	@Override
	public Iterable<HocSinh> findAll() {
		// TODO Auto-generated method stub
		return hocsinhRepository.findAll();
	}

	@Override
	public List<HocSinh> search(String term) {
		// TODO Auto-generated method stub
		return hocsinhRepository.findByTenContaining(term);
	}

	@Override
	public HocSinh findOne(Integer id) {
		// TODO Auto-generated method stub
		return hocsinhRepository.findById(id).orElse(new HocSinh("noname"));
	}

	@Override
	public void save(HocSinh hocsinh) {
		// TODO Auto-generated method stub
		hocsinhRepository.save(hocsinh);
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		hocsinhRepository.deleteById(id);
	}

	@Override
	public void update(HocSinh hocsinh) {
		// TODO Auto-generated method stub
		hocsinhRepository.save(hocsinh);
	}

	@Override
	public HocSinh findByTen(String ten) {
		return hocsinhRepository.findByTen(ten);

	}

	@Override
	public void saveAll(Iterable<HocSinh> hocsinhs) {
		hocsinhRepository.saveAll(hocsinhs);
	}

	@Override
	public int create_random(int min, int max) {
		int random_int = (int)(Math.random() * (max - min + 1) + 1);
		return random_int;
	}

	@Override
	public HashMap<String, HocSinh> convertListToHashMap(List<HocSinh> l_hs) {
		HashMap<String, HocSinh> map = new HashMap<>();
		for(HocSinh hs:l_hs){
			if (map.containsKey(hs.getTen()) != true)
			{
				map.put(hs.getTen(), hs);
			}
		}
		return map;
	}

}
