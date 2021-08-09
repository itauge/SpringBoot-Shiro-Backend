package com.itauge.springbootshirobackend.service;

//caffeine緩存要引入pom
import com.github.benmanes.caffeine.cache.Cache;
import com.itauge.springbootshirobackend.config.exception.CommonJsonException;
import com.itauge.springbootshirobackend.dao.LoginDao;
import com.itauge.springbootshirobackend.dto.session.SessionUserInfo;
import com.itauge.springbootshirobackend.util.StringUtil;
import com.itauge.springbootshirobackend.util.constants.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TokenService {
    @Autowired
    Cache<String,SessionUserInfo> cacheMap;

    @Autowired
    LoginDao loginDao;

    public String generateToke(String username){
        MDC.put("username",username);
        String token = UUID.randomUUID().toString().replace("-","").substring(0,20);
        //設置用戶信息緩存
        setCache(token,username);
        return token;
    }

    /**
     * 根据token查询用户信息
     * 如果token无效,会抛未登录的异常
     */
    private SessionUserInfo getUserInfoFromCache(String token) {
        if (StringUtil.isNullOrEmpty(token)) {
            throw new CommonJsonException(ErrorEnum.E_20011);
        }
        log.debug("根据token从缓存中查询用户信息,{}", token);
        SessionUserInfo info = cacheMap.getIfPresent(token);
        if (info == null) {
            log.info("没拿到缓存 token={}", token);
            throw new CommonJsonException(ErrorEnum.E_20011);
        }
        return info;
    }

    public SessionUserInfo getUserInfo() {
        String token = MDC.get("token");
        return getUserInfoFromCache(token);
    }

    private void setCache(String token, String username) {
        SessionUserInfo info = getUserInfoByUsername(username);
        log.info("设置用户信息缓存:token={} , username={}, info={}", token, username, info);
        cacheMap.put(token, info);
    }

    /**
     * 退出登录时,将token置为无效
     */
    public void invalidateToken() {
        String token = MDC.get("token");
        if (!StringUtil.isNullOrEmpty(token)) {
            cacheMap.invalidate(token);
        }
        log.debug("退出登录,清除缓存:token={}", token);
    }

    private SessionUserInfo getUserInfoByUsername(String username) {
        SessionUserInfo userInfo = loginDao.getUserInfo(username);
        if (userInfo.getRoleIds().contains(1)) {
            //管理员,查出全部按钮和权限码
            userInfo.setMenuList(loginDao.getAllMenu());
            userInfo.setPermissionList(loginDao.getAllPermissionCode());
        }
        return userInfo;
    }



}
