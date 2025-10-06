package com.nnk.springboot.models;

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
    @Size(max = 30)
    private String account;

    @NotNull
    @Size(max = 30)
    private String type;

    @DecimalMax(value = "1.7976931348623157E308")
    private Double buyQuantity;

    @DecimalMax(value = "1.7976931348623157E308")
    private Double sellQuantity;

    @DecimalMax(value = "1.7976931348623157E308")
    private Double buyPrice;

    @DecimalMax(value = "1.7976931348623157E308")
    private Double sellPrice;

    private LocalDateTime tradeDate;

    @Size(max = 125)
    private String security;

    @Size(max = 10)
    private String status;

    @Size(max = 125)
    private String trader;

    @Size(max = 125)
    private String benchmark;

    @Size(max = 125)
    private String book;

    @Size(max = 125)
    private String creationName;

    private LocalDateTime creationDate;

    @Size(max = 125)
    private String revisionName;

    private LocalDateTime revisionDate;

    @Size(max = 125)
    private String dealName;

    @Size(max = 125)
    private String dealType;

    @Size(max = 125)
    private String sourceListId;

    @Size
    private String side;

    //Constructor for the NotNull values
    public Trade (String account, String type) {
        this.account = account;
        this.type = type;
    }
}
