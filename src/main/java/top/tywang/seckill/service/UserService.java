package top.tywang.seckill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tywang.seckill.dao.UserDao;
import top.tywang.seckill.domain.User;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	public User getById(int id) {
		 return userDao.getById(id);
	}

	@Transactional
	public boolean tx() {
		return true;
	}
	
}
