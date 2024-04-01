package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.demo.entity.*;
import com.example.demo.service.*;
import com.example.demo.support.FileSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@Controller
public class GiaoVienController {
	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
	@Autowired
	private GiaoVienService giaovienService;
	@Autowired
	private LopService lopService;
	@Autowired
	private MonHocService monhocService;
	@Autowired
	private ChucVuService chucvuService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/giaovien")
	public String list(Model model, HttpServletRequest request
			, RedirectAttributes redirect) {
		request.getSession().setAttribute("giaovienlist", null);

		if(model.asMap().get("success") != null)
			redirect.addFlashAttribute("success",model.asMap().get("success").toString());
		return "redirect:/giaovien/page/1";
	}
	@GetMapping("/giaovien/page/{pageNumber}")
	public String showGiaoVienPage(HttpServletRequest request,
								  @PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("giaovienlist");
		int pagesize = 8;
		List<GiaoVien> list =(List<GiaoVien>) giaovienService.findAll();
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("giaovienlist", pages);
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
		String baseUrl = "/giaovien/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("giaoviens", pages);

		return "giaovien/list_gv_paging";
	}

	@GetMapping("/giaovien/add")
	public String add(Model model)
	{
		GiaoVien gv = new GiaoVien();
		Form_GiaoVien f_gv = new Form_GiaoVien();
		f_gv.setGiaovien(gv);
		model.addAttribute("giaovien", f_gv);
		return "giaovien/form_gv";
	}
	@PostMapping("/giaovien/save")
	public String save(@Valid Form_GiaoVien gv, BindingResult result, RedirectAttributes redirect,
					   @RequestParam("file") MultipartFile file) {

		if (gv.getGiaovien().getMagv() == null)
		{
			for(ChucVu cv : chucvuService.findAll()) {
				if (cv.getTencv().equals("Teacher")) {
                    gv.getGiaovien().setChucvu(cv);
					break;
				}
			}
			List<Role> roles = new ArrayList<>();
			roles.add(roleService.findByName("ROLE_MEMBER"));
            gv.getGiaovien().setRoles(roles);

		}else{
			GiaoVien gv_old = giaovienService.findOne(gv.getGiaovien().getMagv());
            gv.getGiaovien().setRoles(gv_old.getRoles());
            gv.getGiaovien().setChucvu(gv_old.getChucvu());
		}
        if (gv.getDate().equals("")){
            if(gv.getGiaovien().getMagv() == null)
            {
                gv.getGiaovien().setNgaysinh(new Date());
            }else{
                gv.getGiaovien().setNgaysinh(giaovienService.findOne(gv.getGiaovien().getMagv()).getNgaysinh());
            }
        }else{
            try {
                String date = gv.getDate();  ;
                Date d = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                gv.getGiaovien().setNgaysinh(d);
            }catch(Exception e){}
        }

		if (gv.getGiaovien().getPassword().equals("") != true)
            gv.getGiaovien().setPassword(passwordEncoder.encode(gv.getGiaovien().getPassword()));
		else{
			if(gv.getGiaovien().getMagv() == null)
			{
                gv.getGiaovien().setPassword(passwordEncoder.encode("123456"));
			}else{
                gv.getGiaovien().setPassword(giaovienService.findOne(gv.getGiaovien().getMagv()).getPassword());
			}
		}

		String file_n = null;
		if (file.isEmpty()) {
			if (gv.getGiaovien().getMagv() == null)
				gv.getGiaovien().setImage_path(gv.getGiaovien().getImage_path());
			else
				gv.getGiaovien().setImage_path(giaovienService.findOne(gv.getGiaovien().getMagv()).getImage_path());
		}else{
			Path fileNameAndPath_t = Paths.get(uploadDirectory, file.getOriginalFilename());
			Path fileNameAndPath = null;
			if(gv.getGiaovien().getMagv() != null)
			{
				String[] tmp = file.getContentType().split("/");
				fileNameAndPath = fileNameAndPath_t.getParent().resolve(gv.getGiaovien().getMagv().toString().concat("." + tmp[tmp.length-1]));
				file_n = gv.getGiaovien().getMagv().toString().concat("." + tmp[tmp.length-1]);
			}else{
				fileNameAndPath = fileNameAndPath_t;
				file_n = file.getOriginalFilename();
			}
			try{
				Files.write(fileNameAndPath, file.getBytes());
			}catch (Exception ex){ex.printStackTrace();}
			gv.getGiaovien().setImage_path(file_n);
		}

		giaovienService.save(gv.getGiaovien());

	    return "redirect:/giaovien";
	}
	@GetMapping("/giaovien/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirect) {

		String path = uploadDirectory + "/" + giaovienService.findOne(id).getImage_path();
		if(FileSupport.checkFileExist(path)==true){

			FileSupport.delete_file(path);
		}
		giaovienService.delete(id);
		return "redirect:/giaovien";
	}
	@GetMapping("/giaovien/{id}/edit")
	public String edit(@PathVariable("id") Integer id, Model model) {
	    Form_GiaoVien f_gv = new Form_GiaoVien();
	    f_gv.setGiaovien(giaovienService.findOne(id));
	    if (giaovienService.findOne(id).getNgaysinh() != null){
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			f_gv.setDate(dateFormat.format(giaovienService.findOne(id).getNgaysinh()));
		}
		model.addAttribute("giaovien", f_gv);
		return "giaovien/form_gv";
	}
	@GetMapping("/giaovien/search")
	public String search(@RequestParam("term") String term, Model model) {
	    if (StringUtils.isEmpty(term)) {
	        return "redirect:/giaovien";
	    }
	    model.addAttribute("giaoviens", giaovienService.search(term));
	    return "giaovien/list_gv";
	}
	@GetMapping("giaovien/{id}/detail")
	public String detail(@PathVariable("id") Integer id, Model model) {
		ViewGiaoVien lop_mh = new ViewGiaoVien();
		lop_mh.setLops(giaovienService.findOne(id).getLops());
		lop_mh.setMonhoc(giaovienService.findOne(id).getMonhoc());
		lop_mh.setId_gv(id);
		model.addAttribute("tengv", giaovienService.findOne(id).getHoten());
		model.addAttribute("hinhanh", giaovienService.findOne(id).getImage_path());
		model.addAttribute("lop_mh", lop_mh);
		model.addAttribute("chucvu", giaovienService.findOne(id).getChucvu());
		return "giaovien/giaovien_detail";
	}
	@GetMapping("/giaovien/detail/{id}/phancong_monhoc")
	public String phancong_monhoc(@PathVariable("id") Integer id, Model model) {
		List<MonHoc> monhocs = new ArrayList<MonHoc>();
		monhocs = (List<MonHoc>) monhocService.findAll();
		GiaoVien gv = giaovienService.findOne(id);
		ArrayList<MonHocWithSelection> a_mh = new ArrayList<>();
		MonHocWithSelectionListWrapper wrapper = new MonHocWithSelectionListWrapper();
		if(gv.getMonhoc() == null) {
			for(MonHoc m : monhocs) {
				a_mh.add(new MonHocWithSelection(false, m));
			}
		}else {
			for(MonHoc m : monhocs) {
				if(gv.getMonhoc().getMamh() != m.getMamh())
					a_mh.add(new MonHocWithSelection(false, m));
			}
		}
		wrapper.setId_gv(id);
		wrapper.setList_monhoc(a_mh);
		model.addAttribute("wrapper", wrapper);
		return "giaovien/form_gv_mh";
	}
	@PostMapping("/giaovien/detail/save_phancong_monhoc")
	public String save_phancong_monhoc(@ModelAttribute MonHocWithSelectionListWrapper wrapper) {
		GiaoVien gv = giaovienService.findOne(wrapper.getId_gv());

		MonHoc mh = new MonHoc();
		for(MonHocWithSelection m : wrapper.getList_monhoc()) {
			if (m.getSelected() == true) {
				mh = monhocService.findOne(m.getMonhoc().getMamh());
				break;
			}
		}
		gv.addMonHoc(mh);
		giaovienService.update(gv);
		monhocService.update(mh);
		return "redirect:/giaovien/"+ Integer.toString(wrapper.getId_gv()) + "/detail";
	}
	@GetMapping("giaovien/detail/{id}/phancong_lop")
	public String phancong_lop(@PathVariable("id") Integer id, Model model) {
		List<Lop> lops = new ArrayList<Lop>();
		lops = (List<Lop>) lopService.findAll();
		GiaoVien gv = giaovienService.findOne(id);
		ArrayList<GiaoVienWithSelection> a_lop = new ArrayList<>();
		GiaoVienWithSelectionListWrapper wrapper = new GiaoVienWithSelectionListWrapper();
		if (gv.getLops().size() == 0)
		{
			for (Lop l : lops) {
				a_lop.add(new GiaoVienWithSelection(false, l));
			}
			
		}else {
			for(Lop l : lops) {
				if (lopService.compare_lop(l, gv.getLops()) == false){
					a_lop.add(new GiaoVienWithSelection(false, l));
				}
			}
		}
		wrapper.setList_lop(a_lop);
		wrapper.setId_gv(id);
		model.addAttribute("wrapper", wrapper);
		return "giaovien/form_gv_lop";
	}
	@PostMapping("/giaovien/detail/save_phancong")
	public String save_phancong(@ModelAttribute GiaoVienWithSelectionListWrapper wrapper){
		GiaoVien gv = giaovienService.findOne(wrapper.getId_gv());
		List<Lop> lops = new ArrayList<Lop>();
		for(GiaoVienWithSelection g : wrapper.getList_lop()) {
			if(g.getSelected() == true) {
				Lop tmp = lopService.findOne(g.getLop().getMalop());
				lops.add(tmp);
			}
		}
		for(Lop l : lops) {
			gv.addLop(l);
		}
		giaovienService.update(gv);
		return "redirect:/giaovien/"+ Integer.toString(wrapper.getId_gv()) + "/detail";
	}
	@GetMapping("/giaovien/detail/{id_gv}/{id_lop}/delete_pc_lop")
	public String delete_pc_lop(@PathVariable("id_gv") Integer id_gv,
			 					@PathVariable("id_lop")Integer id_lop, RedirectAttributes redirect) {
		GiaoVien gv = giaovienService.findOne(id_gv);
		Lop lop = lopService.findOne(id_lop);
		gv.xoaLop(lop);
		giaovienService.update(gv);
		lopService.update(lop);
		return "redirect:/giaovien/" + Integer.toString(id_gv) + "/detail";
	}
}
