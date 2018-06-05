package top.tywang.secKill.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tywang.secKill.redis.RedisService;

@Service
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(Object message){
        amqpTemplate.convertAndSend("queue", RedisService.beanToString(message));
    }
}
