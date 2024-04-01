package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.demo.entity.Form_HocSinh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.HocSinh;
import com.example.demo.service.HocSinhService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
public class HocSinhController {
	@Autowired
	private HocSinhService hocsinhService;

	@GetMapping("/hocsinh")
	public String list(Model model, HttpServletRequest request
			, RedirectAttributes redirect) {
		request.getSession().setAttribute("hocsinhlist", null);

		if(model.asMap().get("success") != null)
			redirect.addFlashAttribute("success",model.asMap().get("success").toString());
		return "redirect:/hocsinh/page/1";
	}
	@GetMapping("/hocsinh/page/{pageNumber}")
	public String showHocsinhPage(HttpServletRequest request,
								   @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("hocsinhlist");
		int pagesize = 8;
		List<HocSinh> list =(List<HocSinh>) hocsinhService.findAll();
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
		String baseUrl = "/hocsinh/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("hocsinhs", pages);

		return "hocsinh/list_hs_paging";
	}
	@GetMapping("/hocsinh/add")
	public String add(Model model)
	{
		Form_HocSinh hocsinh = new Form_HocSinh();
		hocsinh.setHocsinh(new HocSinh());
		model.addAttribute("student", hocsinh);
		return "hocsinh/form_hs";
	}
	@PostMapping("/hocsinh/save")
	public String save(@Valid Form_HocSinh hocsinh, BindingResult result, RedirectAttributes redirect) {
		if (hocsinh.getDate()== ""){
			if(hocsinh.getHocsinh().getMasv() == null)
			{
				hocsinh.getHocsinh().setNgaysinh(new Date());
			}else{
				hocsinh.getHocsinh().setNgaysinh(hocsinhService.findOne(hocsinh.getHocsinh().getMasv()).getNgaysinh());
			}
		}else{
			try {
				String date = hocsinh.getDate();  ;
				Date d = new SimpleDateFormat("dd/MM/yyyy").parse(date);
				hocsinh.getHocsinh().setNgaysinh(d);
			}catch(Exception e){}
		}
		if(hocsinh.getHocsinh().getGioitinh().equals(""))
		{
			if(hocsinh.getHocsinh().getMasv() == null)
			{
				hocsinh.getHocsinh().setGioitinh("nam");
			}else{
				hocsinh.getHocsinh().setGioitinh(hocsinhService.findOne(hocsinh.getHocsinh().getMasv()).getGioitinh());
			}
		}
		if(hocsinh.getHocsinh().getMasv() != null)
		{
			Integer id = hocsinh.getHocsinh().getMasv();
			hocsinh.getHocsinh().setChucvu(hocsinhService.findOne(id).getChucvu());
			hocsinh.getHocsinh().setLop(hocsinhService.findOne(id).getLop());
			hocsinh.getHocsinh().setDiems(hocsinhService.findOne(id).getDiems());
		}
	    hocsinhService.save(hocsinh.getHocsinh());
		redirect.addFlashAttribute("success", "Lưu thành công!");
	    return "redirect:/hocsinh";
	}
	@GetMapping("/hocsinh/{id}/edit")
	public String edit(@PathVariable("id") Integer id, Model model) {
		Form_HocSinh f_hocsinh = new Form_HocSinh();
		f_hocsinh.setHocsinh(hocsinhService.findOne(id));
		model.addAttribute("student", f_hocsinh);
		return "hocsinh/form_hs";
	}
	@GetMapping("/hocsinh/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirect) {
		hocsinhService.delete(id);
		redirect.addFlashAttribute("success", "Xoá thành công!");
		return "redirect:/hocsinh";
	}
	@GetMapping("/hocsinh/search")
	public String search(@RequestParam("term") String term, Model model) {
	    if (StringUtils.isEmpty(term)) {
	        return "redirect:/hocsinh";
	    }
	    model.addAttribute("students", hocsinhService.search(term));
	    return "hocsinh/list_hs";
	}
	@GetMapping("hocsinh/{id}/detail")
	public String detail(@PathVariable("id") Integer id, Model model){
		return "hocsinh/hocsinh_detail";
	}
}
