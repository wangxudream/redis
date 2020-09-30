package com.kataer.redis.common.entity;

import lombok.Data;

@Data
public class JsonResult<T> {
    private Integer code;
    private T data;
}
