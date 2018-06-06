package top.tywang.secKill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tywang.secKill.domain.SecKillOrder;
import top.tywang.secKill.domain.SecKillUser;
import top.tywang.secKill.redis.RedisService;
import top.tywang.secKill.service.GoodsService;
import top.tywang.secKill.service.OrderService;
import top.tywang.secKill.service.SecKillService;
import top.tywang.secKill.vo.SecKillGoodsVo;

@Service
@Slf4j
public class MQReceiver {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SecKillService secKillService;

    @RabbitListener(queues = "queue")
    public void receive(String message) {
        log.info(message);
    }

    @RabbitListener(queues = MQSender.SECKILL_QUEUE)
    public void secKill(String message) {
        log.info(message);
        SecKillMessage killMessage = RedisService.stringToBean(message, SecKillMessage.class);
        SecKillUser secKillUser = killMessage.getSecKillUser();
        SecKillGoodsVo goods = goodsService.getGoodsVoByGoodsId(killMessage.getGoodsId());

        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }

        //判断是否已经秒杀到了
        SecKillOrder order = orderService.getSecKillOrderByUserIdGoodsId(secKillUser.getId(), killMessage.getGoodsId());
        if (order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        secKillService.secKill(secKillUser, goods);
    }
}
