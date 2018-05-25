package top.tywang.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tywang.seckill.domain.OrderInfo;
import top.tywang.seckill.domain.SecKillUser;
import top.tywang.seckill.redis.RedisService;
import top.tywang.seckill.result.CodeMsg;
import top.tywang.seckill.result.Result;
import top.tywang.seckill.service.GoodsService;
import top.tywang.seckill.service.OrderService;
import top.tywang.seckill.service.UserService;
import top.tywang.seckill.vo.OrderDetailVo;
import top.tywang.seckill.vo.SeckillGoodsVo;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(SecKillUser user,
                                      @RequestParam("orderId") long orderId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo order = orderService.getOrderById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        SeckillGoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }

}
