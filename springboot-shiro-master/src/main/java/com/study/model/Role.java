package com.study.model;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel
public class Role implements Serializable{
    private static final long serialVersionUID = -6140090613812307452L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;

    @Column(name = "roleDesc")
    @ApiModelProperty(value = "角色描述", required = true)
    private String roledesc;
    @Transient
    private Integer selected;
    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return roleDesc
     */
    public String getRoledesc() {
        return roledesc;
    }

    /**
     * @param roledesc
     */
    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }
}