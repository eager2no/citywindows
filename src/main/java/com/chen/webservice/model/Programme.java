package com.chen.webservice.model;

import lombok.Data;

@Data
public class Programme {
    private long id;
    private long plannerId;
    private String test1 = String.valueOf(plannerId);
}
