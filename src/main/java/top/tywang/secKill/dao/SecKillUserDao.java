package top.tywang.secKill.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Update;
import top.tywang.secKill.domain.SecKillUser;

@Mapper
public interface SecKillUserDao {

    @Select("select * from secKill_user where id = #{id}")
    SecKillUser getById(@Param("id") long id);

    @Update("update secKill_user set password = #{password} where id = #{id}")
    void update(SecKillUser toBeUpdate);

}
