package com.example.demo.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.HocSinh;
import com.example.demo.entity.HocSinhWithSelection;
import com.example.demo.entity.HocSinhWithSelectionListWrapper;
import com.example.demo.entity.Lop;
import com.example.demo.service.HocSinhService;
import com.example.demo.service.LopService;

@Controller
public class LopController {
	@Autowired
	private LopService lopService;
	@Autowired
	private HocSinhService hocsinhService;

	@GetMapping("/lop")
	public String list(Model model, HttpServletRequest request
			, RedirectAttributes redirect) {
		request.getSession().setAttribute("loplist", null);

		if(model.asMap().get("success") != null)
			redirect.addFlashAttribute("success",model.asMap().get("success").toString());
		return "redirect:/lop/page/1";
	}
	@GetMapping("/lop/page/{pageNumber}")
	public String showLopPage(HttpServletRequest request,
								  @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("loplist");
		int pagesize = 4;
		List<Lop> list =(List<Lop>) lopService.findAll();
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("loplist", pages);
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
		String baseUrl = "/lop/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("lops", pages);

		return "lop/list_lop_paging";
	}

	@GetMapping("/lop/add")
	public String add(Model model)
	{
		model.addAttribute("lop", new Lop());
		return "lop/form_lop";
	}
	@PostMapping("/lop/save")
	public String save(@Valid Lop lop, BindingResult result, RedirectAttributes redirect) {

		lopService.save(lop);

	    return "redirect:/lop";
	}
	@GetMapping("/lop/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirect) {
		Lop lop = lopService.findOne(id);
		if (lop.getHocsinhs().size() != 0)
			lop.xoaLop();
		if(lop.getGiaoviens().size() != 0)
			lop.xoaGiaoVien();
		lopService.delete(id);
		return "redirect:/lop";
	}
	@GetMapping("/lop/{id}/edit")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("lop", lopService.findOne(id));
		return "lop/form_lop";
	}
	@GetMapping("/lop/search")
	public String search(@RequestParam("term") String term, Model model) {
	    if (StringUtils.isEmpty(term)) {
	        return "redirect:/lop";
	    }
	    model.addAttribute("lops", lopService.search(term));
	    return "lop/list_lop";
	}
	
	@GetMapping("lop/{id}/detail")
	public String detail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("lop", lopService.findOne(id));
		return "lop/lop_detail";
	}
	@GetMapping("/lop/detail/{id}/add_hocsinh")
	public String add_hs(@PathVariable("id") Integer id, Model model)
	{
		ArrayList<HocSinhWithSelection> hsws = new ArrayList<HocSinhWithSelection>();
		for (HocSinh s : hocsinhService.findAll())
		{
			if(s.getLop() == null) {
				hsws.add(new HocSinhWithSelection(false, s));
			}
		}
		HocSinhWithSelectionListWrapper wrapper = new HocSinhWithSelectionListWrapper();
		wrapper.setHocsinhlist(hsws);
		wrapper.setId_lop(id);
		model.addAttribute("wrapper", wrapper);
		return "lop/form_hs_lop";
	}
	@PostMapping("/lop/detail/save_hocsinh")
	public String save_hocsinh(@ModelAttribute HocSinhWithSelectionListWrapper wrapper, BindingResult result, RedirectAttributes redirect) {
		Lop tmp = lopService.findOne(wrapper.getId_lop());
		List<HocSinh> init = new ArrayList<>();
		
		for(HocSinhWithSelection s : wrapper.getHocsinhlist())
		{
			if (s.getSelected() == true) {
				HocSinh tkl = hocsinhService.findOne(s.getHocsinh().getMasv()); 
				init.add(tkl);			
			}
		}
		for(HocSinh s : init) {
			tmp.addHocsinh(s);
		}
		lopService.update(tmp);
	    return "redirect:/lop" + "/" + Integer.toString(wrapper.getId_lop()) + "/" + "detail";
	}
	@GetMapping("/lop/detail/{id_hs}/{id_class}/delete_hs")
	public String delete_hs_from_lop(@PathVariable("id_hs") Integer id_hs,
									 @PathVariable("id_class")Integer id_class, RedirectAttributes redirect) {
		HocSinh hs = hocsinhService.findOne(id_hs);
		Lop lop = lopService.findOne(id_class);
		lop.removeHocsinh(hs);
		lopService.update(lop);
		return "redirect:/lop/" + id_class.toString() + "/detail";
	}

	@GetMapping("/lop/detail/{id}/import_hs")
	public String import_hs(@PathVariable("id") Integer id, Model model)
	{
		Lop lop = lopService.findOne(id);
		model.addAttribute("lop_id", lop);
		return "lop/form_hs_import";
	}@PostMapping("/lop/detail/save_import")
	public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model, @ModelAttribute Lop l, RedirectAttributes redirect) {

		if (file.isEmpty()) {

		} else {
			List<HocSinh> personList = new ArrayList<HocSinh>();
			try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
				String row = "";
				csvReader.readLine();
				Lop lop = lopService.findOne(l.getMalop());
				List<HocSinh> hocsinhs = new ArrayList<HocSinh>();
				while ((row = csvReader.readLine()) != null) {
					String[] data = row.split(",");
					HocSinh hs = new HocSinh();
					hs.setTen(data[0]);
					hs.setGioitinh(data[2]);
					String date = Integer.toString(hocsinhService.create_random(1,30)) + '/' + Integer.toString(hocsinhService.create_random(1,12)) + '/' + data[3];
					hs.setNgaysinh(new SimpleDateFormat("dd/MM/yyyy").parse(date));
					hs.setDiachi(data[4].concat("," + data[5]));
					hocsinhs.add(hs);
					lop.addHocsinh(hs);
				}
				hocsinhService.saveAll(hocsinhs);
				lopService.update(lop);
				csvReader.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
		return "redirect:/lop" + "/" + Integer.toString(l.getMalop()) + "/" + "detail";
	}
	@GetMapping("/lop/detail/{id}/export_hs")
	public String export_hs(@PathVariable("id") Integer id, Model model)
	{
		Lop lop = lopService.findOne(id);
		List<HocSinh> l_hs = lop.getHocsinhs();
		try{
            FileOutputStream out = new FileOutputStream("C://Users//pc//Desktop//".concat(lop.getTenlop().concat(".csv")));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            Integer stt = 1;
            bw.write("STT,TEN,GIOI TINH,NGAY SINH, DIA CHI");

            bw.write("\n");
            for(HocSinh hs : l_hs)
            {
                String ngaysinh = "";
                if(hs.getNgaysinh() != null){
                    ngaysinh = hs.getNgaysinh().toString();
                }else{
                    ngaysinh = "00/00/0000";
                }
                String data = stt.toString() + ',' + hs.getTen() + "," + hs.getGioitinh() + "," + ngaysinh + "," + hs.getDiachi().replaceAll("^\"|\"$", "");

                stt += 1;
                bw.write(data);
                bw.write("\n");
            }
            bw.close();
            out.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "redirect:/lop" + "/" + Integer.toString(id) + "/" + "detail";
	}
}
