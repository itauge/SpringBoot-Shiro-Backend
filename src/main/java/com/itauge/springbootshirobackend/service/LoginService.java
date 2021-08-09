package com.itauge.springbootshirobackend.service;

import com.itauge.springbootshirobackend.config.exception.CommonJsonException;
import com.itauge.springbootshirobackend.dao.LoginDao;
import com.itauge.springbootshirobackend.dto.session.SessionUserInfo;
import com.itauge.springbootshirobackend.entity.User;
import com.itauge.springbootshirobackend.util.CommonUtil;
import com.itauge.springbootshirobackend.util.constants.ErrorEnum;
import com.itauge.springbootshirobackend.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {

    @Autowired
    LoginDao loginDao;

    @Autowired
    TokenService tokenService;

    /**
     * 登錄表單提交
     */
    public ResultVO authLogin(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        User user1 = loginDao.checkUser(username, password);
        if (user1 == null) {
            throw new CommonJsonException(ErrorEnum.E_10010);
        }
        String token = tokenService.generateToke(username);
        user1.setToken(token);
        return CommonUtil.successJSON(user1);
    }

    /**
     * 查询当前登录用户的权限等信息
     */
    public ResultVO getInfo() {
        //从session获取用户信息
        SessionUserInfo userInfo = tokenService.getUserInfo();
        log.info(userInfo.toString());
        return CommonUtil.successJSON(userInfo);
    }

    /**
     * 退出登录
     */
    public ResultVO logout() {
        tokenService.invalidateToken();
        return CommonUtil.successJSON();
    }

}
