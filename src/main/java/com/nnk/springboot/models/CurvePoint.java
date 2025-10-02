package com.nnk.springboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "curvepoint")
public class CurvePoint {


    LocalDateTime localDateTime;
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
}


