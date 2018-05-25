package top.tywang.seckill.vo;

import lombok.Getter;
import lombok.Setter;
import top.tywang.seckill.domain.SecKillUser;

@Getter
@Setter
public class GoodsDetailVo {
    private int seckillStatus;
    private int remainSeconds;
    private SeckillGoodsVo goods;
    private SecKillUser user;
}
