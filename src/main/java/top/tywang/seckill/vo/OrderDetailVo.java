package top.tywang.seckill.vo;


import lombok.Getter;
import lombok.Setter;
import top.tywang.seckill.domain.OrderInfo;

@Getter
@Setter
public class OrderDetailVo {
    private SeckillGoodsVo goods;
    private OrderInfo order;
}
