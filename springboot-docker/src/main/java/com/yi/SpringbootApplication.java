package com.yi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
public class SpringbootApplication {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello world!";
    }

    @RequestMapping("/getReids")
    public String getReids() {
        String sql = "select * from user";
        List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql);
        redisTemplate.opsForValue().set("data1", list);
        Object uu = redisTemplate.opsForValue().get("data1");

        return uu.toString();
    }

    @RequestMapping("/saveReids")
    public String saveReids(@RequestParam(value = "name", defaultValue = "yi") String name) {
        String sql = "insert into user (name) values(?)";
        jdbcTemplate.update(sql, new Object[]{name});
        return name.toString();
    }

    @RequestMapping("/deleteReids")
    public String deleteReids() {
        redisTemplate.delete("data1");
        return "delete success!";
    }


}
