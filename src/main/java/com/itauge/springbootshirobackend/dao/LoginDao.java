package com.itauge.springbootshirobackend.dao;

import com.itauge.springbootshirobackend.dto.session.SessionUserInfo;
import com.itauge.springbootshirobackend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface LoginDao {
    public User checkUser(@Param("username")String username,@Param("password")String password);
    SessionUserInfo getUserInfo(String username);
    Set<String> getAllMenu();
    Set<String> getAllPermissionCode();


}
