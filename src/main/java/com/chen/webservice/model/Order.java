package com.chen.webservice.model;

import lombok.Data;
import com.chen.webservice.Utils.CityConstants;

import java.util.Random;

@Data
public class Order {
    private long id;
    private long orderId;
    //    private
    private long startTime;
    private long endTime;
    private int playTime;//播放时长 秒
    private int playFrequency;//播放频次 次
    private int playPeriod;//播放周期 天
    private long landmarkId;//地标id
    private int category;//商业、政府、原创
    private int categorySub;//细分类
    private int style;//风格，动画、文字
    private int color;//色系
    private int rate;//评分
    private int aging;//时效

    public Order(long startTime, long endTime) {
        this.id = System.currentTimeMillis()-randInt(1000);
        this.orderId = this.id;
        this.startTime = randLong() % endTime + startTime;

        this.playTime = CityConstants.PLAY_TIME.get(randInt(5));
        this.endTime =this.startTime + this.playTime;

        this.playFrequency = 1 + randInt(5);
        this.playPeriod = 1 + randInt(30);
        this.category = randInt(3);
        this.categorySub = randInt(30);
        this.style = randInt(10);
        this.color = randInt(10);
        this.rate = randInt(10);
        this.aging = randInt(5);
    }

    private int randInt(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    private long randLong() {
        Random random = new Random();
        long x = random.nextLong();
        x = x> 0 ? x : -x;
        return x;
    }
}
