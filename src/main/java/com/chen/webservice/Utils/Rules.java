package com.chen.webservice.Utils;

import com.chen.webservice.model.Order;

import java.util.List;

public class Rules {
    public static int businessFirst(List<Order> orders) {
        int rate = 0;
        int len = orders.size();
        if (len <= 1) {
            return rate;
        }
//        if (orders.get(0).getPlayTime() < 15001 && orders.get(1).getPlayTime() > 15001 && orders.get(2).getPlayTime() > 15001 && orders.get(3).getPlayTime() == 15000) {
//            return rate ;
//        }
        for (int i = 0; i < len - 1; i++) {
            Order o1 = orders.get(i);
            Order o2 = orders.get(i + 1);
            if (o1.getCategory() > o2.getCategory()) {
                rate++;
            }
        }
        return rate+1;
    }
}
