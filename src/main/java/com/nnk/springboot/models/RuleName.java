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

    @NotBlank
    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String name;

    @NotBlank
    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String description;

    @NotBlank
    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String json;

    @NotBlank
    @Size(max = FieldConstant.TEXT_FIELD_BIG)
    private String template;

    @NotBlank
    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String sqlStr;

    @NotBlank
    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String sqlPart;

}
