package top.tywang.seckill.redis;

import top.tywang.seckill.redis.prefix.BasePrefix;

public class OrderKey extends BasePrefix {

	public OrderKey(String prefix) {
		super(prefix);
	}
	public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
}
