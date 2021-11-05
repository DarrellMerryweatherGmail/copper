package com.copper.coppertest.deribit.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * A DTO that represents the response from the transfer to sub-account request
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferToSubaccountDto
{
    @JsonProperty("updated_timestamp")
    private Long updatedTimestamp;
    private String type;
    private String state;
    @JsonProperty("other_side")
    private String otherSide;
    private Integer id;
    private String direction;
    private String currency;
    @JsonProperty("created_timestamp")
    private Long createTimestamp;
    private BigDecimal amount;
}
