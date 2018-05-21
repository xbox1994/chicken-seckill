package top.tywang.seckill.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tywang.seckill.dao.OrderDao;
import top.tywang.seckill.domain.OrderInfo;
import top.tywang.seckill.domain.SecKillOrder;
import top.tywang.seckill.domain.SecKillUser;
import top.tywang.seckill.vo.SeckillGoodsVo;


@Service
public class OrderService {
	
	@Autowired
	OrderDao orderDao;
	
	public SecKillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
		return orderDao.getSeckillOrderByUserIdGoodsId(userId, goodsId);
	}

	@Transactional
	public OrderInfo createOrder(SecKillUser user, SeckillGoodsVo goods) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		long orderId = orderDao.insert(orderInfo);
		SecKillOrder seckillOrder = new SecKillOrder();
		seckillOrder.setGoodsId(goods.getId());
		seckillOrder.setOrderId(orderId);
		seckillOrder.setUserId(user.getId());
		orderDao.insertSeckillOrder(seckillOrder);
		return orderInfo;
	}
	
}
