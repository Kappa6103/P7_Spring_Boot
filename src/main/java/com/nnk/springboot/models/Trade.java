package com.nnk.springboot.models;

import com.nnk.springboot.models.config.FieldConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @Column(name = "trade_id")
    private int tradeId;

    @NotNull
    @Size(max = FieldConstant.TEXT_FIELD_SMALL)
    private String account;

    @NotNull
    @Size(max = FieldConstant.TEXT_FIELD_SMALL)
    private String type;

    private Double buyQuantity;

    private Double sellQuantity;

    private Double buyPrice;

    private Double sellPrice;

    private LocalDateTime tradeDate;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String security;

    @Size(max = FieldConstant.TEXT_FIELD_EXTRA_SMALL)
    private String status;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String trader;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String benchmark;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String book;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String creationName;

    private LocalDateTime creationDate;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String revisionName;

    private LocalDateTime revisionDate;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String dealName;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String dealType;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String sourceListId;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String side;

    //Constructor for the NotNull values
    public Trade (String account, String type) {
        this.account = account;
        this.type = type;
    }
}
