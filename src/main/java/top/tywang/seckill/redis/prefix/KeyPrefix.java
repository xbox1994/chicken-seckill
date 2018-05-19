package top.tywang.seckill.redis.prefix;

public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();

}
