package com.itauge.springbootshirobackend.dao;

import com.alibaba.fastjson.JSONObject;
import com.itauge.springbootshirobackend.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    /**
     * 查詢用戶數量
     */
    public int countUser();

    /**
     * 查詢用戶列表
     */
    public List<User> listUser(JSONObject jsonObject);

    /**
     * 查詢所有的角色
     * 在添加、修改用戶時要使用此方法
     */
    public List<JSONObject> getAllRoles();

    /**
     * 校驗用戶名是否存在
     * @param username
     * @return
     */
    public int queryExistUsername(String username);

    /**
     * 添加用戶
     * @param user
     * @return
     */
    public int addUser(User user);

    /**
     * 更新用戶
     * @param user
     */
    public int updateUser(User user);

    public int batchAddUserRole(User user);

    public int removeUserAllRole(int userId);

    /**
     * 角色列表
     * @return
     */
    public List<JSONObject> listRole();

    /**
     * 查詢所有權限，給角色分配權限時使用
     * @return
     */
    public List<JSONObject> listAllPermission();

    /**
     * 新增角色
     */
    public int insertRole(JSONObject jsonObject);

    /**
     * 批量插入角色權限
     * @param roleId
     * @param permissions
     * @return
     */
    public int insertRolePermission(@Param("roleId") String roleId, @Param("permissions") List<Integer> permissions);

    /**
     * 将角色曾经拥有而修改为不再拥有的权限 delete_status改为'2'
     */
    int removeOldPermission(@Param("roleId") String roleId, @Param("permissions") List<Integer> permissions);

    /**
     * 修改角色名称
     */
    int updateRoleName(JSONObject jsonObject);

    /**
     * 查询某角色的全部数据
     * 在删除和修改角色时调用
     */
    JSONObject getRoleAllInfo(JSONObject jsonObject);

    /**
     * 删除角色
     */
    int removeRole(JSONObject jsonObject);

    /**
     * 删除本角色全部权限
     */
    int removeRoleAllPermission(JSONObject jsonObject);

}
