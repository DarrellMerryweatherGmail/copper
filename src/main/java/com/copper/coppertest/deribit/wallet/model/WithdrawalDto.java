package com.copper.coppertest.deribit.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * A DTO that represents the response when requesting the withdrawal's from Deribit
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WithdrawalDto
{
    private String address;
    private BigDecimal amount;
    @JsonProperty("confirmed_timestamp")
    private Long confirmedTimestamp;
    @JsonProperty("created_timestamp")
    private Long createdTimestamp;
    private String currency;
    private BigDecimal fee;
    private Integer id;
    private Double priority;
    private String state;
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("updated_timestamp")
    private Long updatedTimestamp;
}
