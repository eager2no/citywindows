package com.czw.newone.demo.controller;

import com.czw.newone.demo.model.Programme;
import com.czw.newone.demo.service.ProgrammeBiz;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProgrammeController {

    @Resource
    private ProgrammeBiz programmeBiz;



    @GetMapping("index")
    public String index(Model model){
        List<Programme> list = this.programmeBiz.getAllProgramme();
        System.out.println(list);
        model.addAttribute("msg","欢迎！");
        return list.toString();
    }
}
