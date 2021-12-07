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
 * @date 2021/9/24 13:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "t_role_menu_resource",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "menu_resource_id"})}
)
public class RoleMenuResource implements Serializable {

    private static final long serialVersionUID = -6072649242943360823L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "role_menu_resource_id", length = 32, nullable = false)
    private String roleMenuResourceId;

    @Column(name = "role_id", length = 32, nullable = false)
    private String roleId;

    @Column(name = "menu_resource_id", length = 32, nullable = false)
    private String menuResourceId;

    @Column(name = "cre_time")
    private Date creTime;

    @Column(name = "upd_time")
    private Date updTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "menu_resource_id", updatable = false, insertable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MenuResource menuResource;

}
