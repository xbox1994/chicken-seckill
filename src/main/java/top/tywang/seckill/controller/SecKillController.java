package top.tywang.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tywang.seckill.domain.OrderInfo;
import top.tywang.seckill.domain.SecKillOrder;
import top.tywang.seckill.domain.SecKillUser;
import top.tywang.seckill.redis.RedisService;
import top.tywang.seckill.result.CodeMsg;
import top.tywang.seckill.result.Result;
import top.tywang.seckill.service.GoodsService;
import top.tywang.seckill.service.OrderService;
import top.tywang.seckill.service.SecKillService;
import top.tywang.seckill.service.SecKillUserService;
import top.tywang.seckill.vo.SeckillGoodsVo;

@   Controller
@RequestMapping("/seckill")
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
    SecKillService seckillService;

    @PostMapping("/do_seckill")
    @ResponseBody
    public Result<OrderInfo> list(Model model, SecKillUser user,
                                  @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        SeckillGoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        //判断是否已经秒杀到了
        SecKillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.SECKILL_REPEAT);
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = seckillService.secKill(user, goods);
        return Result.success(orderInfo);
    }
}
