package com.nnk.springboot.models;

import jakarta.persistence.*;
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

    private Double term;

    @Column(name = "curve_point_value")
    private Double value;

    private LocalDateTime creationDate;

}


