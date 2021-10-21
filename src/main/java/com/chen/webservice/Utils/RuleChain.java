package com.chen.webservice.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 可添加不同的约束规则，放进 ruleHandleMap
 * @params:
 * @return:
 * @author: chenzhiwen
 * @dateTime: 2021/10/21 下午7:16
 */
@Service
public class RuleChain {

    public static final Map<Integer, RuleHandler> ruleHandleMap = new HashMap<>();
//
//    @Autowired
//    public static CategoryTypeHandler categoryTypeHandler;

    //    @PostConstruct
    static {
        final CategoryTypeHandler categoryTypeHandler = new CategoryTypeHandler();
        RuleChain.ruleHandleMap.put(1, categoryTypeHandler);
    }
}
