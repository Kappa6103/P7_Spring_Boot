package com.nnk.springboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @Column(name = "rulename_id")
    private int rulenameId;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String name;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String description;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String json;

    @Size(max = FieldConstant.TEXT_FIELD_BIG)
    private String template;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String sqlStr;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String sqlPart;

}
