package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class DiemController {
    @Autowired
    private DiemService diemService;
    @Autowired
    private LopService lopService;
    @Autowired
    private HocSinhService hocsinhService;
    @Autowired
    private MonHocService monhocService;
    @Autowired
    private LoaiDiemService loaiDiemService;

    @GetMapping("/diem")
    public String chon_lop(Model model){
        List<GiaoVienWithSelection> l_k10 = new ArrayList<GiaoVienWithSelection>();
        List<GiaoVienWithSelection> l_k11 = new ArrayList<GiaoVienWithSelection>();
        List<GiaoVienWithSelection> l_k12 = new ArrayList<GiaoVienWithSelection>();
        for(Lop l : lopService.findAll())
        {
            if (l.getKhoilop() == 10){
                l_k10.add(new GiaoVienWithSelection(false, l));
            }
            if (l.getKhoilop() == 11){
                l_k11.add(new GiaoVienWithSelection(false, l));
            }
            if (l.getKhoilop() == 12){
                l_k12.add(new GiaoVienWithSelection(false, l));
            }
        }
        DiemWithSelectionListWrapper wrapper = new DiemWithSelectionListWrapper();
        wrapper.setK10(l_k10);
        wrapper.setK11(l_k11);
        wrapper.setK12(l_k12);
        model.addAttribute("wrapper", wrapper);
        return "diem/chon_lop";
    }
    @GetMapping("/diem/{id}/detail")
    public String diem_detail(@PathVariable("id") Integer id, Model model, HttpServletRequest request
            , RedirectAttributes redirect){
        request.getSession().setAttribute("hocsinhlist", null);
        request.getSession().setAttribute("lop_id", id);
        if(model.asMap().get("success") != null)
            redirect.addFlashAttribute("success",model.asMap().get("success").toString());
        return "redirect:/diem/detail/page/1";
    }
    @GetMapping("/diem/detail/page/{pageNumber}")
    public String showDiemPage(HttpServletRequest request,
                                  @PathVariable int pageNumber, Model model) {
        PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("hocsinhlist");
        int pagesize = 8;
        List<HocSinh> list =(List<HocSinh>) lopService.findOne((Integer)request.getSession().getAttribute("lop_id")).getHocsinhs();
        if (pages == null) {
            pages = new PagedListHolder<>(list);
            pages.setPageSize(pagesize);
        } else {
            final int goToPage = pageNumber - 1;
            if (goToPage <= pages.getPageCount() && goToPage >= 0) {
                pages.setPage(goToPage);
            }
        }
        request.getSession().setAttribute("hocsinhlist", pages);
        int current = pages.getPage() + 1;
        int begin = 0;
        int end = 0;
        if (current == 1){
            begin = current;
            end = current + 5;
            if(end > pages.getPageCount())
                end = pages.getPageCount();
        }
        else if(current == pages.getPageCount()){
            begin = pages.getPageCount() - 5;
            end = pages.getPageCount();
            if(begin < 1)
                begin = 1;
        }else{
            begin = current - 1;
            end = current + 4;
            if (end > pages.getPageCount()){
                end = pages.getPageCount();
                if (end - begin < 6){
                    begin = begin - (6-(end-begin));
                }
                if (end - begin > 6){
                    begin = begin + (6-begin);
                }

            }
            if (begin < 1){
                begin = 1;
            }
        }
        int totalPageCount = pages.getPageCount();
        String baseUrl = "/diem/detail/page/";
        model.addAttribute("lop", lopService.findOne((Integer)request.getSession().getAttribute("lop_id")));
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("hocsinhs", pages);

        return "diem/dhs_detail_paging";
    }

    @GetMapping("/diem/detail/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model){
        ViewNhapDiem diem = new ViewNhapDiem();
        diem.setMasv(id);
        HocSinh hs = hocsinhService.findOne(id);
        model.addAttribute("monhocs", monhocService.findAll());
        model.addAttribute("loaidiems", loaiDiemService.findAll());
        model.addAttribute("lop", hs.getLop());
        model.addAttribute("diem", diem);
        return "diem/form_diem";
    }
    @PostMapping("/diem/save")
    public String save(@Valid ViewNhapDiem diem, RedirectAttributes redirect, Model model){
        HocSinh hs = hocsinhService.findOne(diem.getMasv());
        Lop lop = hs.getLop();
        MonHoc mh = monhocService.findOne(diem.getMamh());
        LoaiDiem ld = loaiDiemService.findOne(diem.getMaloai());
        if(diemService.findOne(new DiemPK(diem.getMasv(), diem.getMamh(), diem.getMaloai(), diem.getHocky())) == null){
            Diem r_diem = new Diem();
            r_diem.setDiemPK(new DiemPK());
            r_diem.setHocsinh(hs);
            r_diem.setMonhoc(mh);
            r_diem.setLoaidiem(ld);
            r_diem.getDiemPK().setHocky(diem.getHocky());
            r_diem.setDiem(diem.getDiem());
            diemService.save(r_diem);
            mh.getDiems().add(r_diem);
            monhocService.update(mh);
            ld.getDiems().add(r_diem);
            loaiDiemService.update(ld);
            hs.getDiems().add(r_diem);
            hocsinhService.update(hs);
        }else{
            Diem r_diem = diemService.findOne(new DiemPK(diem.getMasv(), diem.getMamh(), diem.getMaloai(), diem.getHocky()));
            r_diem.setDiem(diem.getDiem());
            diemService.save(r_diem);
        }

        return "redirect:/diem/" + "detail/" + hs.getMasv() + "/diem_hocsinh";
    }
    @GetMapping("/diem/{id}/nhapdiem_ds")
    public String nhapdiem_ds(@PathVariable("id") Integer id, Model model)
    {
        ViewNhapDiem diem = new ViewNhapDiem();
        diem.setLopid(id);
        model.addAttribute("monhocs", monhocService.findAll());
        model.addAttribute("diem", diem);
        return "diem/nhapdiem_ds";
    }
    @PostMapping("/diem/save_nhapdiem_ds")
    public String save_nhapdiem_ds(@RequestParam("file") MultipartFile file, @Valid ViewNhapDiem diem, RedirectAttributes redirect, Model model){
        if (file.isEmpty()) {

        } else {
            List<HocSinh> personList = new ArrayList<HocSinh>();
            HashMap<String, HocSinh> m_hs = hocsinhService.convertListToHashMap(lopService.findOne(diem.getLopid()).getHocsinhs());
            Integer diem_mieng = 0;
            LoaiDiem l_mieng = null;
            Integer diem_15p = 0;
            LoaiDiem l_15p = null;
            Integer diem_1t = 0;
            LoaiDiem l_1t = null;
            Integer diem_GK = 0;
            LoaiDiem l_gk = null;
            Integer diem_CK = 0;
            LoaiDiem l_ck = null;
            for(LoaiDiem ld:loaiDiemService.findAll())
            {
                if (ld.getTenloai().equals("DiemMieng"))
                {
                    diem_mieng = ld.getMaloai();
                    l_mieng = ld;
                }
                if (ld.getTenloai().equals("Diem15p"))
                {
                    diem_15p = ld.getMaloai();
                    l_15p = ld;
                }
                if (ld.getTenloai().equals("Diem1t"))
                {
                    diem_1t = ld.getMaloai();
                    l_1t = ld;
                }
                if (ld.getTenloai().equals("DiemGK"))
                {
                    diem_GK = ld.getMaloai();
                    l_gk = ld;
                }
                if (ld.getTenloai().equals("DiemCK"))
                {
                    diem_CK = ld.getMaloai();
                    l_ck = ld;
                }
            }

            try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String row = "";
                csvReader.readLine();
                Integer hocky = diem.getHocky();
                MonHoc monhoc = monhocService.findOne(diem.getMamh());
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",");
                    if(m_hs.containsKey(data[2]) == true)
                    {
                        Integer mahs = m_hs.get(data[2]).getMasv();
                        HocSinh hs = hocsinhService.findOne(mahs);
                        if(diemService.findOne(new DiemPK(mahs, diem.getMamh(), diem_mieng, hocky)) == null)
                        {
                            Diem r_diem = new Diem();
                            r_diem.setDiemPK(new DiemPK());
                            r_diem.setHocsinh(hocsinhService.findOne(mahs));
                            r_diem.setMonhoc(monhocService.findOne(diem.getMamh()));
                            r_diem.setLoaidiem(loaiDiemService.findOne(diem_mieng));
                            r_diem.getDiemPK().setHocky(hocky);
                            r_diem.setDiem(Float.parseFloat(data[4]));
                            diemService.save(r_diem);
                            monhoc.getDiems().add(r_diem);
                            monhocService.update(monhoc);
                            l_mieng.getDiems().add(r_diem);
                            loaiDiemService.update(l_mieng);
                            hs.getDiems().add(r_diem);
                            hocsinhService.update(hs);
                        }else{
                            Diem r_diem = diemService.findOne(new DiemPK(mahs, diem.getMamh(), diem_mieng, hocky));
                            r_diem.setDiem(Float.parseFloat(data[4]));
                            diemService.save(r_diem);
                        }

                        if(diemService.findOne(new DiemPK(mahs, diem.getMamh(), diem_15p, hocky)) == null)
                        {
                            Diem r_diem = new Diem();
                            r_diem.setDiemPK(new DiemPK());
                            r_diem.setHocsinh(hocsinhService.findOne(mahs));
                            r_diem.setMonhoc(monhocService.findOne(diem.getMamh()));
                            r_diem.setLoaidiem(loaiDiemService.findOne(diem_15p));
                            r_diem.getDiemPK().setHocky(hocky);
                            r_diem.setDiem(Float.parseFloat(data[5]));
                            diemService.save(r_diem);
                            monhoc.getDiems().add(r_diem);
                            monhocService.update(monhoc);
                            l_15p.getDiems().add(r_diem);
                            loaiDiemService.update(l_15p);
                            hs.getDiems().add(r_diem);
                            hocsinhService.update(hs);
                        }else{
                            Diem r_diem = diemService.findOne(new DiemPK(mahs, diem.getMamh(), diem_15p, hocky));
                            r_diem.setDiem(Float.parseFloat(data[5]));
                            diemService.save(r_diem);
                        }

                        if(diemService.findOne(new DiemPK(mahs, diem.getMamh(), diem_1t, hocky)) == null)
                        {
                            Diem r_diem = new Diem();
                            r_diem.setDiemPK(new DiemPK());
                            r_diem.setHocsinh(hocsinhService.findOne(mahs));
                            r_diem.setMonhoc(monhocService.findOne(diem.getMamh()));
                            r_diem.setLoaidiem(loaiDiemService.findOne(diem_1t));
                            r_diem.getDiemPK().setHocky(hocky);
                            r_diem.setDiem(Float.parseFloat(data[6]));
                            diemService.save(r_diem);
                            monhoc.getDiems().add(r_diem);
                            monhocService.update(monhoc);
                            l_1t.getDiems().add(r_diem);
                            loaiDiemService.update(l_1t);
                            hs.getDiems().add(r_diem);
                            hocsinhService.update(hs);
                        }else{
                            Diem r_diem = diemService.findOne(new DiemPK(mahs, diem.getMamh(), diem_1t, hocky));
                            r_diem.setDiem(Float.parseFloat(data[6]));
                            diemService.save(r_diem);
                        }


                        if(diemService.findOne(new DiemPK(mahs, diem.getMamh(), diem_GK, hocky)) == null)
                        {
                            Diem r_diem = new Diem();
                            r_diem.setDiemPK(new DiemPK());
                            r_diem.setHocsinh(hocsinhService.findOne(mahs));
                            r_diem.setMonhoc(monhocService.findOne(diem.getMamh()));
                            r_diem.setLoaidiem(loaiDiemService.findOne(diem_GK));
                            r_diem.getDiemPK().setHocky(hocky);
                            r_diem.setDiem(Float.parseFloat(data[7]));
                            diemService.save(r_diem);
                            monhoc.getDiems().add(r_diem);
                            monhocService.update(monhoc);
                            l_gk.getDiems().add(r_diem);
                            loaiDiemService.update(l_gk);
                            hs.getDiems().add(r_diem);
                            hocsinhService.update(hs);
                        }else{
                            Diem r_diem = diemService.findOne(new DiemPK(mahs, diem.getMamh(), diem_GK, hocky));
                            r_diem.setDiem(Float.parseFloat(data[7]));
                            diemService.save(r_diem);
                        }


                        if(diemService.findOne(new DiemPK(mahs, diem.getMamh(), diem_CK, hocky)) == null)
                        {
                            Diem r_diem = new Diem();
                            r_diem.setDiemPK(new DiemPK());
                            r_diem.setHocsinh(hocsinhService.findOne(mahs));
                            r_diem.setMonhoc(monhocService.findOne(diem.getMamh()));
                            r_diem.setLoaidiem(loaiDiemService.findOne(diem_CK));
                            r_diem.getDiemPK().setHocky(hocky);
                            r_diem.setDiem(Float.parseFloat(data[8]));
                            diemService.save(r_diem);
                            monhoc.getDiems().add(r_diem);
                            monhocService.update(monhoc);
                            l_ck.getDiems().add(r_diem);
                            loaiDiemService.update(l_ck);
                            hs.getDiems().add(r_diem);
                            hocsinhService.update(hs);
                        }else{
                            Diem r_diem = diemService.findOne(new DiemPK(mahs, diem.getMamh(), diem_CK, hocky));
                            r_diem.setDiem(Float.parseFloat(data[8]));
                            diemService.save(r_diem);
                        }

                    }
                }
                csvReader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return "redirect:/diem/" + Integer.toString(diem.getLopid()) + "/detail";
    }
    @GetMapping("/diem/detail/{id}/diem_hocsinh")
    public String diem_hocsinh_ct(@PathVariable("id") Integer id, Model model){
        HocSinh hs = hocsinhService.findOne(id);
        List<Diem> hs_diem = diemService.findByHsId(id);
        ViewBangDiem wrapper = new ViewBangDiem();//hk1
        wrapper.setHocsinh(hs);
        wrapper.setHocky(1);
        wrapper.setLop(hs.getLop());

        ViewBangDiem drapper = new ViewBangDiem();//hk2
        drapper.setHocsinh(hs);
        drapper.setHocky(2);
        drapper.setLop(hs.getLop());
        for(MonHoc mh: monhocService.findAll())
        {
            KetQua kq = new KetQua();
            kq.setMonhoc(mh);
            wrapper.getKetQuas().add(kq);
            KetQua kq2 = new KetQua();
            kq2.setMonhoc(mh);
            drapper.getKetQuas().add(kq2);
        }
        for(KetQua re: wrapper.getKetQuas())
        {
            for(Diem d : hs_diem)
            {
                if (re.getMonhoc().getMamh().equals((d.getMonhoc().getMamh()))) {
                    if (d.getLoaidiem().getTenloai().equals("DiemMieng") && d.getDiemPK().getHocky().equals(1))
                        re.setDiemMieng(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("Diem15p") && d.getDiemPK().getHocky().equals(1))
                        re.setDiem15p(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("Diem1t") && d.getDiemPK().getHocky().equals(1))
                        re.setDiem1t(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("DiemGK") && d.getDiemPK().getHocky().equals(1))
                        re.setDiemGK(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("DiemCK") && d.getDiemPK().getHocky().equals(1))
                        re.setDiemCK(d.getDiem());
                }
            }
            re.setDiemTB(re.tinhDiemTB((List<LoaiDiem>) loaiDiemService.findAll()));
        }
        wrapper.setDiemHK(wrapper.tinhDiemHK());

        for(KetQua re : drapper.getKetQuas())
        {
            for(Diem d : hs_diem)
            {
                if (re.getMonhoc().getMamh().equals((d.getMonhoc().getMamh()))) {
                    if (d.getLoaidiem().getTenloai().equals("DiemMieng") && d.getDiemPK().getHocky().equals(2))
                        re.setDiemMieng(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("Diem15p") && d.getDiemPK().getHocky().equals(2))
                        re.setDiem15p(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("Diem1t") && d.getDiemPK().getHocky().equals(2))
                        re.setDiem1t(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("DiemGK") && d.getDiemPK().getHocky().equals(2))
                        re.setDiemGK(d.getDiem());
                    if (d.getLoaidiem().getTenloai().equals("DiemCK") && d.getDiemPK().getHocky().equals(2))
                        re.setDiemCK(d.getDiem());
                }
            }
            re.setDiemTB(re.tinhDiemTB((List<LoaiDiem>) loaiDiemService.findAll()));
        }
        drapper.setDiemHK(drapper.tinhDiemHK());
        float diemBQ = -1;
        if (wrapper.getDiemHK() != -1 && drapper.getDiemHK() != -1)
        {
            diemBQ = (wrapper.getDiemHK() + (drapper.getDiemHK() * 2))/3;
        }
        model.addAttribute("diemBQ", diemBQ);
        model.addAttribute("wrapper", wrapper);
        model.addAttribute("drapper", drapper);
        return "diem/bang_diem";
    }
    @GetMapping("/diem/{id}/thongke")
    public String diem_thongke(@PathVariable("id") Integer id, RedirectAttributes redirect, Model model)
    {
        ViewThongKe vtk = new ViewThongKe();
        vtk.setLop_id(id);
        model.addAttribute("vtk", vtk);
        return "diem/diem_thongke";
    }
    @PostMapping("/diem/thongke/kq_thongke")
    public String kq_thongke(@Valid ViewThongKe vtk,  RedirectAttributes redirect, Model model){
        List<HocSinh> hocsinhs = new ArrayList<HocSinh>();
        hocsinhs = lopService.findOne(vtk.getLop_id()).getHocsinhs();
        for(HocSinh hs : hocsinhs)
        {
            List<Diem> hs_diem = diemService.findByHsId(hs.getMasv());
            ViewBangDiem hk1 = new ViewBangDiem();//hk1
            hk1.setHocsinh(hs);
            hk1.setHocky(1);
            hk1.setLop(hs.getLop());
            hk1.tinhDiemAllMh((List<MonHoc>)monhocService.findAll(), hs_diem,(List<LoaiDiem>)loaiDiemService.findAll());
            vtk.getHs_hk1().add(hk1);

            ViewBangDiem hk2 = new ViewBangDiem();//hk2
            hk2.setHocsinh(hs);
            hk2.setHocky(2);
            hk2.setLop(hs.getLop());
            hk2.tinhDiemAllMh((List<MonHoc>)monhocService.findAll(), hs_diem,(List<LoaiDiem>)loaiDiemService.findAll());
            vtk.getHs_hk2().add(hk2);
        }
        List<KetQuaThongKe> l = vtk.thongke();
        if (l.size() == 0)
            l = null;
        model.addAttribute("lop_id", vtk.getLop_id());
        model.addAttribute("l", l);
        return "diem/kq_thongke";
    }
}
