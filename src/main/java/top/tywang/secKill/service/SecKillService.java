package top.tywang.secKill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tywang.secKill.domain.OrderInfo;
import top.tywang.secKill.domain.SecKillUser;
import top.tywang.secKill.vo.SecKillGoodsVo;

@Service
public class SecKillService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo secKill(SecKillUser user, SecKillGoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);
        //order_info secKill_order
        return orderService.createOrder(user, goods);
    }
}
