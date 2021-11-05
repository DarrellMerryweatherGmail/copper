package com.copper.coppertest.deribit.account.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortfolioDto
{
    private String currency;
    private BigDecimal balance;
    @JsonProperty("available_withdrawal_funds")
    private BigDecimal availableWithdrawalFunds;
    @JsonProperty("available_funds")
    private BigDecimal availableFunds;
}
