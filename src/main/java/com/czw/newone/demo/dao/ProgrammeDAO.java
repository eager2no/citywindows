package com.czw.newone.demo.dao;

import com.czw.newone.demo.model.Programme;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProgrammeDAO {

    @Select("select id, planner_id from programme")
    List<Programme> getAllProgrammeList();
}
