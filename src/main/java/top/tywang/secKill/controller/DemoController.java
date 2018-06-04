package top.tywang.secKill.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tywang.secKill.domain.SecKillUser;
import top.tywang.secKill.domain.User;
import top.tywang.secKill.redis.RedisService;
import top.tywang.secKill.redis.prefix.UserKey;
import top.tywang.secKill.result.Result;

@Controller
@RequestMapping("/")
@Slf4j
public class DemoController {
    @Autowired
    RedisService redisService;

    @GetMapping("/redis/1")
    public String test(Model model) {
        redisService.set(UserKey.getByName, "a", User.builder().id(1L).build());
        model.addAttribute("name", redisService.get(UserKey.getByName, "a", User.class));
        return "hello";
    }

    @GetMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> setUser() {
        redisService.set(UserKey.getByName, "a", User.builder().id(1L).build());
        return Result.success(true);
    }

    @GetMapping("/redis/get")
    @ResponseBody
    public Result<SecKillUser> getUser(SecKillUser user) {
        return Result.success(user);
    }
}
