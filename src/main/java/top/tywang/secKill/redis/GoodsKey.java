package top.tywang.secKill.redis;

import top.tywang.secKill.redis.prefix.BasePrefix;

public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsListHtml = new GoodsKey(60, "glh");


    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    public static GoodsKey getSecKillGoodsStock = new GoodsKey(0, "skgs");


}
