package com.linln.admin.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linln.admin.core.enums.ExcelType;
import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.enums.UserIsRoleEnum;
import com.linln.admin.core.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 小懒虫
 * @date 2018/8/14
 */
@Entity
@Table(name="sys_user")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Excel("用户数据")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Excel(value = "用户ID", type = ExcelType.EXPORT)
    private Long id;
    @Excel("用户名")
    private String username;
    private String password;
    private String salt;
    @Excel("昵称")
    private String nickname;
    private String picture;
    @Excel(value = "性别", dict = "USER_SEX")
    private String sex;
    @Excel("手机号码")
    private String phone;
    @Excel("电子邮箱")
    private String email;
    @CreatedDate
    @Excel("创建时间")
    private Date createDate;
    @LastModifiedDate
    @Excel("更新时间")
    private Date updateDate;
    @Excel("备注")
    private String remark;
    private Byte isRole = UserIsRoleEnum.NO.getCode();
    private Byte status = StatusEnum.OK.getCode();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dept_id")
    @JsonIgnore
    private Dept dept;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<Role> roles = new HashSet<>(0);
}
