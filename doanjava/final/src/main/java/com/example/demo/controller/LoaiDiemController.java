package com.example.demo.controller;

import com.example.demo.entity.LoaiDiem;
import com.example.demo.service.LoaiDiemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoaiDiemController {
    @Autowired
    private LoaiDiemService loaidiemService;
    @GetMapping("/loaidiem")
    public String list_loaidiem(Model model){
        model.addAttribute("loaidiems", loaidiemService.findAll());
        return "loaidiem/list_loaidiem";
    }
    @GetMapping("/loaidiem/add")
    public String add(Model model){
        model.addAttribute("loaidiem", new LoaiDiem());
        return "loaidiem/form_loaidiem";
    }
    @PostMapping("/loaidiem/save")
    public String save(@Valid LoaiDiem loaidiem, RedirectAttributes redirect){
        if(loaidiem.getMaloai() != null)
        {
            loaidiem.setDiems(loaidiemService.findOne(loaidiem.getMaloai()).getDiems());
        }
        loaidiemService.save(loaidiem);
        return "redirect:/loaidiem";
    }
    @GetMapping("/loaidiem/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model){
        model.addAttribute("loaidiem", loaidiemService.findOne(id));
        return "loaidiem/form_loaidiem";
    }
    @GetMapping("/loaidiem/{id}/delete")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirect){
        loaidiemService.delete(id);
        return "redirect:/loaidiem";
    }

}
