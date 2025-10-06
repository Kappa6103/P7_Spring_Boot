package com.nnk.springboot.models;

import com.nnk.springboot.models.config.FieldConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @Column(name = "rating_id")
    private int ratingId;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String moodysRating;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String sandPRating;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String fitchRating;

    private Byte orderNumber;

}
