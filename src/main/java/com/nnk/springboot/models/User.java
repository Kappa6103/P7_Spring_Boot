package com.nnk.springboot.models;

import com.nnk.springboot.models.config.FieldConstant;
import com.nnk.springboot.models.config.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @Column(name = "app_user_id")
    private int id;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @NotNull(message = "Role is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ADMIN', 'USER')")
    private Role role;

}
