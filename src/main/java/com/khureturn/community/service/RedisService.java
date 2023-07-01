package com.khureturn.community.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisTemplate<String, Object> redisBlackListTemplate;

    public String getData(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }


    public void setData(String key, String value, int time){ //키 : 밸류
        Duration expireDuration = Duration.ofSeconds(time);
        redisTemplate.opsForValue().set(key, value, expireDuration);
    }

    public void setDataWithExpiration(String key, String value, int time){  // 키 : 밸류 : 만료시간.
        if(this.getData(key) != null){ //우선 삭제.
            this.redisTemplate.delete(key);
        }
        setData(key, value, time);
    }

    public boolean hasKey(String key) {
        return redisBlackListTemplate.hasKey(key);
    }

    public void deleteValues(String key){ //데이터 삭제
        redisTemplate.delete(key);
    }


    public void setBlackListToken(String key, String value, Long time ){
        Duration expireDuration = Duration.ofSeconds(time);
        redisBlackListTemplate.opsForValue().set(key, value, expireDuration);
    }

    public Object getBlackListToken(String key) {
        return redisBlackListTemplate.opsForValue().get(key);
    }

    public boolean hasKeyBlackListToken(String key) {
        return redisBlackListTemplate.hasKey(key);
    }

    public void deleteBlackListToken(String key) {
        redisBlackListTemplate.delete(key);
    }


}