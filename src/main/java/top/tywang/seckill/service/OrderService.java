package top.tywang.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tywang.seckill.dao.OrderDao;
import top.tywang.seckill.domain.OrderInfo;
import top.tywang.seckill.domain.SecKillOrder;
import top.tywang.seckill.domain.SecKillUser;
import top.tywang.seckill.redis.OrderKey;
import top.tywang.seckill.redis.RedisService;
import top.tywang.seckill.vo.SeckillGoodsVo;

import java.util.Date;


@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisService redisService;

    public SecKillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
        return orderDao.getSeckillOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    public OrderInfo createOrder(SecKillUser user, SeckillGoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);
        SecKillOrder seckillOrder = new SecKillOrder();
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setOrderId(orderId);
        seckillOrder.setUserId(user.getId());
        orderDao.insertSeckillOrder(seckillOrder);
        return orderInfo;
    }

    public SecKillOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid, "" + userId + "_" + goodsId, SecKillOrder.class);
    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }


}
