package top.tywang.secKill.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;
import top.tywang.secKill.MainApplication;
import top.tywang.secKill.domain.User;
import top.tywang.secKill.redis.prefix.UserKey;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@EnableAutoConfiguration
public class RedisTest {

    @Autowired
    RedisService redisService;

    @Test
    public void redisSetTest(){
        boolean ret = redisService.set(UserKey.getByName, "a", User.builder().id(1L).build());
        Assert.assertEquals(true,ret);
    }

    @Test
    public void existJedisTest(){
        JedisPool jedisPool = redisService.jedisPool;
        Assert.assertEquals(true,jedisPool != null);
    }

    @Test
    public void redisGetTest(){
        User ret = redisService.get(UserKey.getByName, "a",User.class);
        Assert.assertTrue(ret.getId() == 1L);
    }
}
