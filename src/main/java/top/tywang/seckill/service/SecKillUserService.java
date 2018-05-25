package top.tywang.seckill.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tywang.seckill.dao.SecKillUserDao;
import top.tywang.seckill.domain.SecKillUser;
import top.tywang.seckill.exception.GlobalException;
import top.tywang.seckill.redis.RedisService;
import top.tywang.seckill.redis.SecKillUserKey;
import top.tywang.seckill.result.CodeMsg;
import top.tywang.seckill.util.MD5Util;
import top.tywang.seckill.util.UUIDUtil;
import top.tywang.seckill.vo.LoginVo;


@Service
public class SecKillUserService {

	
	public static final String COOKIE_NAME_TOKEN = "token";
	
	@Autowired
	SecKillUserDao miaoshaUserDao;
	
	@Autowired
	RedisService redisService;
	
	public SecKillUser getById(long id) {
		//取缓存
		SecKillUser user = redisService.get(SecKillUserKey.getById, ""+id, SecKillUser.class);
		if(user != null) {
			return user;
		}
		//取数据库
		user = miaoshaUserDao.getById(id);
		if(user != null) {
			redisService.set(SecKillUserKey.getById, ""+id, user);
		}
		return user;
	}
	

	public SecKillUser getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		SecKillUser user = redisService.get(SecKillUserKey.token, token, SecKillUser.class);
		//延长有效期
		if(user != null) {
			addCookie(response, token, user);
		}
		return user;
	}

	public boolean updatePassword(String token, long id, String formPass) {
		//取user
		SecKillUser user = getById(id);
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//更新数据库
        SecKillUser toBeUpdate = new SecKillUser();
		toBeUpdate.setId(id);
		toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
		miaoshaUserDao.update(toBeUpdate);
		//处理缓存
		redisService.delete(SecKillUserKey.getById, ""+id);
		user.setPassword(toBeUpdate.getPassword());
		redisService.set(SecKillUserKey.token, token, user);
		return true;
	}
	

	public boolean login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		//判断手机号是否存在
		SecKillUser user = getById(Long.parseLong(mobile));
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		//生成cookie
		String token	 = UUIDUtil.uuid();
		addCookie(response, token, user);
		return true;
	}
	
	private void addCookie(HttpServletResponse response, String token, SecKillUser user) {
		redisService.set(SecKillUserKey.token, token, user);
		Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
		cookie.setMaxAge(SecKillUserKey.token.expireSeconds());
		cookie.setPath("/");
		response.addCookie(cookie);
	}

}
