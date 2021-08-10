package com.itauge.springbootshirobackend.service;

import com.alibaba.fastjson.JSONObject;
import com.itauge.springbootshirobackend.dao.UserDao;
import com.itauge.springbootshirobackend.entity.User;
import com.itauge.springbootshirobackend.util.CommonUtil;
import com.itauge.springbootshirobackend.util.constants.ErrorEnum;
import com.itauge.springbootshirobackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    /**
     * 用戶列表
     */
    public ResultVO listUser(JSONObject jsonObject){
        CommonUtil.fillPageParam(jsonObject);
        int count = userDao.countUser();
        List<User> users = userDao.listUser(jsonObject);
        return CommonUtil.successPage(jsonObject,users,count);
    }

    /**
     * 添加用戶
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addUser(User user){
        int i = userDao.queryExistUsername(user.getUsername());
        if (i>0){
            return CommonUtil.errorJson(ErrorEnum.E_10009);
        }
        userDao.addUser(user);
        userDao.batchAddUserRole(user);
        return CommonUtil.successJSON();
    }

    /**
     * 查詢所有角色
     * 在添加、修改時要用此方法
     */
    public ResultVO getAllRoles(){
        List<JSONObject> allRoles = userDao.getAllRoles();
        return CommonUtil.successPage(allRoles);
    }

    /**
     * 修改用戶
     * @param user
     * @return
     */
    public ResultVO updateUser(User user){
        //不允許修改管理員信息
        if (user.getId() == 10001) return CommonUtil.successJSON();
        userDao.updateUser(user);
        userDao.removeUserAllRole(user.getId());
        if (!user.getRoles().isEmpty()){
            userDao.batchAddUserRole(user);
        }
        return CommonUtil.successJSON();
    }

    /**
     * 角色列表
     * @return
     */
    public ResultVO listRole(){
        List<JSONObject> jsonObjects = userDao.listRole();
        return CommonUtil.successPage(jsonObjects);
    }

    /**
     * 查詢所有權限 給角色分配權限時調用
     * @return
     */
    public ResultVO listAllPermission(){
        List<JSONObject> jsonObjects = userDao.listAllPermission();
        return CommonUtil.successPage(jsonObjects);
    }

    /**
     * 添加角色
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addRole(JSONObject jsonObject){
        userDao.insertRole(jsonObject);
        userDao.insertRolePermission(jsonObject.getString("roleId"),(List<Integer>) jsonObject.get("permissions"));
        return CommonUtil.successJSON();
    }


    @Transactional(rollbackFor = Exception.class)
    public ResultVO updateRole(JSONObject jsonObject){
        String roleId = jsonObject.getString("roleId");
        List<Integer> newPerms = (List<Integer>) jsonObject.get("permissions");
        JSONObject roleinfo = userDao.getRoleAllInfo(jsonObject);
        Set<Integer> oldPerms = (Set<Integer>) roleinfo.get("permissionIds");
        //修改角色名稱
        dealRoleName(jsonObject, roleinfo);
        //添加新权限
        saveNewPermission(roleId, newPerms, oldPerms);
        //移除旧的不再拥有的权限
        removeOldPermission(roleId, newPerms, oldPerms);
        return CommonUtil.successJSON();
    }

    /**
     * 修改角色名稱
     */
    private void dealRoleName(JSONObject jsonObject,JSONObject roleInfo){
        String roleName = jsonObject.getString("roleName");
        if (!roleName.equals(roleInfo.getString("roleName"))) {
            userDao.updateRoleName(jsonObject);
        }
    }

    /**
     * 為角色添加新權限
     */
    private void saveNewPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitInsert = new ArrayList<>();
        for (Integer newPerm : newPerms) {
            if (!oldPerms.contains(newPerm)) {
                waitInsert.add(newPerm);
            }
        }
        if (waitInsert.size() > 0) {
            userDao.insertRolePermission(roleId, waitInsert);
        }
    }

    /**
     * 刪除角色 舊的 不再拥有的權限
     */
    private void removeOldPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> waitRemove = new ArrayList<>();
        for (Integer oldPerm : oldPerms) {
            if (!newPerms.contains(oldPerm)) {
                waitRemove.add(oldPerm);
            }
        }
        if (waitRemove.size() > 0) {
            userDao.removeOldPermission(roleId, waitRemove);
        }
    }

    /**
     * 删除角色
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public ResultVO deleteRole(JSONObject jsonObject) {
        JSONObject roleInfo = userDao.getRoleAllInfo(jsonObject);
        List<JSONObject> users = (List<JSONObject>) roleInfo.get("users");
        if (users != null && users.size() > 0) {
            return CommonUtil.errorJson(ErrorEnum.E_10008);
        }
        userDao.removeRole(jsonObject);
        userDao.removeRoleAllPermission(jsonObject);
        return CommonUtil.successJSON();
    }




}
