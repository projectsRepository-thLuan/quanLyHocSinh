package com.example.demo.controller;

import com.example.demo.entity.GiaoVien;
import com.example.demo.entity.Lop;
import com.example.demo.entity.MonHoc;
import com.example.demo.service.GiaoVienService;
import com.example.demo.service.LichlamviecService;
import com.example.demo.service.LopService;
import com.example.demo.service.MonHocService;
import com.example.demo.support.Cons;
import com.example.demo.support.Graph;
import com.example.demo.support.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class PhancongController {
    @Autowired
    private LichlamviecService lichlamviecService;
    @Autowired
    private LopService lopService;
    @Autowired
    private GiaoVienService giaoVienService;
    @Autowired
    private MonHocService monHocService;
    @GetMapping("/phancong")
    public String phancong_truong(RedirectAttributes redirect) throws CloneNotSupportedException {
        List<GiaoVien> giaoviens = (List<GiaoVien>) giaoVienService.findAll();
        List<Lop> lops = (List<Lop>) lopService.findAll();
        if (giaoviens == null || lops == null)
        {
            redirect.addFlashAttribute("error", "Không có giáo viên hoặc lớp ");
        }else{
            Map<String, List<GiaoVien>> d_loaigv = new HashMap<String, List<GiaoVien>>();
            for (MonHoc mh : monHocService.findAll())
            {
                d_loaigv.put(mh.getTenmh(), new ArrayList<GiaoVien>());
            }
            for (GiaoVien gv : giaoVienService.findAll())
            {
                if (gv.getMonhoc() != null){
                    List<GiaoVien> gvs = d_loaigv.get(gv.getMonhoc().getTenmh());
                    gvs.add(gv);
                }
            }
            boolean flag = true;
            int sotiet_1 = lops.size() * Cons.LoaiMon_1_lop;
            int sotiet_2 = lops.size() * Cons.LoaiMon_2_lop;
            int sotiet_3 = lops.size() * Cons.LoaiMon_3_lop;
            double sogv_1 = Math.ceil(sotiet_1 / Cons.LoaiMon_1_week);
            double sogv_2 = Math.ceil(sotiet_2 / Cons.LoaiMon_2_week);
            double sogv_3 = Math.ceil(sotiet_3 / Cons.LoaiMon_3_week);
            Set<String> set = d_loaigv.keySet();
            for (String key : set)
            {
                if(key.equals("Toan") == true || key.equals("Van") || key.equals("Anh"))
                {
                    if (d_loaigv.get(key).size() < sogv_1)
                        flag = false;
                }else if(key.equals("Ly") == true || key.equals("Hoa")){
                    if (d_loaigv.get(key).size() < sogv_2)
                        flag = false;
                }else{
                    if (d_loaigv.get(key).size() < sogv_3)
                        flag = false;
                }
            }
            if (flag == true)
            {
                Graph g = new Graph();
                for (GiaoVien gv : giaoVienService.findAll())
                {
                    Vertex v = new Vertex();
                    v.setGiaovien(gv);
                    g.add_vertex(v);
                }
                g.create_relation();
                g.tomau();
                redirect.addFlashAttribute("success", "Bạn đã xếp lịch thành công");
            }else{
                redirect.addFlashAttribute("error", "Không đủ điều kiển để xếp lịch");
            }
        }
        return "redirect:/quantri_admin";
    }
}
