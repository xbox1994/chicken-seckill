package top.tywang.secKill.redis.prefix;

public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();

}
