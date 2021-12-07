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
 * @date 2021/9/24 13:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "t_menu_resource",
        uniqueConstraints = {}
)
public class MenuResource implements Serializable {

    private static final long serialVersionUID = -9193136305309479629L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "menu_resource_id", length = 32, nullable = false)
    private String menuResourceId;

    @Column(name = "parent_id", length = 32)
    private String parentId;

    @Column(name = "menu_order", length = 4, nullable = false)
    private Integer menuOrder;

    @Column(name = "menu_name", length = 100, nullable = false)
    private String menuName;

    @Column(name = "menu_name_en", length = 120, nullable = false)
    private String menuNameEn;

    @Column(name = "menu_path", length = 100, nullable = false)
    private String menuPath;

    @Column(name = "menu_type", length = 10, nullable = false)
    private String menuType;

    @Column(name = "cre_time")
    private Date creTime;

    @Column(name = "upd_time")
    private Date updTime;

}
