package com.nnk.springboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "curve_point")
public class CurvePoint {

    @Id
    @Column(name = "curve_point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Byte CurveId;

    private LocalDateTime asOfDate;

    @NotNull
    @Positive
    private Double term;

    @NotNull
    @Positive
    @Column(name = "curve_point_value")
    private Double value;

    private LocalDateTime creationDate;

}


