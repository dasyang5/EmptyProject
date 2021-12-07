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
 * @date 2021/9/26 13:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "t_organ"
)
public class Organ implements Serializable {

    private static final long serialVersionUID = 6011690279294594238L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "organ_id", length = 32, nullable = false)
    private String organId;

    @Column(name = "organ_name", length = 100, nullable = false)
    private String organName;

    @Column(name = "cre_time")
    private Date creTime;

    @Column(name = "upd_time")
    private Date updTime;

}
