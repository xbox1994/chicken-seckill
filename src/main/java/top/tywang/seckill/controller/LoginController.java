package top.tywang.seckill.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.tywang.seckill.redis.RedisService;
import top.tywang.seckill.result.Result;
import top.tywang.seckill.service.SecKillUserService;
import top.tywang.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    RedisService redisService;

    @Autowired
    SecKillUserService userService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public Result<Boolean> login(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info("login user: " + loginVo.toString());
        userService.login(response, loginVo);
        return Result.success(true);
    }
}
