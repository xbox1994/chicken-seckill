package top.tywang.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.tywang.seckill.domain.SecKillUser;
import top.tywang.seckill.redis.RedisService;
import top.tywang.seckill.service.GoodsService;
import top.tywang.seckill.service.UserService;
import top.tywang.seckill.vo.SeckillGoodsVo;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_list")
    public String list(Model model, SecKillUser user) {
        model.addAttribute("user", user);
        //查询商品列表
        List<SeckillGoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, SecKillUser user,
                         @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);

        SeckillGoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int secKillStatus;
        int remainSeconds;
        if (now < startAt) {
            //秒杀还没开始，倒计时
            secKillStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {
            //秒杀已经结束
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }

}
