package com.oblivion.sugar.sugar.mapper;

import com.oblivion.sugar.sugar.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    @Insert("insert into user (account,name,token,create_time,modify_time) values (#{account},#{name},#{token},#{createTime},#{modifyTime})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
}
