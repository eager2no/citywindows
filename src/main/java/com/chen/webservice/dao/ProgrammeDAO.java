package com.chen.webservice.dao;

import com.chen.webservice.model.Programme;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProgrammeDAO {

    @Select("select id, planner_id from programme")
    List<Programme> getAllProgrammeList();
}
