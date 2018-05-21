package top.tywang.seckill.vo;

import lombok.Data;
import top.tywang.seckill.domain.Goods;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author dongwei
 * @date 2018/05/21
 * Time: 18:35
 */
@Data
public class SeckillGoodsVo extends Goods {
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
