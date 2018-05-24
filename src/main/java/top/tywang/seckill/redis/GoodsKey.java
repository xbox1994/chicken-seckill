package top.tywang.seckill.redis;

import top.tywang.seckill.redis.prefix.BasePrefix;

public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsListHtml = new GoodsKey(60, "glh");
}
