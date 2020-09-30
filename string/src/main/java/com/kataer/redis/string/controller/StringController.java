package com.kataer.redis.string.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/string")
public class StringController {
    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;

    @GetMapping("/iamge/{id}")
    public void getImage(@PathVariable(name = "id") Integer id) {
        String image = strRedisTemplate.opsForValue().get(id);
    }
}
