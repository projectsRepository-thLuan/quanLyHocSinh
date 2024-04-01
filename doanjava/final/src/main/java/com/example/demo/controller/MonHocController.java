package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.GiaoVien;
import com.example.demo.entity.MonHoc;
import com.example.demo.service.GiaoVienService;
import com.example.demo.service.MonHocService;

@Controller
public class MonHocController {
	@Autowired
	private MonHocService monhocService;
	@Autowired
	private GiaoVienService giaovienService;


	@GetMapping("/monhoc")
	public String list(Model model, HttpServletRequest request
			, RedirectAttributes redirect) {
		request.getSession().setAttribute("monhoclist", null);

		if(model.asMap().get("success") != null)
			redirect.addFlashAttribute("success",model.asMap().get("success").toString());
		return "redirect:/monhoc/page/1";
	}
	@GetMapping("/monhoc/page/{pageNumber}")
	public String showMonHocPage(HttpServletRequest request,
								  @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("monhoclist");
		int pagesize = 5;
		List<MonHoc> list =(List<MonHoc>) monhocService.findAll();
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("monhoclist", pages);
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
		String baseUrl = "/monhoc/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("monhocs", pages);

		return "monhoc/list_mh_paging";
	}

	@GetMapping("/monhoc/add")
	public String add(Model model) {
		model.addAttribute("monhoc", new MonHoc());
		return "monhoc/form_mh";
	}
	@PostMapping("/monhoc/save")
	public String save(@Valid MonHoc monhoc, RedirectAttributes redirect) {
		if(monhoc.getMamh() != null)
		{
			monhoc.setGiaoviens(monhocService.findOne(monhoc.getMamh()).getGiaoviens());
			monhoc.setDiems(monhocService.findOne(monhoc.getMamh()).getDiems());
		}
		monhocService.save(monhoc);
	    return "redirect:/monhoc";
	}
	@GetMapping("/monhoc/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirect) {
		MonHoc mh = monhocService.findOne(id);
		mh.xoaMonHoc();
		monhocService.delete(id);
		return "redirect:/monhoc";
	}
	@GetMapping("/monhoc/{id}/edit")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("monhoc", monhocService.findOne(id));
		return "monhoc/form_mh";
	}
	@GetMapping("/monhoc/{id}/detail")
	public String detail(@PathVariable("id") Integer id, Model model) {
		MonHoc mh = monhocService.findOne(id);
		List<GiaoVien> gv = mh.getGiaoviens();
		model.addAttribute("giaoviens", gv);
		model.addAttribute("monhoc", mh);
		return "monhoc/monhoc_detail";
	}
}
