package com.simon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @data: 2020/6/16 10:26
 * @author: limeng17
 * @version:
 * @description:
 */
@Service("cacheService")
public class CacheService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Boolean lock (String key,int expired){
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, "1",expired,TimeUnit.SECONDS);
        return result;
    }

    public void set(String key,String value,int expired){
        redisTemplate.opsForValue().set(key,value,expired, TimeUnit.SECONDS);
    }

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void del(String key){
        redisTemplate.delete(key);
    }

    public Long incr(String key){
        return redisTemplate.opsForValue().increment(key,1L);
    }

    /**
     * 向Hash中添加值
     * @param key      可以对应数据库中的表名
     * @param field    可以对应数据库表中的唯一索引
     * @param value    存入redis中的值
     */
    public void hset(String key, String field, String value) {
        if(key == null || "".equals(key)){
            return ;
        }
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 从redis中取出值
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field){
        if(key == null || "".equals(key)){
            return null;
        }
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 判断 是否存在 key 以及 hash key
     * @param key
     * @param field
     * @return
     */
    public boolean hexists(String key, String field){
        if(key == null || "".equals(key)){
            return false;
        }
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 查询 key中对应多少条数据
     * @param key
     * @return
     */
    public long hsize(String key) {
        if(key == null || "".equals(key)){
            return 0L;
        }
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 删除
     * @param key
     * @param field
     */
    public void hdel(String key, String field) {
        if(key == null || "".equals(key)){
            return;
        }
        redisTemplate.opsForHash().delete(key, field);
    }

    public void zadd(String key, String data, double score){
        redisTemplate.opsForZSet().add(key,data,score);
    };

    public void zrem(String key, String data){
        redisTemplate.opsForZSet().remove(key,data);
    };

    public Set<String> zrangeByScore(String key, double min, double max){
        return redisTemplate.opsForZSet().rangeByScore(key,min,max);
    };
}
