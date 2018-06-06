package top.tywang.secKill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tywang.secKill.redis.RedisService;

@Service
@Slf4j
public class MQSender {

    public static final String SECKILL_QUEUE = "secKill_queue";
    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(Object message) {
        amqpTemplate.convertAndSend("queue", RedisService.beanToString(message));
    }

    public void sendSecKillMessage(SecKillMessage build) {
        log.info("send secKill message" + build);
        amqpTemplate.convertAndSend(SECKILL_QUEUE, RedisService.beanToString(build));
    }
}
