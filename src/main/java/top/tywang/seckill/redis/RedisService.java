package top.tywang.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import top.tywang.seckill.redis.prefix.KeyPrefix;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = jedisPool.getResource();
        T result = stringToBean(jedis.get(prefix.getPrefix() + key), clazz);
        returnToPool(jedis);
        return result;
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        String valueStr = beanToString(value);
        if (StringUtils.isEmpty(valueStr)) {
            return false;
        }

        Jedis jedis = jedisPool.getResource();
        String keyStr = prefix.getPrefix() + key;
        jedis.set(keyStr, valueStr);
        int seconds = prefix.expireSeconds();
        if (seconds <= 0) {
            jedis.set(keyStr, valueStr);
        } else {
            jedis.setex(keyStr, seconds, valueStr);
        }

        returnToPool(jedis);
        return true;
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }


}