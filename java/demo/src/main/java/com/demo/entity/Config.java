package com.demo.entity;

import com.demo.bean.ConfigEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Alex
 * @date 2021/9/23 14:27
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "t_config",
        uniqueConstraints = {@UniqueConstraint(columnNames = "config_name")}
)
public class Config implements Serializable {

    private static final long serialVersionUID = 7730509270840748955L;

    public Config(ConfigEnum configEnum) {
        this.configName = configEnum.getKey();
        this.configValue = configEnum.getValue();
        this.configDesc = configEnum.getDesc();
    }

    @Id
    @Column(name = "config_name", length = 30, nullable = false)
    private String configName;

    @Column(name = "config_value", length = 30, nullable = false)
    private String configValue;

    @Column(name = "config_desc", length = 100)
    private String configDesc;
}
