package top.tywang.secKill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tywang.secKill.domain.OrderInfo;
import top.tywang.secKill.domain.SecKillOrder;
import top.tywang.secKill.domain.SecKillUser;
import top.tywang.secKill.redis.GoodsKey;
import top.tywang.secKill.redis.RedisService;
import top.tywang.secKill.vo.SecKillGoodsVo;

import java.util.List;

@Service
public class SecKillService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo secKill(SecKillUser user, SecKillGoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if (success) {
            return orderService.createOrder(user, goods);
        }
        return null;
    }

    public long getSecKillResult(Long id, long goodsId) {
        SecKillOrder order = orderService.getSecKillOrderByUserIdGoodsId(id, goodsId);
        if (order != null) {
            return order.getId();
        } else {
            return redisService.get(GoodsKey.getSecKillGoodsStock, goodsId + "", String.class).equals("0") ? -1 : 0;
        }
    }

    public void reset(List<SecKillGoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }
}
