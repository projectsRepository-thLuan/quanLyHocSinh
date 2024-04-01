package com.example.demo.configuration;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.GiaoVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private GiaoVienService giaoVienService;
    @Autowired
    private ChucVuRepository chucVuRepository;
    @Autowired
    private LoaiDiemRepository loaiDiemRepository;
    @Autowired
    private HocSinhRepository hocSinhRepository;
    @Autowired
    private LopRepository lopRepository;
    @Autowired
    private MonHocRepository monHocRepository;
    @Autowired
    private GiaoVienRepository giaoVienRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(roleRepository.findByName("ROLE_ADMIN") == null)
        {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        if(roleRepository.findByName("ROLE_MEMBER") == null)
        {
            roleRepository.save(new Role("ROLE_MEMBER"));
        }
        if (chucVuRepository.findByTencv("Teacher") == null){
            ChucVu cv = new ChucVu();
            cv.setTencv("Teacher");
            chucVuRepository.save(cv);
        }
        if(chucVuRepository.findByTencv("Student") == null)
        {
            ChucVu cv = new ChucVu();
            cv.setTencv("Student");
            chucVuRepository.save(cv);
        }
        if(loaiDiemRepository.findByTenloai("DiemMieng") == null)
        {
            LoaiDiem ld = new LoaiDiem();
            ld.setTenloai("DiemMieng");
            ld.setHeso(1);
            loaiDiemRepository.save(ld);
        }
        if(loaiDiemRepository.findByTenloai("Diem15p") == null)
        {
            LoaiDiem ld = new LoaiDiem();
            ld.setTenloai("Diem15p");
            ld.setHeso(1);
            loaiDiemRepository.save(ld);
        }
        if(loaiDiemRepository.findByTenloai("Diem1t") == null)
        {
            LoaiDiem ld = new LoaiDiem();
            ld.setTenloai("Diem1t");
            ld.setHeso(2);
            loaiDiemRepository.save(ld);
        }
        if(loaiDiemRepository.findByTenloai("DiemGK") == null)
        {
            LoaiDiem ld = new LoaiDiem();
            ld.setTenloai("DiemGK");
            ld.setHeso(2);
            loaiDiemRepository.save(ld);
        }
        if(loaiDiemRepository.findByTenloai("DiemCK") == null)
        {
            LoaiDiem ld = new LoaiDiem();
            ld.setTenloai("DiemCK");
            ld.setHeso(3);
            loaiDiemRepository.save(ld);
        }
        if(monHocRepository.findByTenmh("Toan") == null)
        {
            MonHoc m1 = new MonHoc();
            m1.setTenmh("Toan");
            m1.setHeso(2);
            monHocRepository.save(m1);
        }
        if(monHocRepository.findByTenmh("Vat Ly") == null)
        {
            MonHoc m2 = new MonHoc();
            m2.setTenmh("Vat Ly");
            m2.setHeso(1);
            monHocRepository.save(m2);
        }
        if(monHocRepository.findByTenmh("Anh Van") == null)
        {
            MonHoc m3 = new MonHoc();
            m3.setTenmh("Anh Van");
            m3.setHeso(2);
            monHocRepository.save(m3);
        }
        if(monHocRepository.findByTenmh("Sinh") == null)
        {
            MonHoc m4 = new MonHoc();
            m4.setTenmh("Sinh");
            m4.setHeso(1);
            monHocRepository.save(m4);
        }
        if(monHocRepository.findByTenmh("Lich Su") == null)
        {
            MonHoc m5 = new MonHoc();
            m5.setTenmh("Lich Su");
            m5.setHeso(1);
            monHocRepository.save(m5);
        }
        if(monHocRepository.findByTenmh("Dia Ly") == null)
        {
            MonHoc m6 = new MonHoc();
            m6.setTenmh("Dia Ly");
            m6.setHeso(1);
            monHocRepository.save(m6);
        }
        if(monHocRepository.findByTenmh("Ngu Van") == null)
        {
            MonHoc m7 = new MonHoc();
            m7.setTenmh("Ngu Van");
            m7.setHeso(2);
            monHocRepository.save(m7);
        }
        if(monHocRepository.findByTenmh("Hoa Hoc") == null)
        {
            MonHoc m8 = new MonHoc();
            m8.setTenmh("Hoa Hoc");
            m8.setHeso(1);
            monHocRepository.save(m8);
        }
        if(monHocRepository.findByTenmh("GDCD") == null)
        {
            MonHoc m9 = new MonHoc();
            m9.setTenmh("GDCD");
            m9.setHeso(1);
            monHocRepository.save(m9);
        }
        if(monHocRepository.findByTenmh("The Duc") == null)
        {
            MonHoc m10 = new MonHoc();
            m10.setTenmh("The Duc");
            m10.setHeso(1);
            monHocRepository.save(m10);
        }
        if(giaoVienRepository.findByHoten("Vu Hoang Em") == null && giaoVienRepository.findByUsername("admin1") == null)
        {
            try{
                String sDate1 = "24/4/2002";
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
                GiaoVien gv1 = new GiaoVien();
                gv1.setHoten("Vu Hoang Em");
                gv1.setDiachi("Bien Hoa");
                gv1.setNgaysinh(date1);
                gv1.setUsername("admin1");
                String pw1 = "123456";
                gv1.setPassword(passwordEncoder.encode(pw1));
                List<Role> roles = new ArrayList<>();
                roles.add(roleRepository.findByName("ROLE_MEMBER"));
                gv1.setRoles(roles);
                giaoVienRepository.save(gv1);
            }catch (Exception e){
                e.getStackTrace();
            }
        }
        if(giaoVienRepository.findByHoten("Diep Thai Binh") == null && giaoVienRepository.findByUsername("admin2") == null)
        {
            try{
                String sDate2 = "18/5/2002";
                Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
                GiaoVien gv2 = new GiaoVien();
                gv2.setHoten("Diep Thai Binh");
                gv2.setDiachi("Thu Duc");
                gv2.setNgaysinh(date2);
                gv2.setUsername("admin2");
                String pw2 = "123456";
                gv2.setPassword(passwordEncoder.encode(pw2));
                List<Role> roles = new ArrayList<>();
                roles.add(roleRepository.findByName("ROLE_ADMIN"));
                gv2.setRoles(roles);
                giaoVienRepository.save(gv2);
            }catch (Exception e){
                e.getStackTrace();
            }
        }

    }
}

