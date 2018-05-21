package top.tywang.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tywang.seckill.domain.OrderInfo;
import top.tywang.seckill.domain.SecKillUser;
import top.tywang.seckill.vo.SeckillGoodsVo;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author dongwei
 * @date 2018/05/21
 * Time: 19:18
 */
@Service
public class SecKillService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo secKill(SecKillUser user, SeckillGoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);
        //order_info maiosha_order
        return orderService.createOrder(user, goods);
    }
}
