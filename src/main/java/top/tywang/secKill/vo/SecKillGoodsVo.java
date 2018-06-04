package top.tywang.secKill.vo;

import lombok.Data;
import top.tywang.secKill.domain.Goods;

import java.util.Date;

@Data
public class SecKillGoodsVo extends Goods {
    private Double secKillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
