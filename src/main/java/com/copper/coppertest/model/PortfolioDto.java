package com.copper.coppertest.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PortfolioDto
{
    private String currency;
//    private String accountId;
    private BigDecimal balance;
    private BigDecimal availableWithdrawalFunds;
    private BigDecimal availableFunds;
}
