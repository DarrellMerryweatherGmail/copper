package com.copper.coppertest.deribit.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DepositDto
{
    @JsonProperty("updated_timestamp")
    private Long updatedTimestamp;
    @JsonProperty("transaction_id")
    private String transactionId;
    private String state;
    @JsonProperty("received_timestamp")
    private Long receivedTimestamp;
    private String currency;
    private BigDecimal amount;
    private String address;
    private String note;
}
