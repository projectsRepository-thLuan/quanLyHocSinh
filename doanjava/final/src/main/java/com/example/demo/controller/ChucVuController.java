package com.example.demo.controller;

import com.example.demo.entity.ChucVu;
import com.example.demo.service.ChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ChucVuController {
    @Autowired
    private ChucVuService chucvuService;
    @GetMapping("/chucvu")
    public String list_chucvu(Model model){
        model.addAttribute("chucvus", chucvuService.findAll());
        return "chucvu/list_chucvu";
    }
    @GetMapping("/chucvu/add")
    public String add(Model model){
        model.addAttribute("chucvu", new ChucVu());
        return "chucvu/form_chucvu";
    }
    @PostMapping("/chucvu/save")
    public String save(@Valid ChucVu chucvu, RedirectAttributes redirect){
        if (chucvu.getMacv() != null)
        {
            chucvu.setHocsinhs(chucvuService.findOne(chucvu.getMacv()).getHocsinhs());
            chucvu.setGiaoviens(chucvuService.findOne(chucvu.getMacv()).getGiaoviens());
        }
        chucvuService.save(chucvu);
        return "redirect:/chucvu";
    }
    @GetMapping("/chucvu/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model){
        model.addAttribute("chucvu", chucvuService.findOne(id));
        return "chucvu/form_chucvu";
    }
    @GetMapping("/chucvu/{id}/delete")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirect){
        chucvuService.delete(id);
        return "redirect:/chucvu";
    }
}
