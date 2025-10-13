package com.nnk.springboot.models;

import com.nnk.springboot.models.config.FieldConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @Column(name = "rulename_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
