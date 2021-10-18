package com.czw.newone.demo.service;

import com.czw.newone.demo.dao.ProgrammeDAO;
import com.czw.newone.demo.model.Programme;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProgrammeBiz {
    @Resource
    private ProgrammeDAO programmeDao;

    public List<Programme> getAllProgramme(){
        return programmeDao.getAllProgrammeList();
    }
}
