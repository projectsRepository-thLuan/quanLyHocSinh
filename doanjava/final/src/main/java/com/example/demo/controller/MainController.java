package com.example.demo.controller;

import com.example.demo.entity.Form_GiaoVien;
import com.example.demo.entity.GiaoVien;
import com.example.demo.entity.ViewGiaoVien;
import com.example.demo.entity.WebUtils;
import com.example.demo.service.GiaoVienService;
import com.example.demo.service.RoleService;
import com.example.demo.support.FileSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    @Autowired
    private GiaoVienService giaoVienService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleRepository;
    @GetMapping("/login")
    public String login(){
        return "main/login";
    }
    @GetMapping(value = {"/", "/index"})
    public String index(){
        return "main/index";
    }
    @RequestMapping("/giaovieninfo")
    public String userinfo(Model model, Principal principal)
    {
        int auth = 0;
        User u = (User) ((Authentication) principal).getPrincipal();
        List<GrantedAuthority> gr;
        if (u.getAuthorities() instanceof List)
            gr = (List)u.getAuthorities();
        else
            gr = new ArrayList(u.getAuthorities());
        for(GrantedAuthority g: gr){
            auth += 1;
        }
        GiaoVien gv = giaoVienService.findByUsername(principal.getName());
        model.addAttribute("auth", auth);
        String userName = principal.getName();
        model.addAttribute("userInfo", userName);
        model.addAttribute("giaovien", gv);
        return "main/giaovieninfo";
    }
    @GetMapping("/quantri_admin")
    public String quantri_admin(Model model){
        return "main/quantri_admin";
    }
    @GetMapping(value= {"/logout", "/logout"})
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null)
        {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
    @GetMapping("/403")
    public String accessDenied(Model model, Principal principal)
    {
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loginedUser);
            model.addAttribute("userInfo", userInfo);
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
        }
        return "403";
    }
    @GetMapping("/thaydoi_giaovien/{id}")
    public String thaydoi_giaovien(@PathVariable("id") Integer id, Model model){
        GiaoVien gv = giaoVienService.findOne(id);
        Form_GiaoVien f_gv = new Form_GiaoVien();
        if (gv.getNgaysinh() != null){
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            f_gv.setDate(dateFormat.format(gv.getNgaysinh()));
        }
        f_gv.setGiaovien(gv);
        model.addAttribute("giaovien", f_gv);
        return "main/thaydoi_giaovien";
    }
    @PostMapping("/save_thaydoi")
    public String save_thaydoi(@Valid Form_GiaoVien gv, RedirectAttributes redirect,
                               @RequestParam("file") MultipartFile file){
        String file_n = null;
        GiaoVien giaovien = giaoVienService.findOne(gv.getGiaovien().getMagv());
        if (file.isEmpty()) {
            giaovien.setImage_path(giaovien.getImage_path());
        }else{
            Path fileNameAndPath_t = Paths.get(uploadDirectory, file.getOriginalFilename());
            String[] tmp = file.getContentType().split("/");
            Path fileNameAndPath = fileNameAndPath_t.getParent().resolve(gv.getGiaovien().getMagv().toString().concat("." + tmp[tmp.length-1]));
            file_n = gv.getGiaovien().getMagv().toString().concat("." + tmp[tmp.length-1]);
            try{
                Files.write(fileNameAndPath, file.getBytes());
            }catch (Exception ex){ex.printStackTrace();}
            giaovien.setImage_path(file_n);
        }

        giaovien.setHoten(gv.getGiaovien().getHoten());
        giaovien.setDiachi(gv.getGiaovien().getDiachi());
        if (gv.getDate().equals("") == false)
        {
            try{
                giaovien.setNgaysinh(new SimpleDateFormat("dd/MM/yyyy").parse(gv.getDate()));
            }catch(Exception ex){}
        }else{

        }
        giaoVienService.save(giaovien);
        return "redirect:/giaovieninfo";
    }
    @GetMapping("/quantri_thanhvien")
    public String quantri_thanhvien()
    {
        return "main/quantri_thanhvien";
    }
}


