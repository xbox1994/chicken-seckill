package top.tywang.seckill.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import top.tywang.seckill.domain.SecKillUser;

@Mapper
public interface SecKillUserDao {

    @Select("select * from user where id = #{id}")
    SecKillUser getById(@Param("id") long id);
}
