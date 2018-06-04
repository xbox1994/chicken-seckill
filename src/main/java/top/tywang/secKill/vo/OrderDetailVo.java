package top.tywang.secKill.vo;


import lombok.Getter;
import lombok.Setter;
import top.tywang.secKill.domain.OrderInfo;

@Getter
@Setter
public class OrderDetailVo {
    private SecKillGoodsVo goods;
    private OrderInfo order;
}
