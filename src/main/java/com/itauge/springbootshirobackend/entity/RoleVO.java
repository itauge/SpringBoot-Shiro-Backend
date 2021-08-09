package com.itauge.springbootshirobackend.entity;

import com.alibaba.fastjson.JSONObject;
import com.itauge.springbootshirobackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO {
    private Set<String> roleList;
    private Set<String> menuList;
    private Set<String> permissionList;
    private Set<Integer> permissionIds;
    private int roleId;
    private String roleName;
    private String menuName;
    private String menuCode;
    private List<User> users;
    private List<JSONObject> menus;
    private List<JSONObject> permissions;
}
