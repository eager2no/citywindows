package com.chen.webservice;

import com.chen.webservice.model.Order;
import com.chen.webservice.service.ProgrammeBiz;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication()
@MapperScan("com.chen.webservice.dao")
public class DemoApplication {
    private static final long startTime = 43200000;


    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);


        process();
    }

    private static void process() {
        System.out.println("entering\n");

        List<Order> orders = initOrders(10);
        printItems(orders, startTime);
        System.out.println(orders);
    }

    private static void printItems(List<Order> orders, long startTime) {
        int i = 0;
        long playedTime = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        for (Order order : orders) {
            i++;
            String sd = sdf.format(new Date(startTime));
            String ed = sdf.format(new Date(startTime + order.getPlayTime()));
            System.out.println("节目" + i + order.getOrderId() + ", 开始时间：" + sd + ", 结束时间：" + ed);
            playedTime = playedTime + order.getPlayTime();
            startTime = startTime + playedTime;
        }
    }

    public static List<Order> initOrders(int len) {
        List<Order> orders = new ArrayList<>();

        long startTime = 43200000;
        long endTime = 54000000;

        for (int i = 0; i < len; i++) {
            Order order = new Order(startTime, endTime - startTime);
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            orders.add(order);
        }
        return orders;
    }


}
