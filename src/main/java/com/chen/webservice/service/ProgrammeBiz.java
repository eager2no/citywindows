package com.chen.webservice.service;

import com.chen.webservice.dao.ProgrammeDAO;
import com.chen.webservice.model.Programme;
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
