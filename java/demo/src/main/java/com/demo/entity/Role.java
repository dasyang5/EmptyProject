package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Alex
 * @date 2021/9/23 10:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "t_role",
        uniqueConstraints = {@UniqueConstraint(columnNames = "role_auth_key")}
)
public class Role implements Serializable {

    private static final long serialVersionUID = 7067288500411264553L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "role_id", length = 32, nullable = false)
    private String roleId;

    @Column(name = "organ_id", length = 32)
    private String organId;

    @Column(name = "role_name", length = 50, nullable = false)
    private String roleName;

    /**
     * Spring Security 权限验证时使用
     */
    @Column(name = "role_auth_key", length = 50, nullable = false)
    private String roleAuthKey;

    @Column(name = "upd_time")
    private Date updTime;

    @Column(name = "cre_time")
    private Date creTime;
}
