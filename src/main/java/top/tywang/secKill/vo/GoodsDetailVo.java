package top.tywang.secKill.vo;

import lombok.Getter;
import lombok.Setter;
import top.tywang.secKill.domain.SecKillUser;

@Getter
@Setter
public class GoodsDetailVo {
    private int secKillStatus;
    private int remainSeconds;
    private SecKillGoodsVo goods;
    private SecKillUser user;
}
