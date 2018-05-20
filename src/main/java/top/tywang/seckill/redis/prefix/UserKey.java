package top.tywang.seckill.redis.prefix;

public class UserKey extends BasePrefix {

    private static final int TOKEN_EXPIRE = 3600*24 * 2;

    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
    public static UserKey getByToken = new UserKey(TOKEN_EXPIRE, "token");
}
