package com.nnk.springboot.models;

import com.nnk.springboot.models.config.FieldConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bid_list")
public class BidList {

    @Id
    @Column(name = "bid_list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = FieldConstant.TEXT_FIELD_SMALL)
    private String account;

    @NotNull
    @Size(max = FieldConstant.TEXT_FIELD_SMALL)
    private String type;

    private Double bidQuantity;

    private Double askQuantity;

    private Double bid;

    private Double ask;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String benchmark;

    private LocalDateTime bidListDate;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String commentary;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String security;

    @Size(max = FieldConstant.TEXT_FIELD_EXTRA_SMALL)
    private String status;

    @Size(max = FieldConstant.TEXT_FIELD_MEDIUM)
    private String trader;

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
    public BidList(String account, String type) {
        this.account = account;
        this.type = type;
    }
}
