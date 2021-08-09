package com.itauge.springbootshirobackend.controller;

import com.itauge.springbootshirobackend.entity.User;
import com.itauge.springbootshirobackend.service.LoginService;
import com.itauge.springbootshirobackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * login
     */
    @PostMapping("/auth")
    public ResultVO authLogin(@RequestBody User user){
        return loginService.authLogin(user);
    }

    /**
     * 查询当前登录用户的信息
     */
    @PostMapping("/getInfo")
    public ResultVO getInfo() {
        return loginService.getInfo();
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public ResultVO logout() {
        return loginService.logout();
    }

}
