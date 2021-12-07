package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
        name = "t_user_role",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}
)
public class UserRole implements Serializable {

    private static final long serialVersionUID = 2671865235202652334L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "user_role_id", length = 32, nullable = false)
    private String userRoleId;

    @Column(name = "role_id", length = 32, nullable = false)
    private String roleId;

    @Column(name = "user_id", length = 32, nullable = false)
    private String userId;

    @Column(name = "upd_time")
    private Date updTime;

    @Column(name = "cre_time")
    private Date creTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "role_id", updatable = false, insertable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id", updatable = false, insertable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;
}
