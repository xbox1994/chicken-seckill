package top.tywang.secKill.dao;

import org.apache.ibatis.annotations.*;
import top.tywang.secKill.domain.OrderInfo;
import top.tywang.secKill.domain.SecKillOrder;

@Mapper
public interface OrderDao {

    @Select("select * from secKill_order where user_id=#{userId} and goods_id=#{goodsId}")
    SecKillOrder getSecKillOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

    @Insert("insert into secKill_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertSecKillOrder(SecKillOrder secKillOrder);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId")long orderId);

    @Delete("delete from order_info")
    void deleteOrders();

    @Delete("delete from secKill_order")
    void deleteMiaoshaOrders();

}
