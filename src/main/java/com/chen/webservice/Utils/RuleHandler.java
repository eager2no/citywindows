package com.chen.webservice.Utils;

import com.chen.webservice.Demo2;
import com.chen.webservice.model.Order;

import java.util.List;

/**
 * @description: rule接口，子类实现具体约束规则
 * @params:
 * @return:
 * @author: chenzhiwen
 * @dateTime: 2021/10/21 下午7:19
 */
public interface RuleHandler {
    public boolean isMatch(List<Integer> rules);

    public int checkResult(List<Order> orders, int rate);
}
