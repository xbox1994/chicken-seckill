package top.tywang.secKill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tywang.secKill.domain.OrderInfo;
import top.tywang.secKill.domain.SecKillOrder;
import top.tywang.secKill.domain.SecKillUser;
import top.tywang.secKill.redis.RedisService;
import top.tywang.secKill.result.CodeMsg;
import top.tywang.secKill.result.Result;
import top.tywang.secKill.service.GoodsService;
import top.tywang.secKill.service.OrderService;
import top.tywang.secKill.service.SecKillService;
import top.tywang.secKill.service.SecKillUserService;
import top.tywang.secKill.vo.SecKillGoodsVo;

@   Controller
@RequestMapping("/secKill")
public class SecKillController {

    @Autowired
    SecKillUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SecKillService secKillService;

    @PostMapping("/do_secKill")
    @ResponseBody
    public Result<OrderInfo> list(Model model, SecKillUser user,
                                  @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        SecKillGoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        //判断是否已经秒杀到了
        SecKillOrder order = orderService.getSecKillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.SECKILL_REPEAT);
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = secKillService.secKill(user, goods);
        return Result.success(orderInfo);
    }
}
