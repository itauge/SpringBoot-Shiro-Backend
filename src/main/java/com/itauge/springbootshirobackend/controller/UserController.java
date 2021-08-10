package com.itauge.springbootshirobackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.itauge.springbootshirobackend.config.annotation.Logical;
import com.itauge.springbootshirobackend.config.annotation.RequiresPermissions;
import com.itauge.springbootshirobackend.entity.User;
import com.itauge.springbootshirobackend.service.UserService;
import com.itauge.springbootshirobackend.util.CommonUtil;
import com.itauge.springbootshirobackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequiresPermissions("user:list")
    @GetMapping("/list")
    public ResultVO listUser(HttpServletRequest request){
        return userService.listUser(CommonUtil.request2Json(request));
    }

    @RequiresPermissions("user:add")
    @PostMapping("/addUser")
    public ResultVO addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @RequiresPermissions("user:update")
    @PostMapping("/updateUser")
    public ResultVO updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @GetMapping("/getAllRoles")
    public ResultVO getAllRoles(){
        return userService.getAllRoles();
    }

    /**
     * 角色列表
     * @return
     */
    @RequiresPermissions("role:list")
    @GetMapping("/listRole")
    public ResultVO listRole(){
        return userService.listRole();
    }

    /**
     * 查詢所有權限，給角色分配權限時使用
     */
    @RequiresPermissions("role:list")
    @GetMapping("/listAllPermission")
    public ResultVO listAllPermission(){
        return userService.listAllPermission();
    }

    /**
     * 新增角色
     * @param jsonObject
     * @return
     */
    @RequiresPermissions("role:add")
    @PostMapping("/addRole")
    public ResultVO addRole(@RequestBody JSONObject jsonObject){
        return userService.addRole(jsonObject);
    }

    /**
     * 修改角色
     * @param jsonObject
     * @return
     */
    @RequiresPermissions("role:update")
    @PostMapping("/updateRole")
    public ResultVO updateRole(@RequestBody JSONObject jsonObject){
        return userService.updateRole(jsonObject);
    }

    /**
     * 刪除角色
     * @param jsonObject
     * @return
     */
    @RequiresPermissions("role:delete")
    @PostMapping("/deleteRole")
    public ResultVO deleteRole(@RequestBody JSONObject jsonObject){
        return userService.deleteRole(jsonObject);
    }


}
