package com.quiz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenBlacklistService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void blacklistToken(String token) {
        
        redisTemplate.opsForValue().set(token, "blacklisted", Duration.ofMinutes(15));
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}