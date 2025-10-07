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
@Table(name = "rating")
public class Rating {

    @Id
    @Column(name = "rating_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String moodysRating;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String sandPRating;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String fitchRating;

    private Byte orderNumber;

}
