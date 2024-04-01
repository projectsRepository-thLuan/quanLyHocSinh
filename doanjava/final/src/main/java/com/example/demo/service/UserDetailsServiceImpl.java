package com.example.demo.service;

import com.example.demo.entity.GiaoVien;
import com.example.demo.entity.Role;
import com.example.demo.repository.GiaoVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private GiaoVienRepository giaoVienRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        GiaoVien user = giaoVienRepository.findByUsername(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> grantAuthorities = new ArrayList<>();
        List<Role> roles = user.getRoles();
        for(Role role : roles) {
            grantAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantAuthorities);
    }

}