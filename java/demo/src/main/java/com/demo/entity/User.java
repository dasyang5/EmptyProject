package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Alex
 * @date 2021/9/18 9:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "t_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = "username")}
)
public class User implements Serializable {

    private static final long serialVersionUID = 4257586443046657142L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "user_id", length = 32, nullable = false)
    private String userId;

    @Column(name = "organ_id", length = 32)
    private String organId;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "account_non_expired", length = 10, nullable = false)
    private Boolean accountNonExpired;

    /**
     * 账号是否锁定
     */
    @Column(name = "account_non_locked", length = 10, nullable = false)
    private Boolean accountNonLocked;

    @Column(name = "credentials_non_expired", length = 10, nullable = false)
    private Boolean credentialsNonExpired;

    /**
     * 账号是否禁用
     */
    @Column(name = "enabled", length = 10, nullable = false)
    private Boolean enabled;

    /**
     * 密码错误次数
     */
    @Column(name = "error_pwd_time")
    @ColumnDefault("0")
    private Long errorPwdTime;

    @Column(name = "login_time")
    private Date loginTime;

    @Column(name = "upd_time")
    private Date updTime;

    @Column(name = "cre_time")
    private Date creTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "organ_id", updatable = false, insertable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Organ organ;

}
