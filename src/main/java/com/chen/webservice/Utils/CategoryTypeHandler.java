package com.chen.webservice.Utils;

import com.chen.webservice.Demo2;
import com.chen.webservice.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 规则1 商业>政府>原创
 * @params: 
 * @return:  
 * @author: chenzhiwen
 * @dateTime: 2021/10/21 下午7:16
 */
@Service
public class CategoryTypeHandler implements RuleHandler {
    @Override
    public boolean isMatch(List<Integer> rules) {
        return false;
    }

    @Override
    public int checkResult(List<Order> orders, int rate) {
        rate = 0;
        int len = orders.size();
        if (len <= 1) {
            return rate;
        }
        for (int i = 0; i < len - 1; i++) {
            Order o1 = orders.get(i);
            Order o2 = orders.get(i + 1);
            if (o1.getCategory() > o2.getCategory()) {
                rate+=1;
            }
        }
        return rate;
    }
}
