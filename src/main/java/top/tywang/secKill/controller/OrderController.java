package top.tywang.secKill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tywang.secKill.domain.OrderInfo;
import top.tywang.secKill.domain.SecKillUser;
import top.tywang.secKill.redis.RedisService;
import top.tywang.secKill.result.CodeMsg;
import top.tywang.secKill.result.Result;
import top.tywang.secKill.service.GoodsService;
import top.tywang.secKill.service.OrderService;
import top.tywang.secKill.service.UserService;
import top.tywang.secKill.vo.OrderDetailVo;
import top.tywang.secKill.vo.SecKillGoodsVo;

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
        SecKillGoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }

}
