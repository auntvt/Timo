package com.linln.admin.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linln.admin.core.enums.StatusEnum;
import com.linln.admin.core.enums.UserIsRoleEnum;
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
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String salt;
    private String nickname;
    private String picture;
    private String sex;
    private String phone;
    private String email;
    private String remark;
    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date updateDate;
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
