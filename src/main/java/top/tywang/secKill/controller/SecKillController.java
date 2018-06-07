package top.tywang.secKill.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.tywang.secKill.domain.SecKillUser;
import top.tywang.secKill.rabbitmq.MQSender;
import top.tywang.secKill.rabbitmq.SecKillMessage;
import top.tywang.secKill.redis.GoodsKey;
import top.tywang.secKill.redis.RedisService;
import top.tywang.secKill.result.CodeMsg;
import top.tywang.secKill.result.Result;
import top.tywang.secKill.service.GoodsService;
import top.tywang.secKill.service.OrderService;
import top.tywang.secKill.service.SecKillService;
import top.tywang.secKill.service.SecKillUserService;
import top.tywang.secKill.vo.SecKillGoodsVo;

import java.util.List;

@Controller
@RequestMapping("/secKill")
public class SecKillController implements InitializingBean {

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

    @Autowired
    MQSender sender;

    @PostMapping("/do_secKill")
    @ResponseBody
    public Result<Integer> list(Model model, SecKillUser user,
                                @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 判断是否秒杀到
        long stock = redisService.decr(GoodsKey.getSecKillGoodsStock, "" + goodsId);
        if (stock < 0) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        //入队
        sender.sendSecKillMessage(SecKillMessage.builder().secKillUser(user).goodsId(goodsId).build());
        return Result.success(0);

//        //判断库存
//        SecKillGoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        int stock = goods.getStockCount();
//        if (stock <= 0) {
//            return Result.error(CodeMsg.SECKILL_OVER);
//        }
//        //判断是否已经秒杀到了
//        SecKillOrder order = orderService.getSecKillOrderByUserIdGoodsId(user.getId(), goodsId);
//        if (order != null) {
//            return Result.error(CodeMsg.SECKILL_REPEAT);
//        }
//        //减库存 下订单 写入秒杀订单
//        OrderInfo orderInfo = secKillService.secKill(user, goods);
//        return Result.success(orderInfo);
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> secKillResult(Model model, SecKillUser user,
                                      @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = secKillService.getSecKillResult(user.getId(), goodsId);
        return Result.success(result);
    }


    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        List<SecKillGoodsVo> goodsList = goodsService.listGoodsVo();
        for(SecKillGoodsVo goods : goodsList) {
            goods.setStockCount(100);
            redisService.set(GoodsKey.getSecKillGoodsStock, ""+goods.getId(), 100);
        }
        secKillService.reset(goodsList);
        return Result.success(true);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<SecKillGoodsVo> secKillGoodsVos = goodsService.listGoodsVo();
        if (secKillGoodsVos == null) {
            return;
        }

        for (SecKillGoodsVo vo : secKillGoodsVos) {
            redisService.set(GoodsKey.getSecKillGoodsStock, vo.getId() + "", vo.getStockCount());
        }
    }
}
