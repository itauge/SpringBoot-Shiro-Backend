package com.itauge.springbootshirobackend.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    public int id;
    public String username;
    public String password;
    public String nickname;
    public int roleId;
    @TableField(fill = FieldFill.INSERT)
    public String createTime;
    @TableField(fill = FieldFill.UPDATE)
    public String updateTime;
    public int deleteStatus;
    public String token;
    public List roles;
}
