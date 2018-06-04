package top.tywang.secKill.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import top.tywang.secKill.domain.SecKillUser;
import top.tywang.secKill.redis.GoodsKey;
import top.tywang.secKill.redis.RedisService;
import top.tywang.secKill.result.Result;
import top.tywang.secKill.service.GoodsService;
import top.tywang.secKill.service.UserService;
import top.tywang.secKill.vo.GoodsDetailVo;
import top.tywang.secKill.vo.SecKillGoodsVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, SecKillUser user) {
        model.addAttribute("user", user);
        //查页面缓存
        String html = redisService.get(GoodsKey.getGoodsListHtml, "", String.class);
        if (StringUtils.isNotEmpty(html)) {
            return html;
        }
        //手动渲染
        List<SecKillGoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",
                new SpringWebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap(), applicationContext));
        if (StringUtils.isNotEmpty(html)) {
            redisService.set(GoodsKey.getGoodsListHtml, "", html);
        }
        return html;
    }

    @RequestMapping("/to_detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(SecKillUser user, @PathVariable("goodsId") long goodsId) {
        SecKillGoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int secKillStatus;
        int remainSeconds;
        if (now < startAt) {//秒杀还没开始，倒计时
            secKillStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {//秒杀已经结束
            secKillStatus = 2;
            remainSeconds = -1;
        } else {//秒杀进行中
            secKillStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setSecKillStatus(secKillStatus);
        return Result.success(vo);
    }

}
