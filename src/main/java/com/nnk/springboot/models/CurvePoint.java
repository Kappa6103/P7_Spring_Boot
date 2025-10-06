package com.nnk.springboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "curve_point")
public class CurvePoint {

    @Id
    @Column(name = "curve_point_id")
    private int curvePointId;

    private Byte CurveId;

    private LocalDateTime asOfDate;

    private Double term;

    @Column(name = "curve_point_value")
    private Double value;

    private LocalDateTime creationDate;

}


