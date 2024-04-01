package com.example.demo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.entity.Lop;
import com.example.demo.repository.LopRepository;

@Service
public class LopServiceImpl implements LopService{
	@Autowired
	private LopRepository lopRepository;
	@Override
	public Iterable<Lop> findAll() {
		// TODO Auto-generated method stub
		return lopRepository.findAll();
	}

	@Override
	public List<Lop> search(String term) {
		// TODO Auto-generated method stub
		return lopRepository.findByTenlopContaining(term);
	}

	@Override
	public Lop findOne(Integer id) {
		// TODO Auto-generated method stub
		return lopRepository.findById(id).orElse(new Lop());
	}

	@Override
	public void save(Lop lop) {
		// TODO Auto-generated method stub
		lopRepository.save(lop);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		lopRepository.deleteById(id);
	}

	@Override
	public void update(Lop lop) {
		// TODO Auto-generated method stub
		lopRepository.save(lop);
	}
	@Override
	public boolean compare_lop(Lop lop, List<Lop> ls) {
		for (Lop l : ls) {
			if (l.getMalop() == lop.getMalop()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Lop> search_khoilop(Integer kl) {
		return lopRepository.findByKhoilopContaining(kl);
	}


}
