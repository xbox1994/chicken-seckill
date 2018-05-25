package top.tywang.seckill.vo;


import lombok.Data;
import top.tywang.seckill.domain.SecKillUser;
@Data
public class GoodsDetailVo {
	private int miaoshaStatus = 0;
	private int remainSeconds = 0;
	private SecKillGoodsVo goods ;
	private SecKillUser user;

}
