package com.copper.coppertest.deribit.wallet.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest
{
    private String destination;
    private BigDecimal amount;
}
