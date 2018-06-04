package top.tywang.secKill.redis;

import top.tywang.secKill.redis.prefix.BasePrefix;

public class SecKillUserKey extends BasePrefix {

	public static final int TOKEN_EXPIRE = 3600*24 * 2;
	private SecKillUserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static SecKillUserKey token = new SecKillUserKey(TOKEN_EXPIRE, "tk");

	public static SecKillUserKey getById = new SecKillUserKey(0, "id");
}
