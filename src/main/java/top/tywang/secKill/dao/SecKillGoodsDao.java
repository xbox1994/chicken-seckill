package top.tywang.secKill.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.tywang.secKill.domain.SecKillGoods;
import top.tywang.secKill.vo.SecKillGoodsVo;

import java.util.List;


@Mapper
public interface SecKillGoodsDao {

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.secKill_price from secKill_goods mg left join goods g on mg.goods_id = g.id")
    List<SecKillGoodsVo> listSecKillGoodsVo();

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.secKill_price from secKill_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    SecKillGoodsVo getSecKillGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update secKill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
    int reduceStock(SecKillGoods g);

    @Update("update secKill_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    int resetStock(SecKillGoods g);

}
