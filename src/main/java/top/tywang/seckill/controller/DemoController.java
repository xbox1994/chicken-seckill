package top.tywang.seckill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tywang.seckill.domain.User;
import top.tywang.seckill.redis.RedisService;
import top.tywang.seckill.redis.prefix.UserKey;
import top.tywang.seckill.result.Result;

@Controller
@RequestMapping("/")
public class DemoController {
    @Autowired
    RedisService redisService;

    @GetMapping("/redis/1")
    public String test(Model model) {
        redisService.set(UserKey.getByName, "a", User.builder().id(1).build());
        model.addAttribute("name", redisService.get(UserKey.getByName, "a", User.class));
        return "hello";
    }

    @GetMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> setUser() {
        redisService.set(UserKey.getByName, "a", User.builder().id(1).build());
        return Result.success(true);
    }

    @GetMapping("/redis/get")
    @ResponseBody
    public Result<User> getUser() {
        return Result.success(redisService.get(UserKey.getByName, "a", User.class));
    }
}
