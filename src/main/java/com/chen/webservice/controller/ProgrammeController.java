package com.chen.webservice.controller;

import com.chen.webservice.model.Programme;
import com.chen.webservice.service.ProgrammeBiz;
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
