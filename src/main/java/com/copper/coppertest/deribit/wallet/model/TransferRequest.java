package com.copper.coppertest.deribit.wallet.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * A POJO that represents the transfer to sub account request, that includes the destination of the transfer, and the
 * amount
 */
@Data
public class TransferRequest
{
    private String destination;
    private BigDecimal amount;
}
